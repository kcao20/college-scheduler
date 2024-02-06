package com.example.collegescheduler.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Entity(tableName = "Assignment")
@TypeConverters(Assignment.Converters.class)
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "due_date")
    private LocalDate date;

    @ColumnInfo(name = "course")
    private String courseId;

    @ColumnInfo(name = "status")
    private boolean status;

    public Assignment(String title, String description, LocalDate date, String courseId) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.courseId = courseId;
        this.status = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Assignment that = (Assignment) obj;
        return id == that.id && status == that.status && Objects.equals(title, that.title) && Objects.equals(date, that.date) && Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, courseId, status);
    }

    static class Converters {
        @TypeConverter
        public static LocalDate fromTimestamp(long value) {
            return Instant.ofEpochMilli(value).atZone(ZoneOffset.UTC).toLocalDate();
        }

        @TypeConverter
        public static long dateToTimestamp(LocalDate date) {
            return date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        }
    }
  
}
