package com.example.collegescheduler.ui.assignment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAssignmentBinding;
import com.example.collegescheduler.db.Assignment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AssignmentList extends Fragment {

    private AssignmentViewModel viewModel;
    private FragmentAssignmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAssignmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
        FloatingActionButton addButton = binding.assignmentAdd;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_assignment_to_modifyAssignment);
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.assignment_list_recyclerview);
        final AssignmentAdapter adapter = new AssignmentAdapter(new AssignmentAdapter.AssignmentDiff(), viewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        LiveData<List<Assignment>> assignments = viewModel.getAllAssignments();
        assignments.observe(getViewLifecycleOwner(), new Observer<List<Assignment>>() {
            @Override
            public void onChanged(List<Assignment> assignment) {
                if (!assignment.isEmpty()) {
                    binding.youdongxi.setVisibility(View.GONE);
                }
                adapter.submitList(assignments.getValue());
            }
        });

        return root;
    }

}
