package com.example.collegescheduler.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.collegescheduler.CourseTime;
import com.example.collegescheduler.Instructor;

@Entity(tableName = "Course")
public class Course {
    @PrimaryKey(autoGenerate = true)
    public int cid;

    @ColumnInfo(name = "course_title")
    public String courseTitle;

    @ColumnInfo(name = "course_description")
    public String courseDescription;

    @ColumnInfo(name = "course_time")
    public String courseTime;

    @ColumnInfo(name = "instructor")
    public String instructor;

    @Ignore
    public Course() {
    }

    public Course(String courseTitle) {
        this.courseTitle = courseTitle;
        this.cid = 0;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

}
