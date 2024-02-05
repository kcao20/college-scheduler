package com.example.collegescheduler.ui.assignment;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.Course;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentViewHolder> {

    public static ArrayList<Assignment> assignmentList;
    public AssignmentAdapter(ArrayList<Assignment> assignmentList) {
        AssignmentAdapter.assignmentList = assignmentList;
    }

    public Object getItem(int position) { return assignmentList.get(position); }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the item layout
        View assignmentView = inflater.inflate(R.layout.fragment_assignment_details, parent, false);

        return new AssignmentViewHolder(assignmentView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        Assignment assignment = assignmentList.get(position);
        holder.bind(assignment);
    }

    public long getItemId(int position) { return position; }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public static void addData(Assignment newAssignment) {
        assignmentList.add(newAssignment);
    }

    public static void deleteData(int aid) {
        for (int i = 0; i < assignmentList.size(); i++) {
            if (assignmentList.get(i).getAid() == aid) {
                assignmentList.remove(i);
                break;
            }
        }
    }

    public static void editDate(int aid, String date) {
        for (int i = 0; i < assignmentList.size(); i++) {
            if (assignmentList.get(i).getAid() == aid) {
                assignmentList.get(i).setDate(date);
                break;
            }
        }
    }

    public static void editCourse(int aid, Course course) {
        for (int i = 0; i < assignmentList.size(); i++) {
            if (assignmentList.get(i).getAid() == aid) {
                assignmentList.get(i).setCourse(course);
                break;
            }
        }
    }

    public static void editTitle(int aid, String title) {
        for (int i = 0; i < assignmentList.size(); i++) {
            if (assignmentList.get(i).getAid() == aid) {
                assignmentList.get(i).setTitle(title);
                break;
            }
        }
    }

    public static void editStatus(int aid, String status) {
        for (int i = 0; i < assignmentList.size(); i++) {
            if (assignmentList.get(i).getAid() == aid) {
                assignmentList.get(i).setStatus(status);
                break;
            }
        }
    }
}
