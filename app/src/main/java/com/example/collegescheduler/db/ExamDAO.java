package com.example.collegescheduler.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExamDAO {
    @Query("SELECT * FROM exam")
    LiveData<List<Exam>> getAllExams();

    @Query("SELECT * FROM exam WHERE examid IN (:examId)")
    Exam getExam(String examId);

    @Query("SELECT * FROM exam where course in (:courseId)")
    LiveData<List<Exam>> getExamsWithCourseId(String courseId);

    @Insert
    void addExam(Exam exam);

    @Update
    void updateExam(Exam exam);

    @Delete
    void deleteExam(Exam exam);
}