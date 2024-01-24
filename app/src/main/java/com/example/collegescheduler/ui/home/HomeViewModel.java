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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeViewModel extends AndroidViewModel {

    private final CourseDatabase courseDB;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        courseDB = CourseDatabase.getInstance(getApplication());
    }

    public void onSaveButtonClick(String courseID, String courseTitle) {
        addCourseInBackground(courseID, courseTitle);
    }

    public void onQueryButtonClick(TextView textView, String course) {
        getCourseInBackground(textView, course);
    }

    private void getCourseInBackground(TextView textView, String course) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background task
                Course courses = courseDB.getCourseDao().getCourse(course);
                // on finishing task
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateUIWithCourses(courses, textView);
                    }
                });
            }
        });
    }

    private void addCourseInBackground(String courseID, String courseTitle) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background task
                if (courseDB.getCourseDao().getCourse(courseID) == null) {
                    Course course = new Course(courseID, courseTitle);
                    courseDB.getCourseDao().addCourse(course);
                }
                // on finishing task
            }
        });
    }

    private void updateUIWithCourses(Course course, TextView textView) {
        if (course != null) {
            textView.setText(course.getCourseTitle());
        }
    }

}