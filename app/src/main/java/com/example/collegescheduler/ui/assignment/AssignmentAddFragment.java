package com.example.collegescheduler.ui.assignment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAssignmentAddBinding;
import com.example.collegescheduler.db.Assignment;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class AssignmentAddFragment extends Fragment {

    private AssignmentViewModel viewModel;
    private FragmentAssignmentAddBinding binding;
    private EditText titleEditText;
    private Button dateSelector;
    private Spinner courseSpinner;
    private LocalDate selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAssignmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);

        Button addButton = root.findViewById(R.id.assignment_add_course);
        titleEditText = binding.inputTitle.getEditText();
        dateSelector = root.findViewById(R.id.inputDate);
        courseSpinner = root.findViewById(R.id.inputCourse);

        loadCourseIds();

        addButton.setOnClickListener(this::add);

        dateSelector.setOnClickListener(view -> showDatePickerDialog());

        return root;
    }

    private void add(View v) {
        String courseId = (courseSpinner.getSelectedItem().toString());
        Assignment newAssignment = new Assignment(titleEditText.getText().toString(), dateSelector.getText().toString(), courseId);
        viewModel.createAssignment(newAssignment);
        Toast.makeText(requireContext(), "Assignment saved successfully", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(v).navigate(R.id.nav_modifyAssignment_to_nav_assignment);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void loadCourseIds() {
        viewModel.getAllCourseIds().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> courseIds) {
                if (courseIds != null) {
                    // Add "Select Course" option
                    courseIds.add(0, "Select Course");

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, courseIds);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    courseSpinner.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
