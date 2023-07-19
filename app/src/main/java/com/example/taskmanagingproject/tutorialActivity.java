package com.example.taskmanagingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

/**
 * the tutorialActivity shown for first time users
 */
public class tutorialActivity extends AppCompatActivity {
    /**
     * The Image view.
     */
    ImageView imageView;
    /**
     * The Num.
     */
    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        imageView = (ImageView) findViewById(R.id.imageView);
    }


    /**
     * Next Image button.
     *
     * @param view the view
     */
    public void NextButton(View view) {
        num++;
        changeImage();
    }

    /**
     * Previous button.
     *
     * @param view the view
     */
    public void PreviousButton(View view) {
        if(num!=0)
            num--;
        changeImage();
    }

    /**
     * Change image according to the number.
     */
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