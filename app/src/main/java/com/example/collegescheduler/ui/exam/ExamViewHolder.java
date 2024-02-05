package com.example.collegescheduler.ui.exam;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.CheckBox;
import com.example.collegescheduler.R;
import com.example.collegescheduler.db.Exam;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExamViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewExamLocation;
    public TextView textViewExamDateTime;

    public TextView textViewExamCourseId;

    private ImageButton imageButtonDelete;
    private ImageButton imageButtonEdit;
    private View.OnClickListener deleteButtonClickListener;
    private View.OnClickListener editButtonClickListener;

    public ExamViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewExamLocation = itemView.findViewById(R.id.textViewExamLocation);
        textViewExamDateTime = itemView.findViewById(R.id.textViewExamDateTime);
        textViewExamCourseId = itemView.findViewById(R.id.textViewExamCourseId);
        imageButtonDelete = itemView.findViewById(R.id.imageButtonDelete);
        imageButtonEdit = itemView.findViewById(R.id.imageButtonEdit);


        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteButtonClickListener != null) {
                    deleteButtonClickListener.onClick(v);
                }
            }
        });

        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editButtonClickListener != null) {
                    editButtonClickListener.onClick(v);
                }
            }
        });
    }

    public void setDeleteButtonClickListener(View.OnClickListener deleteButtonClickListener) {
        this.deleteButtonClickListener = deleteButtonClickListener;
    }

    public void setEditButtonClickListener(View.OnClickListener editButtonClickListener) {
        this.editButtonClickListener = editButtonClickListener;
    }

    public void bind(Exam exam) {
        // Bind data to UI elements in ExamViewHolder
        textViewExamLocation.setText(exam.getExamLocation());
        textViewExamDateTime.setText(formatDateTime(exam.getDateTime()));
        textViewExamCourseId.setText(exam.getCourseId());
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        return dateTime.format(formatter);
    }
}
