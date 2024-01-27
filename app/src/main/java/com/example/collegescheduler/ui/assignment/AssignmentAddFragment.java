package com.example.collegescheduler.ui.assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentHomeBinding;
import com.example.collegescheduler.db.Course;
import com.example.collegescheduler.ui.home.HomeFragment;
import com.example.collegescheduler.ui.home.HomeViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AssignmentAddFragment extends Fragment {

    private FragmentHomeBinding binding;
    private EditText titleEditText;
    private EditText dateEditText;
    private EditText courseEditText;
    private EditText statusEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_assignment_add, container, false);

        Button addButton = root.findViewById(R.id.assignment_add_course);
        Button backButton = root.findViewById(R.id.assignment_back);
        titleEditText = root.findViewById(R.id.editTextText);
        dateEditText = root.findViewById(R.id.assignment_input_date);
        statusEditText = root.findViewById(R.id.assignment_input_status);

        // Set click listeners for navigation buttons
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addList();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveAssignmentListPage();
            }
        });


        return root;
    }

    private void moveAssignmentListPage() {
        AssignmentList assignmentList = new AssignmentList();

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.assignment_list_scrollview, assignmentList).commit();
    }

    private void addList() {
        Course course = new Course();
        course.setCourseTitle(courseEditText.getText().toString());
        Assignment newAssignment = new Assignment(titleEditText.getText().toString(), dateEditText.getText().toString(), course, statusEditText.getText().toString());
        AssignmentAdapter.addData(newAssignment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}