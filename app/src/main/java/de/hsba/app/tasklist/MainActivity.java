package de.hsba.app.tasklist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import de.hsba.app.tasklist.entities.Task;
import de.hsba.app.tasklist.entities.database.TaskViewModel;
import de.hsba.app.tasklist.views.TaskListAdapter;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_TASK_REQUEST_CODE = 1337;
    TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView taskListView = findViewById(R.id.task_list);
        final TaskListAdapter adapter = new TaskListAdapter(new TaskListAdapter.TaskDiff(), this);
        taskListView.setAdapter(adapter);
        taskListView.setLayoutManager(new LinearLayoutManager(this));

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory
                                                            .getInstance(getApplication());
        taskViewModel = new ViewModelProvider(this,factory).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, tasks -> {
            adapter.submitList(tasks);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TASK_REQUEST_CODE && resultCode == RESULT_OK){
            Task task = new Task(data.getStringExtra(AddTaskActivity.EXTRA_REPLY_LABEL)
                                ,data.getStringExtra(AddTaskActivity.EXTRA_REPLY_DATE));
            taskViewModel.insert(task);
        }else {
            Toast.makeText(getApplicationContext(),"Not Saved",Toast.LENGTH_SHORT);
        }
    }

    public void addTask(View view) {
        Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
        startActivityForResult(addTaskIntent,NEW_TASK_REQUEST_CODE);
    }
}