package com.example.collegescheduler.ui.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAddCourseBinding;

public class NewCourseFragment extends Fragment {

    private FragmentAddCourseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NewCourseViewModel newCourseViewModel = new ViewModelProvider(this).get(NewCourseViewModel.class);

        binding = FragmentAddCourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EditText courseID = binding.courseID.getEditText();
        final EditText courseTitle = binding.courseTitle.getEditText();
        final EditText courseDescription = binding.courseDescription.getEditText();
        final EditText courseInstructor = binding.courseInstructor.getEditText();
        final Button saveButton = binding.saveButton;

        saveButton.setOnClickListener(v -> {
            String cID = courseID.getText().toString();
            String cTitle = courseTitle.getText().toString();
            String cDescription = courseDescription.getText().toString();
            String cInstructor = courseInstructor.getText().toString();
            if (cID.trim().isEmpty()) {
                Toast.makeText(requireContext(), "Course ID can not be blank", Toast.LENGTH_SHORT).show();
            } else {
                newCourseViewModel.checkAndSaveCourse(cID, cTitle, cDescription, cInstructor).observe(getViewLifecycleOwner(), courseSaved -> {
                    if (courseSaved) {
                        Toast.makeText(requireContext(), "Course saved successfully", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigate(R.id.action_add_course_to_nav_home);
                    } else {
                        Toast.makeText(requireContext(), "Course already exists", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}