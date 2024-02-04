package com.example.collegescheduler.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "Assignment")
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    private int aid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "due_date")

    private String date;

    @ColumnInfo(name = "course")
    private Course course;

    @ColumnInfo(name = "status")
    private String status;

    public Assignment(String title, String date, Course course, String status) {
        this.title = title;
        this.date = date;
        this.course = course;
        this.status = status;
    }

    public String getAssignmentTitle() {
        return title;
    }
    public String getDate() {
        return "Due Date: " + date;
    }
    public String getCourse() {
        return "Course: " + course.getCourseTitle();
    }
    public String getStatus(){return status;}
    public int getAid() { return aid; }
    public void setCourse(Course course) {
        this.course = course;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setStatus(String status){this.status = status;}
}
