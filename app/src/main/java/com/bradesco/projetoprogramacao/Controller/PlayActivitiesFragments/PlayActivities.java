package com.bradesco.projetoprogramacao.controller.playActivitiesFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.databinding.ActivityPlayActivitiesBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class PlayActivities extends AppCompatActivity {

    protected static int activityId;
    private ActivityPlayActivitiesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayActivitiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activityId = getIntent().getIntExtra("id", -1);

        BottomNavigationView navView = binding.playActivitiesBottomNavigationBar;

        if(getIntent().getIntExtra("type", -1) != 1){
            getSupportActionBar().hide();
        }else{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Activity");
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back_arrow);
        }

        /*
        navView.setOnItemSelectedListener(item -> {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()){
                case R.id.console:
                    ft.replace(R.id.fragmentActivityContainer, new IdeFragment()).commit();
                    break;
                case R.id.question:
                    ft.replace(R.id.fragmentActivityContainer, new ActivitiesQuestionFragment()).commit();
                    break;
                default:
                    finish();
                    break;
            }
            return false;
        });
        */
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return false;
    }
}