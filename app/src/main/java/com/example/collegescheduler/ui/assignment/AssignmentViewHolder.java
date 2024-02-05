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
    private final TextView assignmentCourseTextView;
    private final TextView assignmentDateTextView;

    public AssignmentViewHolder(@NonNull View itemView) {
        super(itemView);
        assignmentTitleTextView = itemView.findViewById(R.id.assignment_title);
        assignmentCourseTextView = itemView.findViewById(R.id.assignment_course_title);
        assignmentDateTextView = itemView.findViewById(R.id.assignment_edit_due_date);
    }

    static AssignmentViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new AssignmentViewHolder(view);
    }

    public void bind(Assignment assignment) {
        assignmentTitleTextView.setText(assignment.getTitle());
        assignmentCourseTextView.setText(assignment.getCourseId());
        assignmentDateTextView.setText(assignment.getDate());
    }

}
