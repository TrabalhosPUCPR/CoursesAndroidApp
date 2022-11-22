package com.bradesco.projetoprogramacao.controller.playActivityFragments;

import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityId = getIntent().getIntExtra("id", -1);
        int type = getIntent().getIntExtra("type", -1);

        binding = ActivityPlayActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_activity_ide, R.id.nav_activity_question)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_test);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        if(type == 1){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back_arrow);
        }else{
            getSupportActionBar().hide();
        }
    }

}