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
import com.example.collegescheduler.databinding.FragmentEditCourseBinding;
import com.example.collegescheduler.db.Course;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;

public class EditCourseFragment extends Fragment {

    private FragmentEditCourseBinding binding;
    private TextView courseTime;
    private LocalTime selectedTime;
    private int[] repeat = new int[5];

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EditCourseViewModel editCourseViewModel = new ViewModelProvider(this).get(EditCourseViewModel.class);

        binding = FragmentEditCourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView courseID = binding.courseID;
        final EditText courseTitle = binding.courseTitle.getEditText();
        final EditText courseDescription = binding.courseDescription.getEditText();
        final EditText courseInstructor = binding.courseInstructor.getEditText();
        final Button editButton = binding.editButton;
        courseTime = binding.timePicker;
        courseID.setText(EditCourseFragmentArgs.fromBundle(getArguments()).getCourseId());

        editCourseViewModel.getCourse(courseID.getText().toString()).observe(getViewLifecycleOwner(), course -> {
            courseTime.setOnClickListener(view -> showTimePickerDialog(course));
            courseTitle.setText(course.getCourseTitle());
            courseDescription.setText(course.getCourseDescription());
            courseInstructor.setText(course.getInstructor());
            selectedTime = course.getCourseTime();
            repeat = course.getRepeat();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            String formattedTime = selectedTime.format(formatter);
            courseTime.setText(formattedTime);

            CheckBox checkboxMonday = root.findViewById(R.id.checkbox_monday);
            CheckBox checkboxTuesday = root.findViewById(R.id.checkbox_tuesday);
            CheckBox checkboxWednesday = root.findViewById(R.id.checkbox_wednesday);
            CheckBox checkboxThursday = root.findViewById(R.id.checkbox_thursday);
            CheckBox checkboxFriday = root.findViewById(R.id.checkbox_friday);
            CheckBox[] checkboxes = {checkboxMonday, checkboxTuesday, checkboxWednesday, checkboxThursday, checkboxFriday};

            for (int i = 0; i < checkboxes.length; i++) {
                checkboxes[i].setChecked(repeat[i] == 1);
            }
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
        });

        editButton.setOnClickListener(v -> {
            String cID = courseID.getText().toString();
            String cTitle = courseTitle.getText().toString();
            String cDescription = courseDescription.getText().toString();
            String cInstructor = courseInstructor.getText().toString();
            if (cID.trim().isEmpty()) {
                Toast.makeText(requireContext(), "Course ID can not be blank", Toast.LENGTH_SHORT).show();
            } else if (selectedTime == null) {
                Toast.makeText(requireContext(), "Select a time!", Toast.LENGTH_SHORT).show();
            } else if (Arrays.stream(repeat).allMatch(element -> element == 0)){
                Toast.makeText(requireContext(), "Courses repeat!", Toast.LENGTH_SHORT).show();
            } else {
                editCourseViewModel.updateCourse(cID, cTitle, cDescription, cInstructor, selectedTime, repeat).observe(getViewLifecycleOwner(), courseUpdated -> {
                    if (courseUpdated) {
                        Toast.makeText(requireContext(), "Course updated successfully", Toast.LENGTH_SHORT).show();
                        EditCourseFragmentDirections.ActionEditCourseToCourse action =
                                EditCourseFragmentDirections.actionEditCourseToCourse(cID);
                        Navigation.findNavController(v).navigate(action);
                    } else {
                        Toast.makeText(requireContext(), "Course already exists", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return root;
    }

    private void showTimePickerDialog(Course course) {
        // Get the current time

        int initialHour = course.getCourseTime().getHour();
        int initialMinute = course.getCourseTime().getMinute();
        selectedTime = LocalTime.of(initialHour, initialMinute);

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectedTime = LocalTime.of(hourOfDay, minute);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
                String formattedTime = selectedTime.format(formatter);
                courseTime.setText(formattedTime);
            }
        }, initialHour, initialMinute, false);

        // Show the time picker dialog
        timePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}