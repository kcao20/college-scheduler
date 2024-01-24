package com.example.collegescheduler.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.collegescheduler.databinding.FragmentHomeBinding;
import com.example.collegescheduler.db.Course;
import com.example.collegescheduler.db.CourseDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private CourseDatabase courseDB;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        courseDB = CourseDatabase.getInstance(requireContext());
        addCourseInBackground();
        getAllCoursesInBackground();

        return root;
    }

    private void getAllCoursesInBackground() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background task
                List<Course> courses = courseDB.getCourseDao().getAllCourses();
                // on finishing task
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateUIWithCourses(courses);
                    }
                });
            }
        });
    }

    private void addCourseInBackground() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background task
                Course course = new Course("CS");
                courseDB.getCourseDao().addCourse(course);
                // on finishing task
            }
        });
    }

    private void updateUIWithCourses(List<Course> courses) {
        TextView textView1 = binding.textCourse;
        textView1.setText(courses.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}