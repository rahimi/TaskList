package de.hsba.app.tasklist.entities.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.hsba.app.tasklist.entities.Task;
import de.hsba.app.tasklist.entities.daos.TaskDao;
import de.hsba.app.tasklist.entities.database.TaskDatabase;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    TaskRepository(Application app){
        TaskDatabase db = TaskDatabase.getDatabase(app);
        taskDao = db.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }

    void insert(Task task){
        TaskDatabase.databaseWriteExecuter.execute(()->{
            taskDao.insert(task);
        });
    }

    void update(Task task){
        TaskDatabase.databaseWriteExecuter.execute(()->{
            taskDao.update(task);
        });
    }
}
