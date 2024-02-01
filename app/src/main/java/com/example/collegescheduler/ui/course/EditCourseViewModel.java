package com.example.collegescheduler.ui.course;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.collegescheduler.db.Course;
import com.example.collegescheduler.db.CourseRepository;

public class EditCourseViewModel extends AndroidViewModel {

    private final CourseRepository courseRepository;

    public EditCourseViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
    }

    public LiveData<Course> getCourse(String cid) {
        return courseRepository.getCourse(cid);
    }

    public LiveData<Boolean> updateCourse(String courseId, String courseTitle, String courseDescription, String courseInstructor) {
        MutableLiveData<Boolean> courseUpdatedLiveData = new MutableLiveData<>();

        courseRepository.courseExists(courseId).observeForever(courseExists -> {
            if (courseExists) {
                Course course = new Course(courseId, courseTitle, courseDescription, courseInstructor);
                courseRepository.update(course);
                courseUpdatedLiveData.setValue(true);
            } else {
                courseUpdatedLiveData.setValue(false);
            }
        });

        return courseUpdatedLiveData;
    }

}