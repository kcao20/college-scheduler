package com.example.collegescheduler.ui.assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentHomeBinding;
import com.example.collegescheduler.db.Assignment;

public class AssignmentDetailsFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Assignment assignment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_assignment_details, container, false);

        Button deleteButton = root.findViewById(R.id.delete_assignment_button);
        Button editButton = root.findViewById(R.id.assignment_edit);
        TextView assignmentTitleTextView = root.findViewById(R.id.assignment_title);
        TextView courseTextView = root.findViewById(R.id.assignment_course_title);
        TextView dateTextView = root.findViewById(R.id.assignment_due_date_title);
        TextView statusEditText = root.findViewById(R.id.assignment_status);

        // Set click listeners for navigation buttons
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignmentAdapter.deleteData(assignment.getAid());
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveAssignmentEditPage();
            }
        });


        return root;
    }

    private void moveAssignmentEditPage() {
        AssignmentEditFragment assignmentEditFragment = new AssignmentEditFragment();

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.fragment_asssignment_editpage, assignmentEditFragment).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
