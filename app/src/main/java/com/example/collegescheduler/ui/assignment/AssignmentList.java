package com.example.collegescheduler.ui.assignment;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.ui.calendar.CalendarAdapter;
import com.example.collegescheduler.ui.home.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssignmentList extends Fragment {
    //private List<Assignment> assignmentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_assignment, container, false);

        ScrollView scrollView = root.findViewById(R.id.assignment_list_scrollview);
        Button addButton = root.findViewById(R.id.assignment_add);
        Button backButton = root.findViewById(R.id.assignment_edit_back);
        TextView titleTextView = root.findViewById(R.id.assignment_title);
        RecyclerView assignmentList = root.findViewById(R.id.assignment_list_recyclerview);


        //ArrayList<Assignment> mainAssignmentList = new ArrayList<>();

        // Set click listeners for navigation buttons
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAdd();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToMainPage();
            }
        });


        return root;
    }

    private void moveToMainPage() {
        HomeFragment homeFragment = new HomeFragment();

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.drawer_layout, homeFragment).commit();
    }

    private void goToAdd() {
        AssignmentAddFragment assignmentAddFragment = new AssignmentAddFragment();

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.drawer_layout, assignmentAddFragment).commit();
    }
}
