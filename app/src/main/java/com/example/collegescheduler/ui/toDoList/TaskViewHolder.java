package com.example.collegescheduler.ui.toDoList;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.collegescheduler.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewTaskTitle;

    public CheckBox checkBox;
    private View.OnClickListener clickListener;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTaskTitle = itemView.findViewById(R.id.textViewTaskTitle);
        checkBox = itemView.findViewById(R.id.checkBox);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
            }
        });
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
