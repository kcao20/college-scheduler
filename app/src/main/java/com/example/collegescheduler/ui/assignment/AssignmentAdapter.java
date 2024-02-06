package com.example.collegescheduler.ui.assignment;

import android.graphics.Paint;
import android.os.Handler;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.db.Assignment;
import com.example.collegescheduler.db.Task;
import com.example.collegescheduler.ui.course.CourseFragmentDirections;
import com.example.collegescheduler.ui.home.HomeFragmentDirections;

public class AssignmentAdapter extends ListAdapter<Assignment, AssignmentViewHolder> {

    private final AssignmentViewModel viewModel;
    private final boolean onCoursePage;

    public AssignmentAdapter(AssignmentViewModel viewModel, boolean onCoursePage) {
        super(new AssignmentDiff());
        this.viewModel = viewModel;
        this.onCoursePage = onCoursePage;
    }

    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AssignmentViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        Assignment current = getItem(position);
        holder.bind(current);
        holder.itemView.setOnClickListener(v -> {
            NavDirections action;
            if (onCoursePage) {
                action = CourseFragmentDirections.actionCourseToAssignmentDetails(current.getId(), true);
            } else {
                action = AssignmentListFragmentDirections.navAssignmentToAssignmentDetails(current.getId(), false);
            }
            Navigation.findNavController(v).navigate(action);
        });
    }

    static class AssignmentDiff extends DiffUtil.ItemCallback<Assignment> {

        @Override
        public boolean areItemsTheSame(@NonNull Assignment oldItem, @NonNull Assignment newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Assignment oldItem, @NonNull Assignment newItem) {
            return oldItem.equals(newItem);
        }
    }

}
