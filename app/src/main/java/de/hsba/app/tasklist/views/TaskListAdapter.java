package de.hsba.app.tasklist.views;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import de.hsba.app.tasklist.entities.Task;
import de.hsba.app.tasklist.entities.database.TaskViewModel;

public class TaskListAdapter extends ListAdapter<Task,TaskViewHolder> {

    TaskViewModel taskViewModel;

    public TaskListAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback, Activity context){
        super(diffCallback);
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory
                .getInstance(context.getApplication());
        taskViewModel = new ViewModelProvider((ViewModelStoreOwner) context,factory).get(TaskViewModel.class);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TaskViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task current = getItem(position);
        holder.bind(current, new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                current.setDone(b);
                taskViewModel.update(current);
            }
        });
    }

    public static class TaskDiff extends DiffUtil.ItemCallback<Task>{
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getLabel().equals(newItem.getLabel());
        }
    }
}
