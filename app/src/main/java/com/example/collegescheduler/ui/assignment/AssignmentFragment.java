package com.example.collegescheduler.ui.assignment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.Assignment;

import java.util.ArrayList;
import java.util.List;

public class AssignmentFragment extends Fragment {

    private AssignmentViewModel assignmentViewModel;
    private Assignment assignment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_assignment, container, false);

        GridView gridView = root.findViewById(R.id.assignment_display);
        Button addButton = root.findViewById(R.id.add_button);
        Button editButton = root.findViewById(R.id.edit_button);
        Button deleteButton = root.findViewById(R.id.delete_button);
        TextView titleTextView = root.findViewById(R.id.assignment_display_title);
        TextView dateTextView = root.findViewById(R.id.assignment_display_date);
        TextView courseTextView = root.findViewById(R.id.assignment_display_course);

        assignment = new Assignment(null, null, null, null); // Implement getAssignmentData() method
        
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAssignment();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteAssignment();
            }
        });

        return root;
    }

    private void addAssignment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Assignment");

        // Set up the layout for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_assignment, null);
        builder.setView(dialogView);

        // Reference the EditText fields in the layout
        EditText titleEditText = dialogView.findViewById(R.id.editTextTitle);
        EditText courseEditText = dialogView.findViewById(R.id.editTextCourse);
        EditText dateEditText = dialogView.findViewById(R.id.editTextDate);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve the entered values
                String title = titleEditText.getText().toString();
                String course = courseEditText.getText().toString();
                String date = dateEditText.getText().toString();

                // Validate and process the entered values as needed
                if (!title.isEmpty() && !course.isEmpty() && !date.isEmpty()) {
                    // Call a method to handle the addition with the entered values
                    handleAddition(title, course, date);
                }
            }
        });
    }

    private void deleteAssignment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Enter assignment id");

        // Set up the input
        final EditText input = new EditText(requireContext());
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String number = input.getText().toString();
                // Validate and process the entered number as needed
                if (!number.isEmpty()) {
                    // Call a method to handle the deletion with the entered number
                    handleDeletion(Integer.parseInt(number));
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel(); // Cancel the dialog if Cancel is clicked
            }
        });

        builder.show();
    }

    private void handleDeletion(int numberToDelete) {
        // delete assignment
    }

    private List<Assignment> getAssignmentData() {
        // Implement logic to get your assignment data from ViewModel or elsewhere
        // For now, I'll return an empty list. Replace it with your actual data fetching logic.
        return new ArrayList<>();
    }
}