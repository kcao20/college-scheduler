package com.example.collegescheduler.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Entity(tableName = "Exam")
@TypeConverters(Exam.Converters.class)
public class Exam {
    @PrimaryKey(autoGenerate = true)
    public int examId;

    @ColumnInfo(name = "location")
    public String examLocation;

    @ColumnInfo(name = "dateTime")
    public LocalDateTime dateTime;

    @ColumnInfo(name = "course")
    public String courseId;

    public Exam(String examLocation, LocalDateTime dateTime, String courseId) {
        this.examLocation = examLocation;
        this.dateTime = dateTime;
        this.courseId = courseId;
    }

    public int getExamId() {
        return examId;
    }

    public String getExamLocation() {
        return examLocation;
    }

    public void setExamLocation(String examLocation) {
        this.examLocation = examLocation;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
  
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Exam exam = (Exam) obj;

        return examId == exam.examId &&
                Objects.equals(examLocation, exam.examLocation) &&
                Objects.equals(dateTime, exam.dateTime) &&
                Objects.equals(courseId, exam.courseId);
    }

    static class Converters {
        @TypeConverter
        public static LocalDateTime fromTimestamp(long value) {
            return Instant.ofEpochMilli(value).atZone(ZoneOffset.UTC).toLocalDateTime();
        }

        @TypeConverter
        public static long dateToTimestamp(LocalDateTime date) {
            return date.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
        }
    }
}
