package com.example.collegescheduler.ui.assignment;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.collegescheduler.db.Assignment;
import com.example.collegescheduler.db.AssignmentRepository;
import com.example.collegescheduler.db.CourseRepository;
import com.example.collegescheduler.db.Exam;
import com.example.collegescheduler.db.Task;

import java.util.List;

public class AssignmentViewModel extends AndroidViewModel {

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final LiveData<List<Assignment>> allAssignments;

    public AssignmentViewModel(@NonNull Application application) {
        super(application);
        assignmentRepository = new AssignmentRepository(application);
        courseRepository = new CourseRepository(application);
        allAssignments = assignmentRepository.getAllAssignments();
    }

    public LiveData<List<Assignment>> getAllAssignments() {
        return allAssignments;
    }

    public void createAssignment(Assignment assignment) {
        assignmentRepository.insert(assignment);
    }

    public void updateAssignment(Assignment newAssignment) {
        assignmentRepository.update(newAssignment);
    }

    public void deleteAssignment(int assignmentId) {
        assignmentRepository.delete(assignmentId);
    }

    public LiveData<List<String>> getAllCourseIds() {
        return courseRepository.getAllCourseIds();
    }

    public LiveData<List<Assignment>> getAllAssignmentsWithCourseId(String courseId) {
        return assignmentRepository.getAssignmentsWithCourseId(courseId);
    }

    public LiveData<Assignment> getAssignment(int id) {
        return assignmentRepository.getAssignment(id);
    }

    public void updateAssignmentCompletionStatus(Assignment assignment, boolean isCompleted) {
        assignment.setStatus(isCompleted);
        assignmentRepository.update(assignment);
        Log.d("RFT", "Rft");
    }

}
