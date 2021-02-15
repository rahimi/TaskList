package de.hsba.app.tasklist.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import de.hsba.app.tasklist.entities.daos.TaskDao;

@Entity(tableName = TaskDao.TASK_TABLE)
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = TaskDao.LABEL)
    private String label;

    @ColumnInfo(name = TaskDao.DONE)
    private boolean done;

    @ColumnInfo(name = TaskDao.DUE_DATE)
    private String dueDate;

    public Task(@NonNull String label, String dueDate){
        this.done = false;
        this.dueDate = dueDate;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
