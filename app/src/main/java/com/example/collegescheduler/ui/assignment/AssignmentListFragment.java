package com.example.collegescheduler.ui.assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAssignmentListBinding;
import com.example.collegescheduler.db.Assignment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Comparator;
import java.util.List;

public class AssignmentListFragment extends Fragment {

    private AssignmentViewModel viewModel;
    private FragmentAssignmentListBinding binding;
    private Spinner spinnerCourseId;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAssignmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);

        spinnerCourseId = binding.assignmentCourseId;

        FloatingActionButton addButton = binding.assignmentAdd;
        addButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_assignment_to_modifyAssignment));

        RecyclerView recyclerView = root.findViewById(R.id.assignment_list_recyclerview);
        final AssignmentAdapter adapter = new AssignmentAdapter(viewModel, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        LiveData<List<Assignment>> assignments = viewModel.getAllAssignments();
        assignments.observe(getViewLifecycleOwner(), assignment -> {
            if (!assignment.isEmpty()) {
                binding.youdongxi.setVisibility(View.GONE);
            }
            adapter.submitList(assignments.getValue());
        });

        loadCourseIds();

        spinnerCourseId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCourseId = (String) parentView.getItemAtPosition(position);

                if (position == 0) {
                    viewModel.getAllAssignments().observe(getViewLifecycleOwner(), assignmentsList -> {
                        if (!assignmentsList.isEmpty()) {
                            binding.youdongxi.setVisibility(View.GONE);
                        }
                        sortByTime(assignmentsList);
                        adapter.submitList(assignmentsList);
                    });
                } else {
                    viewModel.getAllAssignmentsWithCourseId(selectedCourseId).observe(getViewLifecycleOwner(), assignmentList -> {
                        if (!assignmentList.isEmpty()) {
                            binding.youdongxi.setVisibility(View.GONE);
                        }
                        sortByTime(assignmentList);
                        adapter.submitList(assignmentList);
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return root;
    }

    public void sortByTime(List<Assignment> assignmentLists) {
        assignmentLists.sort(Comparator.comparing(Assignment::getDate));
    }

    private void loadCourseIds() {
        viewModel.getAllCourseIds().observe(getViewLifecycleOwner(), courseIds -> {
            if (courseIds != null) {
                if (!courseIds.get(0).equals("All Courses")) {
                    courseIds.add(0, "All Courses");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, courseIds);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCourseId.setAdapter(adapter);
                spinnerCourseId.setSelection(0);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
