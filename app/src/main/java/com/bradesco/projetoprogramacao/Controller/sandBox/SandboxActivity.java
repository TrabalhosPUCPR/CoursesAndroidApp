package com.bradesco.projetoprogramacao.controller.sandBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.databinding.ActivitySandboxBinding;
import com.chaquo.python.PyException;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class SandboxActivity extends AppCompatActivity{

    public ActivitySandboxBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        binding = ActivitySandboxBinding.inflate(getLayoutInflater());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back_arrow);
        getSupportActionBar().setTitle(R.string.Sandbox);

        IdeCodeParser codeArea = findViewById(R.id.editText_code_area);
        TextView consoleText = findViewById(R.id.consoleText);
        consoleText.setMovementMethod(new ScrollingMovementMethod());
        consoleText.setHorizontallyScrolling(true);

        FloatingActionButton runFab = findViewById(R.id.fab_run);
        FloatingActionButton stopFab = findViewById(R.id.fab_stop);

        if(!Python.isStarted()) Python.start(new AndroidPlatform(this));
        Python python = Python.getInstance();
        PyObject runner = python.getModule("maininterpreter");
        AtomicReference<CodeExecuter> codeExecuter = new AtomicReference<>(new CodeExecuter());

        stopFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.purple_700)));

        runFab.setOnClickListener(view -> {
            // START
            String text = codeArea.toString();
            if(text.isEmpty()){
                return;
            }
            stopFab.setBackgroundColor(ContextCompat.getColor(this, R.color.stopRunning_fab));
            if(codeExecuter.get().isAlive()){
                appendColoredText(consoleText, "\nCode stopped!\n", ContextCompat.getColor(this, R.color.consoleGreenFont));
                codeExecuter.get().interrupt();
            }else {
                appendColoredText(consoleText, "Running...\n\n", ContextCompat.getColor(this, R.color.run_fab));
            }
            codeExecuter.set(new CodeExecuter(text, runner, consoleText, this));
            codeExecuter.get().start();
        });
        stopFab.setOnClickListener(view -> {
            // STOP
            if(!codeExecuter.get().isAlive()) return;
            codeExecuter.get().interrupt();
            stopFab.setBackgroundColor(ContextCompat.getColor(this, R.color.stopNotRunning_fab));
            appendColoredText(consoleText, "\nCode stopped!\n", ContextCompat.getColor(this, R.color.consoleRedFont));
        });
    }

    public static void appendColoredText(TextView tv, String text, int color) {
        int start = tv.getText().length();
        tv.append(text);
        int end = tv.getText().length();

        Spannable spannableText = (Spannable) tv.getText();
        spannableText.setSpan(new ForegroundColorSpan(color), start, end, 0);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btn_save){
            // TODO: 11/4/2022 save the code
        }else{
            finish();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ide_menubar, menu);
        return true;
    }

    private static class CodeExecuter extends Thread{
        String code, output;
        PyObject module;
        TextView consoleText;
        SandboxActivity mainActivity;
        Runner runner;
        private final int consoleUpdateDelay = 10;

        public CodeExecuter() {}

        public CodeExecuter(String text, PyObject module, TextView consoleText, SandboxActivity mainActivity) {
            this.code = text;
            this.module = module;
            this.consoleText = consoleText;
            this.mainActivity = mainActivity;
            this.runner = new Runner();
            this.output = "";
        }

        @Override
        public void run() {
            try {
                mainActivity.binding.setRunning(true);
                mainActivity.binding.setConsoleOutput("");
                runner.start();
                sleep(10);
                Runnable r = () -> {
                    if(runner.error) throw new PyException(runner.e);
                    PyObject o = module.callAttr("consolestring");
                    if(o == null) return;
                    String outputText = o.toString();
                    output += outputText;
                    updateConsoleText(outputText, ContextCompat.getColor(mainActivity, R.color.consoleColor));
                };
                r.run();
                while (runner.isAlive()) {
                    r.run();
                    sleep(consoleUpdateDelay);
                }
                mainActivity.binding.setRunning(false);
                mainActivity.binding.setConsoleOutput(output);
            } catch (Throwable e){
                updateConsoleText(e.getMessage(), ContextCompat.getColor(mainActivity, R.color.consoleRedFont));
            }
        }

        @Override
        public void interrupt() {
            runner.interrupt();
            mainActivity.binding.setRunning(false);
            super.interrupt();
        }

        private void updateConsoleText(String text, int color){
            if(text == null) return;
            mainActivity.runOnUiThread(() -> appendColoredText(consoleText, text, color));
        }

        private class Runner extends Thread{
            Throwable e;
            boolean error;
            boolean running;

            @Override
            public void run() {
                try {
                    module.callAttrThrows("maintextcode", code);
                    running = true;
                    while (running && module.callAttrThrows("isrunning").toBoolean()){}
                    sleep(100);
                    if(running) updateConsoleText("\nFinished Running!\n", ContextCompat.getColor(mainActivity, R.color.consoleRedFont));
                } catch (Throwable e) {
                    this.e = e;
                    error = true;
                }
                running = false;
            }

            @Override
            public void interrupt() {
                try {
                    module.callAttrThrows("stopcode");
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
                running = false;
                super.interrupt();
            }
        }
    }
}