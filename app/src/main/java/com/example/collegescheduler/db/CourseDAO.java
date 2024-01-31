package com.example.collegescheduler.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {
    @Query("SELECT * FROM course")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM course WHERE cid IN (:courseId)")
    Course getCourse(String courseId);

    @Insert
    void addCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

}
