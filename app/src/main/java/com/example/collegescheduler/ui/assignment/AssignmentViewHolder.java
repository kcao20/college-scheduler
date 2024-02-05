package com.example.collegescheduler.ui.assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.Assignment;

public class AssignmentViewHolder extends RecyclerView.ViewHolder {
    private final TextView assignmentTitleTextView;

    public AssignmentViewHolder(@NonNull View itemView) {
        super(itemView);
        assignmentTitleTextView = itemView.findViewById(R.id.textViewAssignmentTitle);
    }

    static AssignmentViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignments, parent, false);
        return new AssignmentViewHolder(view);
    }

    public void bind(Assignment assignment) {
        assignmentTitleTextView.setText(assignment.getTitle());
    }

}
