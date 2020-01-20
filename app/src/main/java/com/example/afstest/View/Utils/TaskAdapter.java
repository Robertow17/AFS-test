package com.example.afstest.View.Utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afstest.Helpers.Database;
import com.example.afstest.Helpers.TaskRowHelper;
import com.example.afstest.Model.Task;
import com.example.afstest.R;
import com.example.afstest.View.ListOfTasksActivity;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskRowHelper> tasks;
    private Context context;
    private Database db;

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, status;
        Button button;
        ConstraintLayout background;

        public TaskViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.id);
            name = (TextView) view.findViewById(R.id.name);
            status = (TextView) view.findViewById(R.id.status);
            button = (Button) view.findViewById(R.id.button);
            background = (ConstraintLayout) view.findViewById(R.id.row_background);
        }
    }

    public TaskAdapter(ArrayList<TaskRowHelper> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
        this.db = new Database(context);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false);

        TaskViewHolder holder = new TaskViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, final int position) {
        final TaskRowHelper taskHelper = tasks.get(position);
        final Task task = taskHelper.getTask();

        holder.id.setText(Integer.toString(task.getId()));
        holder.name.setText(task.getName());
        holder.status.setText(taskHelper.getStatusName());
        holder.button.setText(taskHelper.getButtonText());
        holder.background.setBackgroundColor(taskHelper.getBackgroundColor());
        holder.background.setBackgroundColor(context.getResources().getColor(taskHelper.getBackgroundColor()));
        holder.button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                int newStatus = task.getStatus()+1;
                if(newStatus==3) {
                    newStatus =0;
                }
                db.updateStatus(newStatus, task.getId());

                taskHelper.changeTaskStatus(newStatus);
                if(newStatus==0){
                    makeAllButtonsVisible();
                    ((ListOfTasksActivity)context).updateActions();
                }
                else if(newStatus==1){
                    makeAllNonInProgressTasksInvisible(position);
                    ((ListOfTasksActivity)context).updateActions();
                }
                else{
                    ((ListOfTasksActivity)context).updateActions(position);
                }

            }
        });
        if(taskHelper.isButtonVisible()){
            holder.button.setVisibility(View.VISIBLE);
        }
        else{
            holder.button.setVisibility(View.INVISIBLE);
        }


    }

    private void makeAllNonInProgressTasksInvisible(int position){
        for(int i=0; i<tasks.size(); i++){
            if(i!=position){
                tasks.get(i).setButtonVisible(false);
            }
        }
    }

    private void makeAllButtonsVisible(){
        for (TaskRowHelper task : tasks){
            task.setButtonVisible(true);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
