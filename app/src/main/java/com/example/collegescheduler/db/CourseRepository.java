package com.example.collegescheduler.db;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CourseRepository {

    private CourseDAO courseDao;
    private LiveData<List<Course>> allCourses;

    public CourseRepository(Application application) {
        CourseDatabase db = CourseDatabase.getInstance(application);
        courseDao = db.courseDao();
        allCourses = courseDao.getAllCourses();
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public LiveData<Course> getCourse(String courseId) {
        MutableLiveData<Course> courseLiveData = new MutableLiveData<>();
        CourseDatabase.databaseExecutor.execute(() -> {
            Course course = courseDao.getCourse(courseId);
            courseLiveData.postValue(course); // Update LiveData with the result
        });
        return courseLiveData;
    }

    public void insert(Course course) {
        CourseDatabase.databaseExecutor.execute(() -> {
            courseDao.addCourse(course);
        });
    }

}
