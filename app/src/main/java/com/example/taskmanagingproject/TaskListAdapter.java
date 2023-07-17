package com.example.taskmanagingproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagingproject.model.TaskModel;

import java.util.ArrayList;
import java.util.Locale;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

        private ArrayList<TaskModel> taskDataset;
        private Context context;


    /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder)
         */
        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView taskNameTv, taskStatusTv;



            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View

                taskNameTv = (TextView) view.findViewById(R.id.taskNameTv);
                taskStatusTv = (TextView) view.findViewById(R.id.taskStatusTv);

            }

            public TextView getTextView() {
                return taskNameTv;
            }
        }

        public TaskListAdapter(Context context, ArrayList<TaskModel> taskDataset) {
            this.context = context;
            this.taskDataset = taskDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_task, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.taskNameTv.setText(taskDataset.get(position).getTaskName());
            viewHolder.taskStatusTv.setText(taskDataset.get(position).getTaskStatus());

            String status = taskDataset.get(position).getTaskStatus();
            System.out.println(status);


            if (status.toLowerCase().equals("pending")) {
                viewHolder.taskStatusTv.setTextColor(ContextCompat.getColor(context, R.color.pendingColor));
                viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            } else if (status.toLowerCase().equals("completed")) {
                viewHolder.taskStatusTv.setTextColor(ContextCompat.getColor(context, R.color.completedColor));
                GradientDrawable shapeDrawable = new GradientDrawable();
                shapeDrawable.setColor(ContextCompat.getColor(context, R.color.completedColor));
                System.out.println(shapeDrawable.getColor());

            } else {
                viewHolder.taskStatusTv.setTextColor(ContextCompat.getColor(context, android.R.color.white));
                viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return taskDataset.size();
        }
    }


