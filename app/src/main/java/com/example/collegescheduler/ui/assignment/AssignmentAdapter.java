package com.example.collegescheduler.ui.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;

import java.util.List;

public class AssignmentAdapter extends BaseAdapter {

    private final List<Assignment> assignmentList;
    private Assignment assignment;
    private Context context;

    public AssignmentAdapter(Context context, List<Assignment> assignmentList, Assignment assignment) {
        this.context = context;
        this.assignmentList = assignmentList;
        this.assignment = assignment;
    }

    public int getCount() { return assignmentList.size(); }

    public Object getItem(int position) { return assignmentList.get(position); }

    public long getItemId(int position) { return position; }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_assignment, parent, false);

        GridView gridView = view.findViewById(R.id.assignment_display);
        Button addButton = view.findViewById(R.id.add_button);
        Button editButton = view.findViewById(R.id.edit_button);
        Button deleteButton = view.findViewById(R.id.delete_button);
        TextView titleTextView = view.findViewById(R.id.assignment_display_title);
        TextView dateTextView = view.findViewById(R.id.assignment_display_date);
        TextView courseTextView = view.findViewById(R.id.assignment_display_course);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        Assignment assignment = assignmentList.get(position);

        // Bind assignment data to the ViewHolder
        holder.titleTextView.setText(assignment.getAssignmentTitle());
        holder.titleTextView.setText(assignment.getDate().toString());
        holder.titleTextView.setText(assignment.getCourse().getCourseTitle());
        holder.titleTextView.setText(assignment.getStatus());
    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            // ... (initialize other views as needed)
        }
    }
}
