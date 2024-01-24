package com.example.collegescheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CourseTime {

    private LocalDateTime[] occurrences;

    public CourseTime() {
        occurrences = new LocalDateTime[7];
    }
    public void addTimeSlot(Integer dayOfWeek, LocalDateTime time) {
        occurrences[dayOfWeek] = time;
    }

    public LocalDateTime[] getOccurrences() {
        return occurrences;
    }

}
