package com.bradesco.projetoprogramacao.controller.playActivityFragments;

import android.os.Bundle;
import android.view.View;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.databinding.ActivityPlayActivityBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class PlayActivities extends AppCompatActivity {

    private ActivityPlayActivityBinding binding;
    protected static int activityId;
    protected static int posIndex;
    protected static int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityId = getIntent().getIntExtra("id", -1);
        posIndex = getIntent().getIntExtra("position", -1);
        type = getIntent().getIntExtra("type", -1);

        binding = ActivityPlayActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_activity_ide, R.id.nav_activity_question)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_test);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(type == 1){
            finish();
        }
    }
}