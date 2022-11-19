package com.bradesco.projetoprogramacao.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bradesco.projetoprogramacao.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class SandboxActivity extends AppCompatActivity {

    private boolean consoleExpanded = false;
    private int density, initialSize, initialY;
    private FrameLayout consoleArea;
    private EditText codeArea;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back_arrow);
        getSupportActionBar().setTitle(R.string.Sandbox);

        codeArea = findViewById(R.id.editText_code_area);

        TextView consoleText = findViewById(R.id.text_console);
        consoleArea = findViewById(R.id.consoleLayout);
        FloatingActionButton runFab = findViewById(R.id.fab_run);
        FloatingActionButton stopFab = findViewById(R.id.fab_stop);

        Resources resources = getResources();
        density = (int) resources.getDisplayMetrics().density;
        initialSize = consoleArea.getHeight();

        runFab.setOnClickListener(view -> {
            // TODO: 10/20/2022 run code on edit text

        });
        stopFab.setOnClickListener(view -> {
            // TODO: 10/20/2022 stop running code
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
}