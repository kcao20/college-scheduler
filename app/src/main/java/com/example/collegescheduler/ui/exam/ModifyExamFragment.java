package com.example.collegescheduler.ui.exam;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAddExamBinding;
import com.example.collegescheduler.db.Exam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class ModifyExamFragment extends Fragment {

    private ExamViewModel viewModel;
    private FragmentAddExamBinding binding;
    private Spinner spinnerCourseId;
    private LocalDate selectedDate;
    private LocalTime selectedTime;
    private Exam examToEdit;
    private TextView datePickerButton;
    private TextView timePickerButton;
    private boolean isEditMode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isEditMode = getArguments().getBoolean("editMode", false);
        if (isEditMode) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Edit Exam");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddExamBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(ExamViewModel.class);
        spinnerCourseId = binding.examCourseId;

        String examId = getArguments().getString("examId");

        final EditText examLocation = binding.examLocation.getEditText();

        final EditText examDetails = binding.examDetails.getEditText();

        loadCourseIds();

        datePickerButton = binding.datePickerButton;

        timePickerButton = binding.timePickerButton;

        if (isEditMode) {
            viewModel.getExam(examId).observe(getViewLifecycleOwner(), exam -> {
                if (exam != null) {
                    examToEdit = exam;
                    examLocation.setText(examToEdit.getExamLocation());
                    selectedDate = examToEdit.getDate();
                    selectedTime = examToEdit.getTime();
                    datePickerButton.setText(selectedDate.toString());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
                    String formattedTime = selectedTime.format(formatter);
                    timePickerButton.setText(formattedTime);
                    examDetails.setText(examToEdit.getExamDetails());
                }
            });
        }

        datePickerButton.setOnClickListener(view -> showDatePickerDialog());

        timePickerButton.setOnClickListener(view -> showTimePickerDialog());

        Button saveButton = binding.saveExamButton;

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = examLocation.getText().toString();
                String courseId = spinnerCourseId.getSelectedItem().toString();
                String details = examDetails.getText().toString();

                if (location.trim().isEmpty()) {
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
                            examToEdit.setExamDetails(details);
                            viewModel.updateExam(examToEdit);
                            Toast.makeText(requireContext(), "Exam updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Exam exam = new Exam(location, selectedDateTime, courseId, details);
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

                    if (isEditMode) {
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
                String formattedTime = selectedTime.format(formatter);
                timePickerButton.setText(formattedTime);
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
                datePickerButton.setText(selectedDate.toString());
            }
        }, year, month, day);


        // Show the date picker dialog
        datePickerDialog.show();
    }
}