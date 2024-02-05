package com.example.collegescheduler.ui.assignment;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.Assignment;
import com.example.collegescheduler.ui.home.HomeFragmentDirections;

public class AssignmentAdapter extends ListAdapter<Assignment, AssignmentViewHolder> {

    protected AssignmentAdapter(@NonNull DiffUtil.ItemCallback<Assignment> diffCallback) {
        super(diffCallback);
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
            NavDirections action = AssignmentListDirections.navAssignmentToAssignmentDetails(current.getId());
            Navigation.findNavController(v).navigate(action);
        });
    }

    static class AssignmentDiff extends DiffUtil.ItemCallback<Assignment> {

        @Override
        public boolean areItemsTheSame(@NonNull Assignment oldItem, @NonNull Assignment newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Assignment oldItem, @NonNull Assignment newItem) {
            return oldItem.equals(newItem);
        }
    }

}
