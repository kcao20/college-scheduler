package com.example.collegescheduler.ui.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentCourseBinding;
import com.google.android.material.textfield.TextInputEditText;

public class CourseFragment extends Fragment {

    private FragmentCourseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CourseViewModel courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        binding = FragmentCourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextInputEditText courseID = binding.courseID;
        final TextInputEditText courseTitle = binding.courseTitle;
        final Button saveButton = binding.saveButton;

        saveButton.setOnClickListener(v -> {
            String cid = courseID.getText().toString();
            String title = courseTitle.getText().toString();
            courseViewModel.onSaveButtonClick(cid, title);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}