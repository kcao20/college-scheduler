package com.example.collegescheduler.ui.assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentHomeBinding;
import com.example.collegescheduler.db.Course;

public class AssignmentEditFragment extends Fragment {

    private FragmentHomeBinding binding;
    private EditText titleEditText;
    private EditText dateEditText;
    private EditText courseEditText;
    private EditText statusEditText;
    private int aid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_assignment_edit, container, false);

        Button addCourseButton = root.findViewById(R.id.assignment_add_course);
        Button backButton = root.findViewById(R.id.assignment_back);
        titleEditText = root.findViewById(R.id.editTextText);
        dateEditText = root.findViewById(R.id.editTextDate);
        statusEditText = root.findViewById(R.id.assignment_edit_status);
        courseEditText = root.findViewById(R.id.assignment_input_course);

        // Set click listeners for navigation buttons
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTitleAssignment();
                editDateAssignment();
                editCourseAssignment();
                editStatusAssignment();
            }
        });

        return root;
    }

    private void editDateAssignment() {
        String date = dateEditText.getText().toString();
        if (!date.isEmpty()) {
            AssignmentAdapter.editDate(aid, date);
        }
    }

    private void editCourseAssignment() {
        String courseName = courseEditText.getText().toString();
        if (!courseName.isEmpty()) {
            Course course = new Course();
            course.setCourseTitle(courseEditText.getText().toString());
            AssignmentAdapter.editCourse(aid, course.getCid());
        }
    }

    private void editTitleAssignment() {
        String title = titleEditText.getText().toString();
        if (!title.isEmpty()) {
            AssignmentAdapter.editTitle(aid, title);
        }
    }

    private void editStatusAssignment() {
        String status = statusEditText.getText().toString();
        if (!status.isEmpty()) {
            AssignmentAdapter.editStatus(aid, Boolean.getBoolean(status));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}