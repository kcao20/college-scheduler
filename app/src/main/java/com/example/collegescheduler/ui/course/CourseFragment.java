package com.example.collegescheduler.ui.course;

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

        courseViewModel.getCourse(courseId).observe(getViewLifecycleOwner(), course -> {
            courseInfo.setText(String.format("%s\n%s\n%s\n%s", course.getCid(), course.getCourseTitle(), course.getCourseDescription(), course.getInstructor()));
        });

        deleteButton.setOnClickListener(v -> {
            courseViewModel.delete(courseId);
            Navigation.findNavController(view).navigate(R.id.action_course_to_nav_home);
        });

        editButton.setOnClickListener(v -> {
            CourseFragmentDirections.ActionCourseToEditCourse action =
                    CourseFragmentDirections.actionCourseToEditCourse(courseId);
            Navigation.findNavController(view).navigate(action);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
