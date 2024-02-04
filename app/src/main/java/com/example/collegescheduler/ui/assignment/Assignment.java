package com.example.collegescheduler.ui.assignment;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.collegescheduler.db.Course;

import java.time.LocalDateTime;

@Entity(tableName = "Assignment")
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    private int aid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "due_date")
    private LocalDateTime date;

    @ColumnInfo(name = "course")
    private Course course;

    @ColumnInfo(name = "status")
    private String status;

    public Assignment(String title, LocalDateTime date, Course course, String status) {
        this.title = title;
        this.date = date;
        this.course = course;
        this.status = status;
    }

        public Assignment(Assignment assignment) {
            this(assignment.getAssignmentTitle(), assignment.getDate()
                    , new Course(assignment.course), assignment.getStatus());
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
    public String getStatus(){return status;}
    public void setCourse(Course course) {
        this.course = course;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setStatus(String status){this.status = status;}
}
