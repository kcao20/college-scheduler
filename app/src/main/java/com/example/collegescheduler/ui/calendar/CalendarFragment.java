package com.example.collegescheduler.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.collegescheduler.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private Calendar currentCalendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        GridView gridView = root.findViewById(R.id.calendar_grid);
        Button prevButton = root.findViewById(R.id.calendar_prev_button);
        Button nextButton = root.findViewById(R.id.calendar_next_button);
        Button todayButton = root.findViewById(R.id.date_display_today);
        TextView yearTextView = root.findViewById(R.id.date_display_year);
        TextView dayOfWeekView = root.findViewById(R.id.date_display_day);
        TextView dateDisplay = root.findViewById(R.id.date_display_date);

        // Initialize the calendar to the current month
        currentCalendar = Calendar.getInstance();

        // Set the adapter for the current month
        updateCalendar(gridView, yearTextView, dayOfWeekView, dateDisplay);

        // Set click listeners for navigation buttons
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToPreviousMonth();
                updateCalendar(gridView, yearTextView, dayOfWeekView, dateDisplay);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNextMonth();
                updateCalendar(gridView, yearTextView, dayOfWeekView, dateDisplay);
            }
        });

        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalendar = Calendar.getInstance();
                updateCalendar(gridView, yearTextView, dayOfWeekView, dateDisplay);
            }
        });



        return root;
    }

    private void moveToPreviousMonth() {
        currentCalendar.add(Calendar.MONTH, -1);
    }

    private void moveToNextMonth() {
        currentCalendar.add(Calendar.MONTH, 1);
    }

    private void updateCalendar(GridView gridView, TextView yearTextView, TextView dow, TextView dateView) {
        // Get the days of the current month
        List<Date> days = getDaysOfMonth(currentCalendar);
        int year = currentCalendar.get(Calendar.YEAR);
        int month = currentCalendar.get(Calendar.MONTH);

        yearTextView.setText(String.valueOf(year));

        SimpleDateFormat sdfDayOfWeek = new SimpleDateFormat("EEEE", Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd", Locale.getDefault());
        dow.setText(sdfDayOfWeek.format(currentCalendar.getTime()));
        dateView.setText(sdf.format(currentCalendar.getTime()));


        // Create and set the adapter
        CalendarAdapter calendarAdapter = new CalendarAdapter(requireContext(), days, currentCalendar);
        gridView.setAdapter(calendarAdapter);
    }

    public List<Date> getDaysOfMonth(Calendar calendar) {
        if (calendar == null) {
            calendar = Calendar.getInstance(); // Default to the current month
        }

        // Create a copy of the calendar to avoid modifying the original instance
        Calendar calendarCopy = (Calendar) calendar.clone();

        List<Date> days = new ArrayList<>();
        calendarCopy.set(Calendar.DAY_OF_MONTH, 1); // Set to the first day of the month

        int firstDayOfWeek = calendarCopy.get(Calendar.DAY_OF_WEEK);
        int difference = firstDayOfWeek - calendarCopy.getFirstDayOfWeek();

        if (difference < 0) {
            difference += 7;
        }

        // Adjust the calendar to the beginning of the week
        calendarCopy.add(Calendar.DAY_OF_MONTH, -difference);

        int maxDay = calendarCopy.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Iterate for the correct number of days in the month
        for (int i = 0; i < 42; i++) { // 42 is the maximum number of cells in a 7x6 grid
            days.add(calendarCopy.getTime());
            calendarCopy.add(Calendar.DAY_OF_MONTH, 1);
        }

        return days;
    }
}
