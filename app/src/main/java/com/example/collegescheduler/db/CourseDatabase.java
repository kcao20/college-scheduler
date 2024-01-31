package com.example.collegescheduler.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Course.class}, version = 2, exportSchema = false)
public abstract class CourseDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile CourseDatabase INSTANCE;

    public static CourseDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (CourseDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CourseDatabase.class, "CourseDB").build();
                }
            }
        }
        return INSTANCE;
    }

    // Ensure there is only one instance of database
    public abstract CourseDAO courseDao();

}
