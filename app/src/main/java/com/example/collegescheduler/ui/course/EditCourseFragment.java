package com.example.collegescheduler.ui.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.collegescheduler.databinding.FragmentEditCourseBinding;

public class EditCourseFragment extends Fragment {

    private FragmentEditCourseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EditCourseViewModel editCourseViewModel = new ViewModelProvider(this).get(EditCourseViewModel.class);

        binding = FragmentEditCourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView courseID = binding.courseID;
        final EditText courseTitle = binding.courseTitle.getEditText();
        final EditText courseDescription = binding.courseDescription.getEditText();
        final EditText courseInstructor = binding.courseInstructor.getEditText();
        final Button editButton = binding.editButton;

        courseID.setText(EditCourseFragmentArgs.fromBundle(getArguments()).getCourseId());

        editCourseViewModel.getCourse(courseID.getText().toString()).observe(getViewLifecycleOwner(), course -> {
            courseTitle.setText(course.getCourseTitle());
            courseDescription.setText(course.getCourseDescription());
            courseInstructor.setText(course.getInstructor());
        });

        editButton.setOnClickListener(v -> {
            String cID = courseID.getText().toString();
            String cTitle = courseTitle.getText().toString();
            String cDescription = courseDescription.getText().toString();
            String cInstructor = courseInstructor.getText().toString();
            if (cID.trim().isEmpty()) {
                Toast.makeText(requireContext(), "Course ID can not be blank", Toast.LENGTH_SHORT).show();
            } else {
                editCourseViewModel.updateCourse(cID, cTitle, cDescription, cInstructor).observe(getViewLifecycleOwner(), courseUpdated -> {
                    if (courseUpdated) {
                        Toast.makeText(requireContext(), "Course updated successfully", Toast.LENGTH_SHORT).show();
                        EditCourseFragmentDirections.ActionEditCourseToCourse action =
                                EditCourseFragmentDirections.actionEditCourseToCourse(cID);
                        Navigation.findNavController(v).navigate(action);
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