package com.example.collegescheduler.ui.course;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.collegescheduler.db.Course;
import com.example.collegescheduler.db.CourseRepository;

public class NewCourseViewModel extends AndroidViewModel {

    private CourseRepository courseRepository;

    public NewCourseViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
    }

    public void onSaveButtonClick(String courseID, String courseTitle) {
        courseRepository.getCourse(courseID).observeForever(course -> {
            if (course == null) {
                courseRepository.insert(new Course(courseID, courseTitle));
            } else {
                courseRepository.update(new Course(courseID, courseTitle));
            }
        });
    }

}