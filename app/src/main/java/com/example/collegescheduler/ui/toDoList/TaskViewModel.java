package com.example.collegescheduler.ui.toDoList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.collegescheduler.db.Task;
import com.example.collegescheduler.db.TaskRepository;

import java.util.List;

// TaskViewModel.java
public class TaskViewModel extends AndroidViewModel {

    private TaskRepository taskRepository;
    private LiveData<List<Task>> allTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
        allTasks = taskRepository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void createTask(Task task) {
        taskRepository.insert(task);
    }

    public void updateTask(Task newTask) {
        taskRepository.update(newTask);
    }

    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    public void updateTaskCompletionStatus(Task task, boolean isCompleted) {
        task.setCompleted(isCompleted);
        taskRepository.update(task);
    }

}