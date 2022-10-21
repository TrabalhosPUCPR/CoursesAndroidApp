package com.bradesco.projetoprogramacao.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bradesco.projetoprogramacao.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class SandboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back_arrow);
        getSupportActionBar().setTitle("Sandbox");

        EditText codeArea = findViewById(R.id.editText_code_area);
        TextView consoleText = findViewById(R.id.console_text);
        LinearLayout consoleArea = findViewById(R.id.consoleArea);
        FloatingActionButton runFab = findViewById(R.id.fab_run);
        FloatingActionButton stopFab = findViewById(R.id.fab_stop);

        runFab.setOnClickListener(view -> {
            // TODO: 10/20/2022 run code on edit text
            String code = codeArea.getText().toString();
            int height = 50;
            consoleArea.setLayoutParams(new LinearLayout.LayoutParams(consoleArea.getWidth(), height));
        });
        stopFab.setOnClickListener(view -> {
            // TODO: 10/20/2022 stop running code
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return false;
    }
}