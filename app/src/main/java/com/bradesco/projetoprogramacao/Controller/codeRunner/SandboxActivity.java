package com.bradesco.projetoprogramacao.controller.codeRunner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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

        EditText codeArea = findViewById(R.id.editText_code_area);
        RunnerCli pythonRunnerConsole = findViewById(R.id.consoleText);
        pythonRunnerConsole.setActivity(this);

        FloatingActionButton runFab = findViewById(R.id.fab_run);
        FloatingActionButton stopFab = findViewById(R.id.fab_stop);

        stopFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.purple_700)));

        runFab.setOnClickListener(view -> {
            // START
            String text = codeArea.toString();
            pythonRunnerConsole.runCode(text);
        });
        stopFab.setOnClickListener(view -> {
            // STOP
            pythonRunnerConsole.interrupt();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ide_menubar, menu);
        return true;
    }
}