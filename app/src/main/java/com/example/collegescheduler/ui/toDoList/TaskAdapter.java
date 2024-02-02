package com.example.collegescheduler.ui.toDoList;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.collegescheduler.db.Task;
import com.example.collegescheduler.R;
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
        View customView = LayoutInflater.from(context).inflate(R.layout.custom_task_dialog_layout, null);

        TextView title = customView.findViewById(R.id.titleTextView);
        TextView description = customView.findViewById(R.id.descriptionTextView);
        title.setText(task.getTaskTitle());
        description.setText(task.getTaskDescription());

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialogStyle);
        builder.setView(customView);
        builder.setPositiveButton("Edit", (dialog, which) -> showEditDialog(task));
        builder.setNegativeButton("Delete", (dialog, which) -> taskViewModel.deleteTask(task));
        builder.show();
    }

    private void showEditDialog(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Task");

        // Create a layout for the dialog
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Set up the input fields
        final EditText inputTitle = new EditText(context);
        inputTitle.setHint("Task Title");
        inputTitle.setText(task.getTaskTitle());
        layout.addView(inputTitle);

        final EditText inputDescription = new EditText(context);
        inputDescription.setHint("Task Description");
        inputDescription.setText(task.getTaskDescription());
        layout.addView(inputDescription);

        builder.setView(layout);

        builder.setPositiveButton("Save", (dialog, which) -> {
            // Handle the edited input when the user clicks "Save"
            String newTitle = inputTitle.getText().toString();
            String newDescription = inputDescription.getText().toString();

            // Update the task or perform other actions
            task.setTaskTitle(newTitle);
            task.setTaskDescription(newDescription);
            taskViewModel.updateTask(task);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
