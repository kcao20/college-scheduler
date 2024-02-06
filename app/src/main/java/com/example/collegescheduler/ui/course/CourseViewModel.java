package com.example.collegescheduler.ui.course;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.collegescheduler.db.AssignmentRepository;
import com.example.collegescheduler.db.Course;
import com.example.collegescheduler.db.CourseRepository;
import com.example.collegescheduler.db.ExamRepository;

public class CourseViewModel extends AndroidViewModel {

    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;
    private final AssignmentRepository assignmentRepository;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
        examRepository = new ExamRepository(application);
        assignmentRepository = new AssignmentRepository(application);
    }

    public void delete(String cid) {
        courseRepository.delete(cid);
    }

    public LiveData<Course> getCourse(String cid) {
        return courseRepository.getCourse(cid);
    }

    public void deleteAllAssociatedWithCourseId(String cid) {
        examRepository.deleteExamsWithCourseId(cid);
        assignmentRepository.deleteAssignmentsWithCourseId(cid);
    }

}
