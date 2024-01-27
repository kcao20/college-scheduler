package com.example.collegescheduler.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "Assignment")
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    public int aid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "due_date")
    public LocalDateTime date;

    @ColumnInfo(name = "course")
    public Course course;

    public Assignment(String title, LocalDateTime date, Course course) {
        this.title = title;
        this.date = date;
        this.course = course;
    }

    public String getAssignmentTitle() {
        return title;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
