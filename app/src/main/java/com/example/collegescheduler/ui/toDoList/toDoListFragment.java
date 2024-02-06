package com.example.collegescheduler.ui.toDoList;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentToDoListBinding;
import com.example.collegescheduler.db.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class toDoListFragment extends Fragment {
    private FragmentToDoListBinding binding;
    private TaskViewModel taskViewModel;
    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentToDoListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FloatingActionButton addButton = view.findViewById(R.id.addTask);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        taskAdapter = new TaskAdapter(getContext(), taskViewModel); // Pass an empty list initially
        recyclerView.setAdapter(taskAdapter);

        LiveData<List<Task>> tasksLiveData = taskViewModel.getAllTasks();

        tasksLiveData.observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> taskList) {
                if (!taskList.isEmpty()) {
                    binding.youdongxi.setVisibility(View.GONE);
                }
                taskList.sort((task1, task2) -> {
                    boolean isTask1Completed = task1.isCompleted();
                    boolean isTask2Completed = task2.isCompleted();

                    if (isTask1Completed && isTask2Completed) {
                        return Integer.compare(task1.getTaskid(), task2.getTaskid());
                    } else if (isTask1Completed) {
                        return 1;
                    } else if (isTask2Completed) {
                        return -1;
                    } else {
                        return Integer.compare(task1.getTaskid(), task2.getTaskid());
                    }
                });
                taskAdapter.submitList(taskList);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_task, null);
                dialog.setContentView(dialogView);

                final EditText titleEditText = dialogView.findViewById(R.id.editTextTask);
                final EditText descriptionEditText = dialogView.findViewById(R.id.editTextDescription);

                Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
                Button buttonCreate = dialogView.findViewById(R.id.buttonCreate);

                buttonCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = titleEditText.getText().toString().trim();
                        String description = descriptionEditText.getText().toString().trim();
                        if (!title.isEmpty()) {
                            Task task = new Task(title, description);
                            taskViewModel.createTask(task);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Title is required", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                // Show the keyboard when the dialog is shown
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                        titleEditText.requestFocus();
                    }
                });

                // Set dialog behavior to adjust its size when the keyboard is shown
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                dialog.show();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}