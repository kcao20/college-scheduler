package com.example.collegescheduler.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {
    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM task WHERE taskid IN (:taskId)")
    Task getTask(String taskId);

    @Insert
    void addTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

}