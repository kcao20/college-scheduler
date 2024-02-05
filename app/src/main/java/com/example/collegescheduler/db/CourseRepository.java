package com.example.collegescheduler.db;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CourseRepository {

    private CourseDAO courseDao;
    private LiveData<List<Course>> allCourses;

    private LiveData<List<String>> allCourseIds;

    public CourseRepository(Application application) {
        CourseDatabase db = CourseDatabase.getInstance(application);
        courseDao = db.courseDao();
        allCourses = courseDao.getAllCourses();
        allCourseIds = courseDao.getAllCourseIds();
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public LiveData<List<String>> getAllCourseIds() {
        return allCourseIds;
    }

    public LiveData<Course> getCourse(String courseId) {
        MutableLiveData<Course> courseLiveData = new MutableLiveData<>();
        CourseDatabase.databaseExecutor.execute(() -> {
            Course course = courseDao.getCourse(courseId);
            courseLiveData.postValue(course); // Update LiveData with the result
        });
        return courseLiveData;
    }

    public LiveData<Boolean> courseExists(String courseId) {
        MutableLiveData<Boolean> existsLiveData = new MutableLiveData<>();
        CourseDatabase.databaseExecutor.execute(() -> {
            Course course = courseDao.getCourse(courseId);
            boolean exists = (course != null);
            existsLiveData.postValue(exists);
        });
        return existsLiveData;
    }

    public void insert(Course course) {
        CourseDatabase.databaseExecutor.execute(() -> {
            courseDao.addCourse(course);
        });
    }

    public void delete(String courseId) {
        CourseDatabase.databaseExecutor.execute(() -> {
            courseDao.deleteCourseById(courseId);
        });
    }

    public void update(Course course) {
        CourseDatabase.databaseExecutor.execute(() -> {
            courseDao.updateCourse(course);
        });
    }

}
