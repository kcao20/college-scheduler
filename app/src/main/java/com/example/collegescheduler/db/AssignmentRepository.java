package com.example.collegescheduler.db;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class AssignmentRepository {

    private final AssignmentDAO assignmentDAO;
    private final LiveData<List<Assignment>> allAssignments;

    public AssignmentRepository(Application application) {
        CourseDatabase db = CourseDatabase.getInstance(application);
        assignmentDAO = db.assignmentDAO();
        allAssignments = assignmentDAO.getAllAssignments();
    }

    public LiveData<List<Assignment>> getAllAssignments() {
        return allAssignments;
    }

    public LiveData<Assignment> getAssignment(String id) {
        MutableLiveData<Assignment> assignmentLiveData = new MutableLiveData<>();
        CourseDatabase.databaseExecutor.execute(() -> {
            Assignment assignment = assignmentDAO.getAssignment(id);
            assignmentLiveData.postValue(assignment); // Update LiveData with the result
        });
        return assignmentLiveData;
    }

    public void insert(Assignment assignment) {
        CourseDatabase.databaseExecutor.execute(() -> {
            assignmentDAO.addAssignment(assignment);
        });
    }

    public void update(Assignment assignment) {
        CourseDatabase.databaseExecutor.execute(() -> {
            assignmentDAO.updateAssignment(assignment);
        });
    }

    public void delete(Assignment assignment) {
        CourseDatabase.databaseExecutor.execute(() -> {
            assignmentDAO.deleteAssignment(assignment);
        });
    }

}
