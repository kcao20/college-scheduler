package com.example.collegescheduler.ui.course;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.collegescheduler.db.Course;
import com.example.collegescheduler.db.CourseRepository;

public class CourseViewModel extends AndroidViewModel {

    private final CourseRepository courseRepository;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
    }

    public void delete(String cid) {
        courseRepository.delete(cid);
    }

    public LiveData<Course> getCourse(String cid) {
        return courseRepository.getCourse(cid);
    }

}
