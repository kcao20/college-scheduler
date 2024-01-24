package com.example.collegescheduler.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class}, version = 2)
public abstract class CourseDatabase extends RoomDatabase {

    private static CourseDatabase instance;

    // Ensure there is only one instance of database
    public static synchronized CourseDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), CourseDatabase.class, "CourseDB")
                    // Add any callbacks or migration strategies if needed
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract CourseDAO getCourseDao();

}
