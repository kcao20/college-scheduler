package com.example.collegescheduler.ui.assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAssignmentDetailsBinding;
import com.example.collegescheduler.ui.exam.ExamListFragmentDirections;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AssignmentDetailsFragment extends Fragment {

    @NonNull
    FragmentAssignmentDetailsBinding binding;
    private AssignmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAssignmentDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
        FloatingActionButton deleteButton = binding.deleteButton;
        Button editButton = binding.editButton;
        TextView assignmentTitle = binding.assignmentTitle;
        TextView courseTextView = binding.courseTitle;
        TextView dateTextView = binding.dueDate;
        TextView statusEditText = binding.status;

        int assignmentId = getArguments().getInt("assignmentId");

        viewModel.getAssignment(assignmentId).observe(getViewLifecycleOwner(), assignment -> {
            assignmentTitle.setText(assignment.getTitle());
            courseTextView.setText(assignment.getCourseId());
            dateTextView.setText(assignment.getDate().toString());
            statusEditText.setText(assignment.getStatus() ? "Uncompleted" : "Completed");
        });

        deleteButton.setOnClickListener(v -> {
            viewModel.deleteAssignment(assignmentId);
            Navigation.findNavController(v).navigate(R.id.action_assignmentDetails_to_nav_assignments);
        });

        editButton.setOnClickListener(v -> {
            NavDirections action = AssignmentDetailsFragmentDirections.actionAssignmentDetailsToModifyAssignments(true, assignmentId);
            Navigation.findNavController(v).navigate(action);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
