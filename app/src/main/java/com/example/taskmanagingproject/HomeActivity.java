package com.example.taskmanagingproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanagingproject.model.TaskModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView taskRv;
    AlertDialog.Builder adb;

    ArrayList<TaskModel> dataList= new ArrayList<>();
    TaskListAdapter taskListAdapter;
    EditText eT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        adb = new AlertDialog.Builder(this);
        eT =  new EditText(this);
        eT.setHint("Enter Task");
        adb.setTitle("New Task");
        adb.setCancelable(false);
        adb.setView(eT);


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

    public void addTask(View view) {
        adb.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String newTaskName = eT.getText().toString();
                if(!newTaskName.equals(""))
                    Toast.makeText(HomeActivity.this, "New Task Created Successfully", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(HomeActivity.this, "No Task Created", Toast.LENGTH_SHORT).show();
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        if (eT.getParent() != null) {
            ((ViewGroup) eT.getParent()).removeView(eT);
        }
        adb.setView(eT);
        adb.show();
        eT.setText("");
    }
}