package com.example.collegescheduler.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.Course;

class HomeViewHolder extends RecyclerView.ViewHolder {
    private final TextView courseID;
    private final TextView courseTitle;

    private HomeViewHolder(View itemView) {
        super(itemView);
        courseID = itemView.findViewById(R.id.courseId);
        courseTitle = itemView.findViewById(R.id.courseTitle);
    }

    static HomeViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.courses, parent, false);
        return new HomeViewHolder(view);
    }

    public void bind(Course course) {
        courseID.setText(course.getCid());
        courseTitle.setText(course.getCourseTitle());
    }

}
