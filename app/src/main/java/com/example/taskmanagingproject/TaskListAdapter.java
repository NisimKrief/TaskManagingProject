package com.example.taskmanagingproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagingproject.model.TaskModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The type Task list adapter.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

        private ArrayList<TaskModel> taskDataset;

    /**
     * Set filtered list.
     *
     * @param filteredList the filtered list
     */
    public void setFilteredList(ArrayList<TaskModel> filteredList){
            this.taskDataset = filteredList;
            notifyDataSetChanged();
        }


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView taskNameTv, taskStatusTv;
            private final CardView cardView;

        /**
         * The Containerll.
         */
        LinearLayout containerll;


        /**
         * Instantiates a new View holder.
         *
         * @param view the view
         */
        public ViewHolder(View view) {
                super(view);

                taskNameTv = (TextView) view.findViewById(R.id.taskNameTv);
                taskStatusTv = (TextView) view.findViewById(R.id.taskStatusTv);
                cardView = (CardView) view.findViewById(R.id.cardId);
                containerll = (LinearLayout) view.findViewById(R.id.containerLL);

            }

        /**
         * Gets text view.
         *
         * @return the text view
         */
        public TextView getTextView() {
                return taskNameTv;
            }
        }

    /**
     * Instantiates a new Task list adapter.
     *
     * @param taskDataset the task dataset
     */
    public TaskListAdapter(ArrayList<TaskModel> taskDataset) {
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

            viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            if (status.toLowerCase().equals("pending")) {
                viewHolder.taskStatusTv.setTextColor(Color.parseColor("#FFD300"));
            } else if (status.toLowerCase().equals("completed")) {
                viewHolder.taskStatusTv.setTextColor(Color.parseColor("#013220"));
                viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#8AFFA9"));


            } else {
                viewHolder.taskStatusTv.setTextColor(Color.parseColor("#FFFFFF"));
            }

            viewHolder.containerll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(view.getContext(), viewHolder.containerll);
                    popupMenu.inflate(R.menu.taskmenu);
                    popupMenu.show();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if(menuItem.getItemId()==R.id.deleteMenu){
                                FirebaseFirestore.getInstance().collection("tasks").document(taskDataset.get(position).getTaskId()).delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                taskDataset.remove(position);
                                                notifyItemRemoved(position);
                                                Toast.makeText(view.getContext() , "Task Deleted Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                return true;

                            }
                             if(menuItem.getItemId()==R.id.confirmMenu){

                                TaskModel completedTask = taskDataset.get(position);
                                completedTask.setTaskStatus("COMPLETED");
                                FirebaseFirestore.getInstance().collection("tasks").document(taskDataset.get(position).getTaskId())
                                        .set(completedTask).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(view.getContext() , "Task Marked As Completed", Toast.LENGTH_SHORT).show();
                                                viewHolder.taskStatusTv.setText("COMPLETED");
                                                notifyItemChanged(position);
                                            }
                                        });



                            }




                             return false;
                        }
                    });




                    return false;
                }
            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return taskDataset.size();
        }
    }


