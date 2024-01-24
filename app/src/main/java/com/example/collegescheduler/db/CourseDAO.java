package com.example.collegescheduler.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {
    @Query("SELECT * FROM course")
    List<Course> getAllCourses();

    @Query("SELECT * FROM course WHERE cid IN (:courseId)")
    List<Course> getCourse(int[] courseId);

    @Insert
    void addCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

}
