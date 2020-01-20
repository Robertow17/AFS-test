package com.example.afstest.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import com.example.afstest.Helpers.Database;
import com.example.afstest.Helpers.TaskRowHelper;
import com.example.afstest.Model.Task;
import com.example.afstest.R;
import com.example.afstest.View.Utils.TaskAdapter;

import java.util.ArrayList;

public class ListOfTasksActivity extends AppCompatActivity {
    RecyclerView tasksList;
    ArrayList<Task> tasks;
    TaskAdapter taskAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksList = findViewById(R.id.tasksList);
        tasks = getTasksFromDB();
        ArrayList<TaskRowHelper> tasksHelpers = createTaskHelpers(tasks);
        setAdapterToRecyclerView(tasksHelpers);
    }


    private ArrayList<TaskRowHelper> createTaskHelpers(ArrayList<Task> tasks){
        ArrayList<TaskRowHelper> tasksHelpers = new ArrayList<>();
        for(Task t: tasks){
            TaskRowHelper taskHelper = new TaskRowHelper(t);
            tasksHelpers.add(taskHelper);
        }

        int positionOfTaskInProgress = findTaskInProgress(tasksHelpers);
        if(positionOfTaskInProgress!=-1){
            for(int i=0; i<tasksHelpers.size(); i++){
                if(i!=positionOfTaskInProgress){
                    tasksHelpers.get(i).setButtonVisible(false);
                }
            }
        }

        return tasksHelpers;
    }

    public ArrayList<Task> getTasksFromDB() {
        ArrayList<Task> tasks = new ArrayList<>();
        Database db = new Database(this);
        Cursor data = db.getData();
        while(data.moveToNext()){
            Task t = new Task(data.getInt(0), data.getString(1), data.getInt(2));
            tasks.add(t);
        }
        return tasks;
    }

    private int findTaskInProgress(ArrayList<TaskRowHelper> tasks){
        for(int i=0; i<tasks.size();i++) {
            if(tasks.get(i).getTask().getStatus()!=0){
                return i;
            }
        }
        return -1;
    }

    private void setAdapterToRecyclerView(ArrayList<TaskRowHelper> tasks) {
        taskAdapter = new TaskAdapter(tasks, this);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        tasksList.setLayoutManager(layoutManager);
        tasksList.setItemAnimator(new DefaultItemAnimator());
        tasksList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        tasksList.setAdapter(taskAdapter);


    }


    public void updateActions(){
        taskAdapter.notifyDataSetChanged();
    }

    public void updateActions(int position){
        taskAdapter.notifyItemChanged(position);
    }


}

