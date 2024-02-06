package com.example.collegescheduler.ui.assignment;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.Assignment;

public class AssignmentViewHolder extends RecyclerView.ViewHolder {


    private final TextView assignmentTitle;
    private final CheckBox status;

    public AssignmentViewHolder(@NonNull View itemView) {
        super(itemView);
        assignmentTitle = itemView.findViewById(R.id.textViewAssignmentTitle);
        status = itemView.findViewById(R.id.checkBox);
    }

    static AssignmentViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignments, parent, false);
        return new AssignmentViewHolder(view);
    }

    public void bind(Assignment assignment) {
        assignmentTitle.setText(assignment.getTitle());
        status.setChecked(assignment.getStatus());
        if (assignment.getStatus()) {
            assignmentTitle.setPaintFlags(assignmentTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            assignmentTitle.setPaintFlags(assignmentTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    public CheckBox getStatusCheckBox() {
        return status;
    }

    public TextView getAssignmentTitle() {
        return assignmentTitle;
    }

}
