package com.bradesco.projetoprogramacao.controller.playActivityFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.controller.codeRunner.IdeCodeParser;
import com.bradesco.projetoprogramacao.controller.codeRunner.PythonCodeFinishListener;
import com.bradesco.projetoprogramacao.controller.codeRunner.RunnerCli;
import com.bradesco.projetoprogramacao.databinding.FragmentActivityIdeBinding;
import com.bradesco.projetoprogramacao.model.course.Activities;
import com.bradesco.projetoprogramacao.model.services.localServices.ActivitiesService;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class IdeFragment extends Fragment implements PythonCodeFinishListener {

    private FragmentActivityIdeBinding binding;
    private Activities activities;
    private ExtendedFloatingActionButton fabFinish;
    private TextView txtViewExpected;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentActivityIdeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ActivitiesService service = new ActivitiesService(getContext());
        activities = service.get(PlayActivities.activityId);

        IdeCodeParser codeArea = root.findViewById(R.id.editText_code_area);
        RunnerCli pythonRunnerConsole = root.findViewById(R.id.consoleText);
        pythonRunnerConsole.setActivity(this.getActivity());
        pythonRunnerConsole.setOnFinishListener(this);

        FloatingActionButton runFab = root.findViewById(R.id.fab_run);
        FloatingActionButton stopFab = root.findViewById(R.id.fab_stop);

        fabFinish = getActivity().findViewById(R.id.fab_finish);
        txtViewExpected = getActivity().findViewById(R.id.txt_view_expectedOutput);

        runFab.setOnClickListener(view -> {
            // START
            fabFinish.setVisibility(View.GONE);
            String text = codeArea.toString();
            pythonRunnerConsole.runCode(text);
            txtViewExpected.setTextColor(ContextCompat.getColor(getContext(), R.color.consoleColor));
        });
        stopFab.setOnClickListener(view -> {
            // STOP
            pythonRunnerConsole.interrupt();
        });
        fabFinish.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("id", PlayActivities.activityId);
            intent.putExtra("position", PlayActivities.posIndex);
            getActivity().setResult(Activity.RESULT_OK, intent);

            activities.setCompleted(true);
            service.edit(activities, activities.getId());
            service.close();

            getActivity().finish();
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
            fabFinish.setVisibility(View.VISIBLE);
            txtViewExpected.setTextColor(ContextCompat.getColor(getContext(), R.color.consoleGreenFont));

        }
    }
}