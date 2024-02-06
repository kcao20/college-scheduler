package com.example.collegescheduler.ui.assignment;

import android.os.Handler;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.db.Assignment;

public class AssignmentAdapter extends ListAdapter<Assignment, AssignmentViewHolder> {

    private final AssignmentViewModel viewModel;

    protected AssignmentAdapter(@NonNull DiffUtil.ItemCallback<Assignment> diffCallback, AssignmentViewModel viewModel) {
        super(diffCallback);
        this.viewModel = viewModel;
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
            NavDirections action = AssignmentListFragmentDirections.navAssignmentToAssignmentDetails(current.getId());
            Navigation.findNavController(v).navigate(action);
        });
        holder.getStatusCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            int adapterPosition = holder.getBindingAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Assignment clickedAssignment = getItem(adapterPosition);
                clickedAssignment.setStatus(isChecked);
                viewModel.updateAssignment(clickedAssignment);
                new Handler().post(() -> notifyItemChanged(adapterPosition));
            }
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
