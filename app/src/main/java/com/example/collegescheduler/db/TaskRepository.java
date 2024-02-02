package com.example.collegescheduler.db;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class TaskRepository {

    private TaskDAO taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application) {
        CourseDatabase db = CourseDatabase.getInstance(application);
        taskDao = db.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public LiveData<Task> getTask(String taskId) {
        MutableLiveData<Task> taskLiveData = new MutableLiveData<>();
        CourseDatabase.databaseExecutor.execute(() -> {
            Task task = taskDao.getTask(taskId);
            taskLiveData.postValue(task); // Update LiveData with the result
        });
        return taskLiveData;
    }

    public void insert(Task task) {
        CourseDatabase.databaseExecutor.execute(() -> {
            taskDao.addTask(task);
        });
    }

    public void update(Task task) {
        CourseDatabase.databaseExecutor.execute(() -> {
            taskDao.updateTask(task);
        });
    }

    public void delete(Task task) {
        CourseDatabase.databaseExecutor.execute(() -> {
            taskDao.deleteTask(task);
        });
    }

}