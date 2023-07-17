package com.example.taskmanagingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.taskmanagingproject.model.TaskModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView taskRv;
    ArrayList<TaskModel> dataList= new ArrayList<>();
    TaskListAdapter taskListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();


        taskRv=findViewById(R.id.taskListRv);

        dataList.add(new TaskModel("testId","go to gym", "completed"));
        dataList.add(new TaskModel("testId","eat breakfast", "completed"));
        dataList.add(new TaskModel("testId","run", "pending"));
        dataList.add(new TaskModel("testId","go to gym", "completed"));
        dataList.add(new TaskModel("testId","eat breakfast", "completed"));
        dataList.add(new TaskModel("testId","run", "pending"));
        dataList.add(new TaskModel("testId","go to gym", "completed"));
        dataList.add(new TaskModel("testId","eat breakfast", "completed"));
        dataList.add(new TaskModel("testId","run", "pending"));
        dataList.add(new TaskModel("testId","go to gym", "completed"));
        dataList.add(new TaskModel("testId","eat breakfast", "completed"));
        dataList.add(new TaskModel("testId","run", "pending"));
        dataList.add(new TaskModel("testId","go to gym", "completed"));
        dataList.add(new TaskModel("testId","eat breakfast", "completed"));
        dataList.add(new TaskModel("testId","run", "pending"));
        dataList.add(new TaskModel("testId","go to gym", "completed"));
        dataList.add(new TaskModel("testId","eat breakfast", "completed"));
        dataList.add(new TaskModel("testId","run", "pending"));

        taskListAdapter=new TaskListAdapter(dataList);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        taskRv.setLayoutManager(layoutManager);
        taskRv.setAdapter(taskListAdapter);



    }
}