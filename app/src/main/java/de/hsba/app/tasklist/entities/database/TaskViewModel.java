package de.hsba.app.tasklist.entities.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.hsba.app.tasklist.entities.Task;

public class TaskViewModel extends AndroidViewModel {
    TaskRepository taskRepository;
    private final LiveData<List<Task>> allTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
        allTasks = taskRepository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insert(Task task){
        taskRepository.insert(task);
    }

    public void update(Task task){
        taskRepository.update(task);
    }
}
