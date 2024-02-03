package com.example.collegescheduler.ui.toDoList;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.collegescheduler.db.Task;
import com.example.collegescheduler.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private List<Task> taskList;
    private final TaskViewModel taskViewModel;
    private final Context context;

    public TaskAdapter(List<Task> taskList, Context context, TaskViewModel taskViewModel) {
        this.taskList = taskList;
        this.context = context;
        this.taskViewModel = taskViewModel;
    }

    public void setTaskList (List<Task> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks, parent, false);

        TaskViewHolder viewHolder = new TaskViewHolder(view);

        viewHolder.setClickListener(v -> {
            int position = viewHolder.getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                showPopupForTask(position);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.textViewTaskTitle.setText(task.getTaskTitle());
        holder.checkBox.setChecked(task.isCompleted());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> taskViewModel.updateTaskCompletionStatus(task, isChecked));

        if (task.isCompleted()) {
            // If completed, apply the strikethrough to the text
            holder.textViewTaskTitle.setPaintFlags(holder.textViewTaskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            // If not completed, remove the strikethrough
            holder.textViewTaskTitle.setPaintFlags(holder.textViewTaskTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    private void showPopupForTask(int position) {
        Task task = taskList.get(position);
        final BottomSheetDialog dialog = new BottomSheetDialog(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_task_dialog_layout, null);
        dialog.setContentView(dialogView);

        EditText title = dialogView.findViewById(R.id.titleTextView);
        EditText description = dialogView.findViewById(R.id.descriptionTextView);
        title.setText(task.getTaskTitle());
        description.setText(task.getTaskDescription());

        Button buttonEdit = dialogView.findViewById(R.id.buttonEdit);
        Button buttonDelete = dialogView.findViewById(R.id.buttonDelete);

        buttonEdit.setOnClickListener(v -> {
            String newTitle = title.getText().toString();
            String newDescription = description.getText().toString();

            // Update the task or perform other actions
            task.setTaskTitle(newTitle);
            task.setTaskDescription(newDescription);
            taskViewModel.updateTask(task);

            dialog.dismiss();
        });

        buttonDelete.setOnClickListener(v -> {
            taskViewModel.deleteTask(task);
            dialog.dismiss();
        });

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
