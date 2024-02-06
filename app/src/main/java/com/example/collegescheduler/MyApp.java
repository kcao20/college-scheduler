package com.example.collegescheduler;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.collegescheduler.db.CourseDatabase;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
