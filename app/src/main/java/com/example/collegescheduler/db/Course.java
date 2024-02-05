package com.example.collegescheduler.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Course")
public class Course {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String cid;

    @ColumnInfo(name = "course_title")
    private String courseTitle;

    @ColumnInfo(name = "course_description")
    private String courseDescription;

    @ColumnInfo(name = "instructor")
    private String instructor;

    @Ignore
    public Course() {
    }

    @Ignore
    public Course(@NonNull String cid) {
        this.cid = cid;
    }

    public Course(@NonNull String cid, String courseTitle, String courseDescription, String instructor) {
        this.cid = cid;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.instructor = instructor;
    }

    public String getCid() {
        return cid;
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

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

}
