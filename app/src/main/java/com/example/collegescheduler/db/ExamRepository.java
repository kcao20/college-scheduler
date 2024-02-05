package com.example.collegescheduler.db;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ExamRepository {

    private ExamDAO examDao;
    private LiveData<List<Exam>> allExams;

    public ExamRepository(Application application) {
        CourseDatabase db = CourseDatabase.getInstance(application);
        examDao = db.examDao();
        allExams = examDao.getAllExams();
    }

    public LiveData<List<Exam>> getAllExams() {
        return allExams;
    }

    public LiveData<Exam> getExam(String examId) {
        MutableLiveData<Exam> examLiveData = new MutableLiveData<>();
        CourseDatabase.databaseExecutor.execute(() -> {
            Exam exam = examDao.getExam(examId);
            examLiveData.postValue(exam); // Update LiveData with the result
        });
        return examLiveData;
    }

    public void insert(Exam exam) {
        CourseDatabase.databaseExecutor.execute(() -> {
            examDao.addExam(exam);
        });
    }

    public void update(Exam exam) {
        CourseDatabase.databaseExecutor.execute(() -> {
            examDao.updateExam(exam);
        });
    }

    public void delete(Exam exam) {
        CourseDatabase.databaseExecutor.execute(() -> {
            examDao.deleteExam(exam);
        });
    }
}
