package com.example.collegescheduler.ui.assignment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.Assignment;

public class AssignmentViewHolder extends RecyclerView.ViewHolder {
    private TextView assignmentTitleTextView;
    private TextView assignmentCourseTextView;
    private TextView assignmentDateTextView;

    public AssignmentViewHolder(@NonNull View itemView) {
        super(itemView);
        assignmentTitleTextView = itemView.findViewById(R.id.assignment_title);
        assignmentCourseTextView = itemView.findViewById(R.id.assignment_course_title);
        assignmentDateTextView = itemView.findViewById(R.id.assignment_edit_due_date);
    }

    public void bind(Assignment assignment) {
        assignmentTitleTextView.setText( assignment.getAssignmentTitle());
        assignmentCourseTextView.setText(assignment.getCourse());
        assignmentDateTextView.setText(assignment.getDate());
    }
}
