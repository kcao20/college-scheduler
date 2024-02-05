package com.example.collegescheduler.ui.exam;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.collegescheduler.db.CourseRepository;
import com.example.collegescheduler.db.Exam;
import com.example.collegescheduler.db.ExamRepository;

import java.util.List;

public class ExamViewModel extends AndroidViewModel {

    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;
    private final LiveData<List<Exam>> allExams;

    public ExamViewModel(@NonNull Application application) {
        super(application);
        examRepository = new ExamRepository(application);
        courseRepository = new CourseRepository(application);
        allExams = examRepository.getAllExams();
    }

    public LiveData<List<Exam>> getAllExams() {
        return allExams;
    }

    public void createExam(Exam exam) {
        examRepository.insert(exam);
    }

    public void updateExam(Exam newExam) {
        examRepository.update(newExam);
    }

    public void deleteExam(Exam exam) {
        examRepository.delete(exam);
    }

    public LiveData<List<String>> getAllCourseIds() {
        return courseRepository.getAllCourseIds();
    }

    public LiveData<Exam> getExam(String examId) {
        return examRepository.getExam(examId);
    }

}
