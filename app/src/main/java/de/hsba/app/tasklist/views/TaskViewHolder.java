package de.hsba.app.tasklist.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import de.hsba.app.tasklist.R;
import de.hsba.app.tasklist.entities.Task;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    private final TextView taskLabelView;
    private final TextView taskDueDate;
    private final CheckBox doneBox;

    private TaskViewHolder(View taskItem){
        super(taskItem);
        taskLabelView = taskItem.findViewById(R.id.task_label);
        taskDueDate = taskItem.findViewById(R.id.date_label);
        doneBox = taskItem.findViewById(R.id.check_done);
    }

    public void bind(Task current, CompoundButton.OnCheckedChangeListener onCheckedChangeListener){
        taskLabelView.setText(current.getLabel());
        taskDueDate.setText(current.getDueDate());
        doneBox.setChecked(current.isDone());
        doneBox.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    static TaskViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskview_item,parent,false);
        return new TaskViewHolder(view);
    }
}
