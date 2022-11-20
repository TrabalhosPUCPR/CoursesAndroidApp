package com.bradesco.projetoprogramacao.controller.sandBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bradesco.projetoprogramacao.R;
import com.chaquo.python.PyException;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.Objects;

public class SandboxActivity extends AppCompatActivity{
    private final int consoleUpdateDelay = 100;
    private boolean runningCode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back_arrow);
        getSupportActionBar().setTitle(R.string.Sandbox);

        IdeCodeParser codeArea = findViewById(R.id.editText_code_area);
        TextView consoleText = findViewById(R.id.text_console);

        FloatingActionButton runFab = findViewById(R.id.fab_run);
        FloatingActionButton stopFab = findViewById(R.id.fab_stop);

        stopFab.setBackgroundColor(ContextCompat.getColor(this, R.color.stopNotRunning_fab));

        if(!Python.isStarted()) Python.start(new AndroidPlatform(this));
        Python python = Python.getInstance();
        PyObject runner = python.getModule("maininterpreter");
        PyObject output = python.getModule("io").callAttr("StringIO");
        python.getModule("sys").put("stdout", output);

        runFab.setOnClickListener(view -> {
            // TODO: 10/20/2022 run code on edit text
            String text = codeArea.toString();
            stopFab.setBackgroundColor(ContextCompat.getColor(this, R.color.stopRunning_fab));
            if(runningCode){
                runner.callAttr("stopcode");
                runningCode = false;
                consoleText.setText("");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            runCode(text, consoleText, runner, output);
        });
        stopFab.setOnClickListener(view -> {
            // TODO: 10/20/2022 stop running code
            if(!runningCode) return;
            runningCode = false;
            runner.callAttr("stopcode");
            stopFab.setBackgroundColor(ContextCompat.getColor(this, R.color.stopNotRunning_fab));
            consoleText.append("Stopped running!");
        });
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

    private void runCode(String code, TextView console, PyObject runnerModule, PyObject output){
        try {
            if(code.isEmpty()){
                return;
            }
            runningCode = true;
            console.clearComposingText();
            console.setText("Running...\n");
            CodeExecuter codeExecuter = new CodeExecuter(code, runnerModule);
            codeExecuter.start();
            Handler handler = new Handler();
            Runnable r = () -> {
                if(codeExecuter.error) throw new PyException(codeExecuter.errorMessage);
                PyObject o = runnerModule.callAttr("consolestring");
                if(!(o == null)){
                    String outputText = o.toString();
                    console.append(output.callAttr("getvalue").toString());
                }
            };
            while (runningCode && runnerModule.callAttr("isrunning").toBoolean()) {
                handler.postDelayed(r, consoleUpdateDelay);
            }
        }catch (PyException e){
            console.append(e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        runnerModule.callAttr("stopcode");
        runningCode = false;
        console.append("Done running!");
    }

    private static class CodeExecuter extends Thread{
        String code;
        PyObject module;
        boolean error;
        String errorMessage;

        public CodeExecuter(String code, PyObject module) {
            this.code = code;
            this.module = module;
        }

        @Override
        public void run() {
            try {
                module.callAttrThrows("maintextcode", code);
            } catch (Throwable e) {
                error = true;
                errorMessage = e.getMessage();
            }
        }
    }
}