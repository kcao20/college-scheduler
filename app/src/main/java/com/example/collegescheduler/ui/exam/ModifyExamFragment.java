package com.example.collegescheduler.ui.exam;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAddExamBinding;
import com.example.collegescheduler.db.Exam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

public class ModifyExamFragment extends Fragment {

    private ExamViewModel viewModel;

    private FragmentAddExamBinding binding;

    private Spinner spinnerCourseId;

    private LocalDate selectedDate;

    private LocalTime selectedTime;
    private Exam examToEdit;

    private boolean isEditMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddExamBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(ExamViewModel.class);
        spinnerCourseId = binding.examCourseId;

        isEditMode = getArguments().getBoolean("editMode", false);
        String examId = getArguments().getString("examId");

        final EditText examLocation = binding.examLocation.getEditText();

        loadCourseIds();

        Button datePickerButton = binding.datePickerButton;

        viewModel.getExam(examId).observe(getViewLifecycleOwner(), new Observer<Exam>() {
            @Override
            public void onChanged(Exam exam) {
                if (exam != null && isEditMode) {
                    examToEdit = exam;
                    examLocation.setText(examToEdit.getExamLocation());
                    if (isEditMode) {
                        int year = examToEdit.getDateTime().getYear();
                        int month = examToEdit.getDateTime().getMonthValue();
                        int day = examToEdit.getDateTime().getDayOfMonth();

                        selectedDate = LocalDate.of(year, month, day);

                        int initialHour = examToEdit.getDateTime().toLocalTime().getHour();
                        int initialMinute =examToEdit.getDateTime().toLocalTime().getMinute();
                        selectedTime = LocalTime.of(initialHour, initialMinute);

                    }
                }
            }
        });
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        Button timePickerButton = binding.timePickerButton;
        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        Button saveButton = binding.saveExamButton;

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = examLocation.getText().toString();
                String courseId = spinnerCourseId.getSelectedItem().toString();

                if (location.trim().isEmpty()){
                    Toast.makeText(requireContext(), "Please input a location", Toast.LENGTH_SHORT).show();
                } else if (selectedDate == null || selectedTime == null || location.isEmpty()) {
                    Toast.makeText(requireContext(), "Please select both date and time", Toast.LENGTH_SHORT).show();
                } else if (courseId.equals("Select Course")) {
                    Toast.makeText(requireContext(), "Please select a course", Toast.LENGTH_SHORT).show();
                } else {
                    LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, selectedTime);
                    if (isEditMode) {
                        if (examToEdit != null) {
                            examToEdit.setExamLocation(location);
                            examToEdit.setDateTime(selectedDateTime);
                            examToEdit.setCourseId(courseId);
                            viewModel.updateExam(examToEdit);
                            Toast.makeText(requireContext(), "Exam updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Exam exam = new Exam(location, selectedDateTime, courseId);
                        viewModel.createExam(exam);
                        Toast.makeText(requireContext(), "Exam saved successfully", Toast.LENGTH_SHORT).show();
                    }
                    Navigation.findNavController(view).navigate(R.id.action_add_exam_to_nav_exam);
                }
            }
        });

        return root;
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
                    spinnerCourseId.setAdapter(adapter);

                    if (isEditMode){
                        int position = courseIds.indexOf(examToEdit.getCourseId());
                        spinnerCourseId.setSelection(position);
                    }

            }
        }
        });
    }
    private void showTimePickerDialog() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        int initialHour = isEditMode ? examToEdit.getDateTime().toLocalTime().getHour() : currentHour;
        int initialMinute = isEditMode ? examToEdit.getDateTime().toLocalTime().getMinute() : currentMinute;

        if (isEditMode) {
            selectedTime = LocalTime.of(initialHour, initialMinute);
        }

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectedTime = LocalTime.of(hourOfDay, minute);
            }
        }, initialHour, initialMinute, false);

        // Show the time picker dialog
        timePickerDialog.show();
    }

    private void showDatePickerDialog() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (isEditMode) {
            year = examToEdit.getDateTime().getYear();
            month = examToEdit.getDateTime().getMonthValue() - 1;
            day = examToEdit.getDateTime().getDayOfMonth();
        }

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
                    }
                },
                year, month, day);

        // Show the date picker dialog
        datePickerDialog.show();
    }
}