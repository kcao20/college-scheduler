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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentModifyAssignmentBinding;
import com.example.collegescheduler.db.Assignment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class ModifyAssignmentFragment extends Fragment {

    private AssignmentViewModel viewModel;
    private FragmentModifyAssignmentBinding binding;
    private EditText title;
    private EditText description;
    private TextView dateSelector;
    private Spinner courseSpinner;
    private LocalDate selectedDate;
    private Assignment assignmentToEdit;
    private boolean isEditMode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isEditMode = getArguments().getBoolean("editMode", false);
        if (isEditMode) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Edit Assignment");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentModifyAssignmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);

        int id = getArguments().getInt("id");

        Button addButton = root.findViewById(R.id.assignment_add_course);
        title = binding.inputTitle.getEditText();
        description = binding.inputDescription.getEditText();
        dateSelector = root.findViewById(R.id.inputDate);
        courseSpinner = root.findViewById(R.id.inputCourse);

        loadCourseIds();

        if (isEditMode) {
            addButton.setText("Save");
            viewModel.getAssignment(id).observe(getViewLifecycleOwner(), assignment -> {
                if (assignment != null) {
                    assignmentToEdit = assignment;
                    title.setText(assignment.getTitle());
                    description.setText(assignment.getDescription());
                    selectedDate = assignment.getDate();
                    dateSelector.setText(selectedDate.toString());
                }
            });
        }

        dateSelector.setOnClickListener(view -> showDatePickerDialog());
        addButton.setOnClickListener(this::add);

        return root;
    }

    private void add(View v) {
        String courseId = (courseSpinner.getSelectedItem().toString());
        if (isEditMode) {
            assignmentToEdit.setCourseId(courseId);
            assignmentToEdit.setTitle(title.getText().toString());
            assignmentToEdit.setDescription(description.getText().toString());
            assignmentToEdit.setDate(selectedDate);
            viewModel.updateAssignment(assignmentToEdit);
            Toast.makeText(requireContext(), "Assignment edited successfully", Toast.LENGTH_SHORT).show();
            NavDirections action = ModifyAssignmentFragmentDirections.actionModifyAssignmentsToAssignmentDetails(assignmentToEdit.getId());
            Navigation.findNavController(v).navigate(action);
        } else {
            Assignment newAssignment = new Assignment(title.getText().toString(), description.getText().toString(), selectedDate, courseId);
            viewModel.createAssignment(newAssignment);
            Toast.makeText(requireContext(), "Assignment added successfully", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigate(R.id.nav_modifyAssignment_to_nav_assignment);
        }
    }

    private void showDatePickerDialog() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (isEditMode) {
            year = assignmentToEdit.getDate().getYear();
            month = assignmentToEdit.getDate().getMonthValue() - 1;
            day = assignmentToEdit.getDate().getDayOfMonth();
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
                    }
                },
                year, month, day);

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

                    if (isEditMode) {
                        int position = courseIds.indexOf(assignmentToEdit.getCourseId());
                        courseSpinner.setSelection(position);
                    }
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
