package com.bradesco.projetoprogramacao.controller.codeRunner;

import android.app.Activity;
import android.content.Context;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bradesco.projetoprogramacao.R;
import com.chaquo.python.PyException;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class RunnerCli extends androidx.appcompat.widget.AppCompatTextView {
    private Activity activity;
    private boolean running;
    private String outputtedText;
    private PythonCodeRunner pythonCodeRunner;
    private PythonCodeFinishListener finishListener;

    public RunnerCli(@NonNull Context context, Activity activity) {
        super(context);
        initSetup();
        this.activity = activity;
    }
    public RunnerCli(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initSetup();
    }

    public void setOnFinishListener(PythonCodeFinishListener listener){
        this.finishListener = listener;
    }

    private void initSetup(){
        this.running = false;
        setMovementMethod(new ScrollingMovementMethod());
        setHorizontallyScrolling(true);
        setVerticalScrollBarEnabled(true);
        //setTextColor(getColor(R.id.consoleText));
        this.outputtedText = "";
        this.finishListener = null;
        setOnLongClickListener(view -> {
            clearTerminal();
            return false;
        });
    }

    public String getOutputtedText() {
        return outputtedText;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void clearTerminal(){
        if(running) return;
        setText("");
        try {
            pythonCodeRunner.clearLogs();
        } catch (Throwable e) {
            appendColoredText("\nCould not clear terminal\n", R.color.consoleRedFont);
        }
    }

    public void runCode(String code){
        if(code.isEmpty()) return;
        interrupt();
        this.outputtedText = "";
        pythonCodeRunner = new PythonCodeRunner(code);
        pythonCodeRunner.start();
    }

    public void interrupt(){
        if(running) pythonCodeRunner.interrupt();
    }

    public boolean isRunning(){
        return running;
    }

    private int getColor(int id){
        return ContextCompat.getColor(getContext(), id);
    }

    private void appendColoredText(String text, int color) {
        activity.runOnUiThread(() -> {
            int start = getText().length();
            append(text);
            int end = getText().length();

            Spannable spannableText = (Spannable) getText();
            spannableText.setSpan(new ForegroundColorSpan(color), start, end, 0);
        });
    }

    private void appendNormalText(String text){
        appendColoredText(text, getColor(R.color.consoleColor));
    }

    private class PythonCodeRunner extends Thread{
        String code;
        PyObject module;
        Activity mainActivity;
        Runner runner;
        private final int consoleUpdateDelay = 10;

        public PythonCodeRunner(String text) {
            if(!Python.isStarted()) Python.start(new AndroidPlatform(getContext()));
            Python python = Python.getInstance();
            this.code = text;
            this.module = python.getModule("maininterpreter");
            this.mainActivity = activity;
            this.runner = new Runner();
        }

        public void clearLogs() throws Throwable {
            module.callAttrThrows("clearlogs");
        }

        @Override
        public void run() {
            try {
                appendColoredText("\nRunning...\n", getColor(R.color.consoleGreenFont));
                running = true;
                runner.start();
                sleep(10);
                Runnable r = () -> {
                    if(runner.error) throw new PyException(runner.e);
                    PyObject o = module.callAttr("consolestring");
                    if(o == null) return;
                    String outputText = o.toString();
                    outputtedText += outputText;
                    appendNormalText(outputText);
                };
                r.run();
                while (runner.isAlive()) {
                    r.run();
                    sleep(consoleUpdateDelay);
                }
                running = false;
                appendColoredText("\nCode stopped!\n", getColor(R.color.consoleRedFont));
                if(finishListener != null) mainActivity.runOnUiThread(() -> finishListener.onFinish(outputtedText));
            }catch (InterruptedException ignored){
            } catch (Throwable e){
                appendColoredText("\nError: "+e.getMessage()+"\n", getColor(R.color.consoleRedFont));
            }
        }

        @Override
        public void interrupt() {
            runner.interrupt();
            appendColoredText("\nCode interrupted!\n", getColor(R.color.consoleRedFont));
            running = false;
            super.interrupt();
        }

        private class Runner extends Thread{
            Throwable e;
            boolean error;

            @Override
            public void run() {
                try {
                    module.callAttrThrows("maintextcode", code);
                    while (running && module.callAttrThrows("isrunning").toBoolean()){}
                    sleep(100);
                } catch (Throwable e) {
                    this.e = e;
                    error = true;
                }
            }

            @Override
            public void interrupt() {
                try {
                    module.callAttrThrows("stopcode");
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
                super.interrupt();
            }
        }
    }
}
