package com.example.collegescheduler.ui.toDoList;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentToDoListBinding;
import com.example.collegescheduler.db.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
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

        taskAdapter = new TaskAdapter(new ArrayList<>(), getContext(), taskViewModel); // Pass an empty list initially
        recyclerView.setAdapter(taskAdapter);

        LiveData<List<Task>> tasksLiveData = taskViewModel.getAllTasks();

        tasksLiveData.observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> taskList) {
                taskAdapter.setTaskList(taskList);
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new AlertDialog.Builder instance each time on click
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Create New Task");

                // Set up the layout for the dialog
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                // Set up the first input field
                final EditText inputField1 = new EditText(getContext());
                inputField1.setHint("Task Title");
                layout.addView(inputField1);

                // Set up the second input field
                final EditText inputField2 = new EditText(getContext());
                inputField2.setHint("Task Description");
                layout.addView(inputField2);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the input when the user clicks "OK"
                        String title = inputField1.getText().toString().trim();
                        String description = inputField2.getText().toString().trim();
                        if (!title.isEmpty()) {
                            Task task = new Task(title, description);
                            taskViewModel.createTask(task);
                        } else {
                            // Show a message to the user that input is required for the title
                            Toast.makeText(getContext(), "Title is required", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setView(layout); // Set the custom layout for the AlertDialog
                builder.show();
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