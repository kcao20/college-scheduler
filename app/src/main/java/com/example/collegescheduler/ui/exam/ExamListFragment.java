package com.example.collegescheduler.ui.exam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.MainActivity;
import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentExamListBinding;
import com.example.collegescheduler.db.Exam;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Comparator;
import java.util.List;

public class ExamListFragment extends Fragment {

    private FragmentExamListBinding binding;
    private RecyclerView recyclerView;
    private ExamListAdapter examListAdapter;
    private ExamViewModel examViewModel;
    private Spinner spinnerCourseId;
  
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExamListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
      
        spinnerCourseId = binding.examCourseId;

        FloatingActionButton addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener(v -> {
            NavDirections action = ExamListFragmentDirections.actionNavExamToModifyExam(false, null);
            Navigation.findNavController(v).navigate(action);
        });

        recyclerView = view.findViewById(R.id.examList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        examViewModel = new ViewModelProvider(this).get(ExamViewModel.class);

        examListAdapter = new ExamListAdapter(examViewModel);
        recyclerView.setAdapter(examListAdapter);

        loadCourseIds();

        spinnerCourseId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCourseId = (String) parentView.getItemAtPosition(position);

                if (position == 0) {
                    examViewModel.getAllExams().observe(getViewLifecycleOwner(), examList -> {
                        if (!examList.isEmpty()) {
                            binding.youdongxi.setVisibility(View.GONE);
                        }
                        sortByTime(examList);
                        examListAdapter.submitList(examList);
                    });
                } else {
                    examViewModel.getAllExamsWithCourseId(selectedCourseId).observe(getViewLifecycleOwner(), examList -> {
                        if (!examList.isEmpty()) {
                            binding.youdongxi.setVisibility(View.GONE);
                        }
                        sortByTime(examList);
                        examListAdapter.submitList(examList);
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void sortByTime(List<Exam> examsList) {
        examsList.sort(Comparator.comparing(Exam::getDateTime));
    }

    private void loadCourseIds() {
        examViewModel.getAllCourseIds().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> courseIds) {
                if (courseIds != null) {
                    // Add "Select Course" option
                    courseIds.add(0, "All Courses");

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, courseIds);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCourseId.setAdapter(adapter);
                    spinnerCourseId.setSelection(0);
                }
            }
        });
    }
  
}
