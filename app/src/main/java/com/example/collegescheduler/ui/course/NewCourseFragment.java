package com.example.collegescheduler.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.collegescheduler.databinding.FragmentCourseBinding;
import com.google.android.material.textfield.TextInputEditText;

public class NewCourseFragment extends Fragment {

    private FragmentCourseBinding binding;
    public static final String EXTRA_REPLY = "com.example.collegescheduler.ui.course.REPLY";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NewCourseViewModel newCourseViewModel = new ViewModelProvider(this).get(NewCourseViewModel.class);

        binding = FragmentCourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextInputEditText courseID = binding.courseID;
        final TextInputEditText courseTitle = binding.courseTitle;
        final Button saveButton = binding.saveButton;

        if (getArguments() != null) {
            String courseIdArg = getArguments().getString("courseId");
            String courseTitleArg = getArguments().getString("courseTitle");
            courseID.setText(courseIdArg);
            courseTitle.setText(courseTitleArg);
        }

        saveButton.setOnClickListener(v -> {
            String cid = courseID.getText().toString();
            String title = courseTitle.getText().toString();
            newCourseViewModel.onSaveButtonClick(cid, title);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}