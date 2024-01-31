package com.example.collegescheduler.ui.course;

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

public class CourseViewModel extends AndroidViewModel {

    private CourseRepository courseRepository;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
    }

    public void onSaveButtonClick(String courseID, String courseTitle) {
        courseRepository.getCourse(courseID).observeForever(course -> {
            if (course == null) {
                courseRepository.insert(new Course(courseID, courseTitle));
            }
        });
    }

}