package com.example.taskmanagingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

public class tutorialActivity extends AppCompatActivity {
    ImageView imageView;
    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        imageView = (ImageView) findViewById(R.id.imageView);
    }


    public void NextButton(View view) {
        num++;
        changeImage();
    }

    public void PreviousButton(View view) {
        num--;
        changeImage();
    }
    public void changeImage(){
        switch (num) {
            case 0:
                imageView.setImageResource(R.drawable.image0);

                break;
            case 1:
                imageView.setImageResource(R.drawable.image1);

                break;
            case 2:
                imageView.setImageResource(R.drawable.image2);

                break;
            case 3:
                imageView.setImageResource(R.drawable.image3);

                break;
            case 4:
                imageView.setImageResource(R.drawable.image4);

                break;
            case 5:
                imageView.setImageResource(R.drawable.image5);

                break;
            case 6:
                imageView.setImageResource(R.drawable.image6);

                break;
            default:
                finish();
        }


    }
}