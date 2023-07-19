package com.example.taskmanagingproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagingproject.model.TaskModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity {

    RecyclerView taskRv;
    AlertDialog.Builder adb;

    ArrayList<TaskModel> dataList= new ArrayList<>();
    TaskListAdapter taskListAdapter;
    EditText eT;
    FirebaseFirestore db;
    String TAG="MYT";
    TextView userNameTv;
    CircleImageView userImageIv;
    SearchView searchView;

    String currentSortingOption = "all";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        adb = new AlertDialog.Builder(this);
        eT =  new EditText(this);
        eT.setHint("Enter Task");
        adb.setCancelable(false);
        adb.setView(eT);
        adb.setTitle("New Task");
        userNameTv=findViewById(R.id.userNameTv);
        userImageIv=findViewById(R.id.userProfileIv);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        userNameTv.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        Picasso.get().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(userImageIv);

         db = FirebaseFirestore.getInstance();
         taskRv=findViewById(R.id.taskListRv);


        taskListAdapter=new TaskListAdapter(dataList);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        taskRv.setLayoutManager(layoutManager);
        taskRv.setAdapter(taskListAdapter);

        db.collection("tasks")
                .whereEqualTo("userId", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                TaskModel taskModel =document.toObject(TaskModel.class);

                                dataList.add(taskModel);

                                taskListAdapter.notifyDataSetChanged();

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        userImageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view); // Call the method to show the options menu
            }
        });
    }

    private void filterList(String text) {
        ArrayList<TaskModel> filteredList = new ArrayList<>();
        for(TaskModel item : dataList){
            if(item.getTaskName().toLowerCase().contains(text.toLowerCase())){
                if(item.getTaskStatus().toLowerCase().equals(currentSortingOption) || currentSortingOption.equals("all"))
                    filteredList.add(item);
            }
        }
        if(filteredList.isEmpty()){
            //Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
        else{
            taskListAdapter.setFilteredList(filteredList);
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        getMenuInflater().inflate(R.menu.settingsmenu, popupMenu.getMenu());

        // Handle menu item click events
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_logout) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    Toast.makeText(HomeActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.menu_credits) {
                    Intent intent = new Intent(HomeActivity.this, Credits.class);
                    Toast.makeText(HomeActivity.this, "Credits", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        // Show the popup menu
        popupMenu.show();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.settingsmenu, menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            // Handle logout functionality here
            // For example, you can call a logout method or show a logout dialog.
            // Replace the code below with your desired logout implementation.
            Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    public void addTask(View view) {
        adb.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String newTaskName = eT.getText().toString();
                if(!newTaskName.equals("")) {
                    TaskModel taskModel = new TaskModel("",newTaskName,"PENDING", FirebaseAuth.getInstance().getUid());
                    db.collection("tasks").add(taskModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(HomeActivity.this, "New Task Created Successfully", Toast.LENGTH_SHORT).show();
                                    taskModel.setTaskId(documentReference.getId());
                                    dataList.add(taskModel);
                                    documentReference.update("taskId", documentReference.getId());
                                    taskListAdapter.notifyDataSetChanged();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                    Toast.makeText(HomeActivity.this, "Something Failed While Creating The Task", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
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

    public void sortMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        getMenuInflater().inflate(R.menu.sortmenu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                // Handle sorting options here
                ArrayList<TaskModel> filteredList;
                switch (id) {
                    case R.id.menu_completed:
                        currentSortingOption = "completed";
                        filteredList = new ArrayList<>();
                        for (TaskModel task : dataList) {
                            if (task.getTaskStatus().toLowerCase().equals("completed")) {
                                filteredList.add(task);
                            }
                        }

                if (filteredList.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "No Completed Tasks Found", Toast.LENGTH_SHORT).show();

                } else {
                    taskListAdapter.setFilteredList(filteredList);
                }

                break;
                case R.id.menu_pending:
                    currentSortingOption = "pending";
                    filteredList = new ArrayList<>();
                    for (TaskModel task : dataList) {
                        if (task.getTaskStatus().toLowerCase().equals("pending")) {
                            filteredList.add(task);
                        }
                    }

                    if (filteredList.isEmpty()) {
                        Toast.makeText(HomeActivity.this, "No Pending Tasks Found", Toast.LENGTH_SHORT).show();

                    } else {
                        taskListAdapter.setFilteredList(filteredList);
                    }
                break;
                case R.id.menu_both:
                    currentSortingOption = "all";
                    taskListAdapter.setFilteredList(dataList);
                break;
            }
                return true;
            }
        });

        popupMenu.show();

    }

}