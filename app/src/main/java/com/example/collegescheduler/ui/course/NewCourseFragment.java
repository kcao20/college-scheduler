package com.example.collegescheduler.ui.course;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAddCourseBinding;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;

public class NewCourseFragment extends Fragment {

    private FragmentAddCourseBinding binding;

    private TextView startTime;
    private TextView endTime;
    private LocalTime selectedStartTime;
    private LocalTime selectedEndTime;
    private final int[] repeat = new int[5];

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NewCourseViewModel newCourseViewModel = new ViewModelProvider(this).get(NewCourseViewModel.class);

        binding = FragmentAddCourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EditText courseID = binding.courseID.getEditText();
        final EditText courseTitle = binding.courseTitle.getEditText();
        final EditText courseDescription = binding.courseDescription.getEditText();
        final EditText courseInstructor = binding.courseInstructor.getEditText();
        startTime = binding.timePicker;
        endTime = binding.endTimePicker;
        final Button saveButton = binding.saveButton;

        startTime.setOnClickListener(view -> showTimePickerDialog(true));
        endTime.setOnClickListener(view -> showTimePickerDialog(false));

        CheckBox checkboxMonday = root.findViewById(R.id.checkbox_monday);
        CheckBox checkboxTuesday = root.findViewById(R.id.checkbox_tuesday);
        CheckBox checkboxWednesday = root.findViewById(R.id.checkbox_wednesday);
        CheckBox checkboxThursday = root.findViewById(R.id.checkbox_thursday);
        CheckBox checkboxFriday = root.findViewById(R.id.checkbox_friday);

        CompoundButton.OnCheckedChangeListener checkboxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.getId() == R.id.checkbox_monday) {
                    repeat[0] = isChecked ? 1 : 0;
                } else if (buttonView.getId() == R.id.checkbox_tuesday) {
                    repeat[1] = isChecked ? 1 : 0;
                } else if (buttonView.getId() == R.id.checkbox_wednesday) {
                    repeat[2] = isChecked ? 1 : 0;
                } else if (buttonView.getId() == R.id.checkbox_thursday) {
                    repeat[3] = isChecked ? 1 : 0;
                } else if (buttonView.getId() == R.id.checkbox_friday) {
                    repeat[4] = isChecked ? 1 : 0;
                }
            }
        };

        checkboxMonday.setOnCheckedChangeListener(checkboxListener);
        checkboxTuesday.setOnCheckedChangeListener(checkboxListener);
        checkboxWednesday.setOnCheckedChangeListener(checkboxListener);
        checkboxThursday.setOnCheckedChangeListener(checkboxListener);
        checkboxFriday.setOnCheckedChangeListener(checkboxListener);

        saveButton.setOnClickListener(v -> {
            String cID = courseID.getText().toString();
            String cTitle = courseTitle.getText().toString();
            String cDescription = courseDescription.getText().toString();
            String cInstructor = courseInstructor.getText().toString();
            if (cID.trim().isEmpty()) {
                Toast.makeText(requireContext(), "Course ID can not be blank", Toast.LENGTH_SHORT).show();
            } else if (cTitle.trim().isEmpty()) {
                Toast.makeText(requireContext(), "Course title can not be blank", Toast.LENGTH_SHORT).show();
            } else if (cInstructor.trim().isEmpty()) {
                Toast.makeText(requireContext(), "Course instructor can not be blank", Toast.LENGTH_SHORT).show();
            } else if (selectedStartTime == null || selectedEndTime == null) {
                Toast.makeText(requireContext(), "Select start and end times!", Toast.LENGTH_SHORT).show();
            } else if (Arrays.stream(repeat).allMatch(element -> element == 0)) {
                Toast.makeText(requireContext(), "Courses repeat!", Toast.LENGTH_SHORT).show();
            } else {
                newCourseViewModel.checkAndSaveCourse(cID, cTitle, cDescription, cInstructor, selectedStartTime, selectedEndTime, repeat).observe(getViewLifecycleOwner(), courseSaved -> {
                    if (courseSaved) {
                        Toast.makeText(requireContext(), "Course saved successfully", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigate(R.id.action_add_course_to_nav_home);
                    } else {
                        Toast.makeText(requireContext(), "Course already exists", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return root;
    }

    private void showTimePickerDialog(Boolean start) {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (start) {
                    selectedStartTime = LocalTime.of(hourOfDay, minute);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
                    String formattedTime = selectedStartTime.format(formatter);
                    startTime.setText(formattedTime);
                } else {
                    selectedEndTime = LocalTime.of(hourOfDay, minute);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
                    String formattedTime = selectedEndTime.format(formatter);
                    endTime.setText(formattedTime);
                }

            }
        }, currentHour, currentMinute, false);

        // Show the time picker dialog
        timePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}