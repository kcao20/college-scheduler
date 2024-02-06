package com.example.collegescheduler.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Entity(tableName = "Course")
@TypeConverters(Course.Converters.class)
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

    @ColumnInfo(name = "startTime")
    private LocalTime courseStartTime;

    @ColumnInfo(name = "endTime")
    private LocalTime courseEndTime;

    @ColumnInfo(name = "DOWs")
    private int[] repeat;

    @Ignore
    public Course() {
    }

    @Ignore
    public Course(@NonNull String cid) {
        this.cid = cid;
    }

    public Course(@NonNull String cid, String courseTitle, String courseDescription, String instructor, LocalTime courseStartTime, LocalTime courseEndTime, int[] repeat) {
        this.cid = cid;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.instructor = instructor;
        this.repeat = repeat;
        this.courseStartTime = courseStartTime;
        this.courseEndTime = courseEndTime;
    }

    public String getCid() {
        return cid;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public LocalTime getCourseStartTime() {
        return courseStartTime;
    }

    public LocalTime getCourseEndTime() {
        return courseEndTime;
    }

    public void setCourseEndTime(LocalTime courseEndTime) {
        this.courseEndTime = courseEndTime;
    }

    public void setCourseStartTime(LocalTime courseStartTime) {
        this.courseStartTime = courseStartTime;
    }

    public int[] getRepeat() {
        return repeat;
    }
    public void setRepeat(int[] repeat) {
        this.repeat = repeat;
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

    static class Converters {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        @TypeConverter
        public static LocalTime fromTimestamp(long value) {
            return Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalTime();
        }

        @TypeConverter
        public static long toTimestamp(LocalTime localTime) {
            return localTime.atDate(LocalDate.of(1970, 1, 1)).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
        @TypeConverter
        public static String fromRepeat(int[] repeat) {
            if (repeat == null) {
                return null;
            }
            return Arrays.toString(repeat);
        }
        @TypeConverter
        public static int[] toRepeat(String repeatString) {
            if (repeatString == null || repeatString.isEmpty()) {
                return null;
            }
            String[] repeatArray = repeatString.substring(1, repeatString.length() - 1).split(", ");
            int[] repeat = new int[repeatArray.length];
            for (int i = 0; i < repeatArray.length; i++) {
                repeat[i] = Integer.parseInt(repeatArray[i]);
            }
            return repeat;
        }
    }

}
