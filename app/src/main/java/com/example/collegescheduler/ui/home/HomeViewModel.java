package com.example.collegescheduler.ui.home;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.collegescheduler.db.Course;
import com.example.collegescheduler.db.CourseDatabase;
import com.example.collegescheduler.db.CourseRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeViewModel extends AndroidViewModel {

    private CourseRepository courseRepository;
    private final LiveData<List<Course>> allCourses;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
        allCourses = courseRepository.getAllCourses();
    }

    public void onQueryButtonClick(TextView textView, String course) {
    }

    private void updateUIWithCourses(Course course, TextView textView) {
        if (course != null) {
            textView.setText(course.getCourseTitle());
        }
    }

}