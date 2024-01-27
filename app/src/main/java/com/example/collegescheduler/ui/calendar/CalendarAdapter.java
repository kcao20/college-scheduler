package com.example.collegescheduler.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.collegescheduler.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;

public class CalendarAdapter extends BaseAdapter {

    private Context context;
    private List<Date> days;
    private Calendar currentDate;

    public CalendarAdapter(Context context, List<Date> days, Calendar currentDate) {
        this.context = context;
        this.days = days;
        this.currentDate = currentDate;
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.calendar_day_cell, parent, false);
        }

        TextView dayTextView = view.findViewById(R.id.dayTextView);

        Date date = days.get(position);
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (localDate.getMonthValue() != currentDate.get(Calendar.MONTH) + 1) {
            dayTextView.setTextColor(ContextCompat.getColor(context, R.color.grey)); // Use your desired color resource
        } else {
            dayTextView.setTextColor(ContextCompat.getColor(context, android.R.color.black)); // Set the text color for days within the current month
        }
        // Set the day in the TextView
        SimpleDateFormat sdf = new SimpleDateFormat("d", Locale.getDefault());
        dayTextView.setText(sdf.format(date));



        return view;
    }
}
