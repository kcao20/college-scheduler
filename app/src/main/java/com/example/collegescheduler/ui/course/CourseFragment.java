package com.example.collegescheduler.ui.course;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentCourseBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.format.DateTimeFormatter;

public class CourseFragment extends Fragment {

    private FragmentCourseBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CourseViewModel courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        TextView courseInfo = binding.courseInfo;
        FloatingActionButton deleteButton = binding.deleteButton;
        Button editButton = binding.editButton;

        String courseId = getArguments().getString("courseId");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};



        courseViewModel.getCourse(courseId).observe(getViewLifecycleOwner(), course -> {
            int[] repeat = course.getRepeat();
            StringBuilder repeatStringBuilder = new StringBuilder("Repeat: ");
            for (int i = 0; i < repeat.length; i++) {
                if (repeat[i] == 1) {
                    repeatStringBuilder.append(daysOfWeek[i]);
                    repeatStringBuilder.append(", ");
                }
            }
            if (repeatStringBuilder.length() > "Repeat: ".length()) {
                repeatStringBuilder.setLength(repeatStringBuilder.length() - 2);
            }
            String timeView = repeatStringBuilder.length() == "Repeat: ".length() ? "" : repeatStringBuilder.toString() + " at ";
            courseInfo.setText(String.format("%s\n%s\n%s\n%s\n%s",
                    course.getCid(),
                    course.getCourseTitle(),
                    course.getCourseDescription(),
                    course.getInstructor(),
                    timeView + course.getCourseTime().format(formatter)

            ));
        });

        deleteButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setMessage("Are you sure you want to delete this course?").setPositiveButton("Delete", (dialog, which) -> {
                courseViewModel.delete(courseId);
                Navigation.findNavController(view).navigate(R.id.action_course_to_nav_home);
            }).setNegativeButton("Cancel", (dialog, which) -> {
                // User cancelled the deletion, do nothing
            }).show();
        });

        editButton.setOnClickListener(v -> {
            CourseFragmentDirections.ActionCourseToEditCourse action = CourseFragmentDirections.actionCourseToEditCourse(courseId);
            Navigation.findNavController(view).navigate(action);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
