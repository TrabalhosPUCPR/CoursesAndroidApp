package com.bradesco.projetoprogramacao.controller.playActivityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.controller.codeRunner.IdeCodeParser;
import com.bradesco.projetoprogramacao.controller.codeRunner.PythonCodeFinishListener;
import com.bradesco.projetoprogramacao.controller.codeRunner.RunnerCli;
import com.bradesco.projetoprogramacao.databinding.FragmentActivityIdeBinding;
import com.bradesco.projetoprogramacao.model.course.Activities;
import com.bradesco.projetoprogramacao.model.services.localServices.ActivitiesService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class IdeFragment extends Fragment implements PythonCodeFinishListener {

    private FragmentActivityIdeBinding binding;
    private Activities activities;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentActivityIdeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ActivitiesService service = new ActivitiesService(getContext());
        activities = service.get(PlayActivities.activityId);

        IdeCodeParser codeArea = root.findViewById(R.id.editText_code_area);
        RunnerCli pythonRunnerConsole = root.findViewById(R.id.consoleText);
        pythonRunnerConsole.setActivity(this.getActivity());

        FloatingActionButton runFab = root.findViewById(R.id.fab_run);
        FloatingActionButton stopFab = root.findViewById(R.id.fab_stop);

        runFab.setOnClickListener(view -> {
            // START
            String text = codeArea.toString();
            pythonRunnerConsole.runCode(text);
        });
        stopFab.setOnClickListener(view -> {
            // STOP
            pythonRunnerConsole.interrupt();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onFinish(String fullOutput) {
        if(activities.getExpectedOutput().equals(fullOutput)){
            // TODO: 11/22/2022  activity finished
        }
    }
}