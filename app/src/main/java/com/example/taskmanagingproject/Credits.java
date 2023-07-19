package com.example.taskmanagingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

/**
 * Credits Activity
 */
public class Credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    /**
     * Back.
     * Returning to the HomeActivity
     * @param view the view
     */
    public void back(View view) {
        finish();
    }
}