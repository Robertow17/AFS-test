package com.example.afstest.Helpers;

import com.example.afstest.Model.Task;
import com.example.afstest.R;

public class TaskRowHelper {
    private Task task;
    private boolean isButtonVisible;
    private String statusName, buttonText;
    private int backgroundColor;

    public TaskRowHelper(Task task){
        this.task = task;
        this.isButtonVisible = true;
        this.statusName = getStatusName(task.getStatus());
        this.buttonText = getButtonText(task.getStatus());
        this.backgroundColor = getBackgroundColor(task.getStatus());
    }

    private int getBackgroundColor(int number){
        if(number == 0){
            return R.color.openStatus;
        }
        if(number == 1){
            return R.color.travellingStatus;
        }
        if(number == 2){
            return R.color.workingStatus;
        }
        else{
            return 0;
        }
    }

    private String getStatusName(int number){
        if(number == 0){
            return "OPEN";
        }
        if(number == 1){
            return "TRAVELLING";
        }
        if(number == 2){
            return "WORKING";
        }
        else{
            return "WRONG STATUS";
        }
    }

    private String getButtonText(int status){
        if(status == 0){
            return "START TRAVEL";
        }
        if(status == 1){
            return "START WORK";
        }
        if(status == 2){
            return "STOP";
        }
        else{
            return "WRONG STATUS";
        }
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Task getTask() {
        return task;
    }

    public boolean isButtonVisible() {
        return isButtonVisible;
    }

    public void setButtonVisible(boolean buttonVisible) {
        isButtonVisible = buttonVisible;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void changeTaskStatus(int newStatus){
        this.task.setStatus(newStatus);
        this.statusName= getStatusName(newStatus);
        this.buttonText = getButtonText(newStatus);
        this.backgroundColor = getBackgroundColor(newStatus);
    }
}
