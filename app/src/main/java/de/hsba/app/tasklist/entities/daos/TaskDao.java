package de.hsba.app.tasklist.entities.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.hsba.app.tasklist.entities.Task;

@Dao
public interface TaskDao {

    String TASK_TABLE = "task_table";
    String LABEL = "label";
    String DUE_DATE = "due_date";
    String DONE = "done";

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Task task);

    @Query("DELETE FROM "+TASK_TABLE)
    void deleteAll();

    @Query("SELECT * FROM "+TASK_TABLE+" ORDER BY "+DUE_DATE+" ASC")
    LiveData<List<Task>> getAllTasks();
}
