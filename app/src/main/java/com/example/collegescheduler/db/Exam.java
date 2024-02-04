package com.example.collegescheduler.db;

import com.example.collegescheduler.ui.assignment.Assignment;

import java.time.LocalDateTime;

public class Exam extends Assignment {
    private LocalDateTime time;
    private String location;
    // LocalDate date;
    public Exam(String title, LocalDateTime date, Course course, String status, LocalDateTime time, String location) {
        super(title, date, course, status);
        this.time = time;
        this.location = location;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
