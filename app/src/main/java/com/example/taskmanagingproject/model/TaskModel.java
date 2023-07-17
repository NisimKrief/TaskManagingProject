package com.example.taskmanagingproject.model;

public class TaskModel {

    private String taskId, taskName, taskStatus;


    public TaskModel(String taskId, String taskName, String taskStatus) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskStatus = taskStatus;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
