package com.example.collegescheduler.ui.toDoList;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.collegescheduler.db.Task;
import com.example.collegescheduler.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends ListAdapter<Task, TaskViewHolder> {
    private final TaskViewModel taskViewModel;
    private final Context context;

    public TaskAdapter(Context context, TaskViewModel taskViewModel) {
        super(new TaskDiffCallback());
        this.context = context;
        this.taskViewModel = taskViewModel;
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

        viewHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int position = viewHolder.getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Task task = getItem(position);
                taskViewModel.updateTaskCompletionStatus(task, isChecked);

                // Update the strikethrough flag based on the new completion status
                if (isChecked) {
                    viewHolder.textViewTaskTitle.setPaintFlags(viewHolder.textViewTaskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    viewHolder.textViewTaskTitle.setPaintFlags(viewHolder.textViewTaskTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = getItem(position);
        holder.textViewTaskTitle.setText(task.getTaskTitle());
        holder.checkBox.setChecked(task.isCompleted());

        //Initial UI loading
        if (task.isCompleted()) {
            // If completed, apply the strikethrough to the text
            holder.textViewTaskTitle.setPaintFlags(holder.textViewTaskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            // If not completed, remove the strikethrough
            holder.textViewTaskTitle.setPaintFlags(holder.textViewTaskTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    private void showPopupForTask(int position) {
        Task task = getItem(position);
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
            notifyItemChanged(position);

            dialog.dismiss();
        });

        buttonDelete.setOnClickListener(v -> {
            taskViewModel.deleteTask(task);
            dialog.dismiss();
        });

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();
    }

    static class TaskDiffCallback extends DiffUtil.ItemCallback<Task> {

        @Override
        public boolean areItemsTheSame(Task oldItem, Task newItem) {
            // Check if the IDs are the same
            return oldItem.getTaskid() == newItem.getTaskid();
        }

        @Override
        public boolean areContentsTheSame(Task oldItem, Task newItem) {
            // Check if all the fields are the same
            return oldItem.isCompleted() == newItem.isCompleted() &&
                    oldItem.getTaskTitle().equals(newItem.getTaskTitle()) &&
                    oldItem.getTaskDescription().equals(newItem.getTaskDescription());
        }
    }
}
