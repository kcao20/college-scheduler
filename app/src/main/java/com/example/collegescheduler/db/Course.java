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
    public String cid;

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

    @Ignore
    public Course(@NonNull String cid) {
        this.cid = cid;
    }

    public Course(@NonNull String cid, String courseTitle) {
        this.cid = cid;
        this.courseTitle = courseTitle;
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

}
