package com.example.collegescheduler.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssignmentDAO {
    @Query("SELECT * FROM assignment")
    LiveData<List<Assignment>> getAllAssignments();

    @Query("SELECT * FROM assignment WHERE id IN (:id)")
    Assignment getAssignment(String id);

    @Insert
    void addAssignment(Assignment assignment);

    @Update
    void updateAssignment(Assignment assignment);

    @Delete
    void deleteAssignment(Assignment assignment);

}
