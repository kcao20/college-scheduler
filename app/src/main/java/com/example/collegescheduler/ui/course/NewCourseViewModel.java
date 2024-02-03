package com.example.collegescheduler.ui.course;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.collegescheduler.db.Course;
import com.example.collegescheduler.db.CourseRepository;

public class NewCourseViewModel extends AndroidViewModel {

    private final CourseRepository courseRepository;

    public NewCourseViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
    }

    public LiveData<Boolean> checkAndSaveCourse(String courseId, String courseTitle, String courseDescription, String courseInstructor) {
        MutableLiveData<Boolean> courseSavedLiveData = new MutableLiveData<>();

        courseRepository.courseExists(courseId).observeForever(courseExists -> {
            if (courseExists) {
                courseSavedLiveData.setValue(false);
            } else {
                Course course = new Course(courseId, courseTitle, courseDescription, courseInstructor);
                courseRepository.insert(course);
                courseSavedLiveData.setValue(true);
            }
        });

        return courseSavedLiveData;
    }

}