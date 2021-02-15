package de.hsba.app.tasklist.entities.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.hsba.app.tasklist.entities.Task;
import de.hsba.app.tasklist.entities.daos.TaskDao;

@Database(entities = {Task.class},version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "task_database";

    public abstract TaskDao taskDao();

    private static volatile TaskDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecuter = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback taskDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecuter.execute(()->{
                TaskDao taskDao = INSTANCE.taskDao();
                taskDao.deleteAll();
            });
        }
    };

    static TaskDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (TaskDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                    TaskDatabase.class,
                                                    DATABASE_NAME)
                            .addCallback(taskDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
