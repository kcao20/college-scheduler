package com.example.collegescheduler.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {
    @Query("SELECT * FROM course")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT cid FROM course")
    LiveData<List<String>> getAllCourseIds();

    @Query("SELECT * FROM course WHERE cid IN (:courseId)")
    Course getCourse(String courseId);

    @Insert
    void addCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Query("DELETE FROM course WHERE cid = :courseId")
    void deleteCourseById(String courseId);

}
