package com.example.collegescheduler.ui.course;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentCourseBinding;
import com.example.collegescheduler.db.Assignment;
import com.example.collegescheduler.db.Exam;
import com.example.collegescheduler.ui.assignment.AssignmentAdapter;
import com.example.collegescheduler.ui.assignment.AssignmentViewModel;
import com.example.collegescheduler.ui.exam.ExamListAdapter;
import com.example.collegescheduler.ui.exam.ExamViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class CourseFragment extends Fragment {

    private FragmentCourseBinding binding;
    private String courseId;

    private ExamListAdapter examListAdapter;
    private ExamViewModel examViewModel;
    private AssignmentAdapter assignmentAdapter;
    private AssignmentViewModel assignmentViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseId = getArguments().getString("courseId");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(courseId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CourseViewModel courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        examViewModel = new ViewModelProvider(this).get(ExamViewModel.class);
        examListAdapter = new ExamListAdapter(examViewModel, getContext(), true);
        RecyclerView exams = view.findViewById(R.id.exams);
        exams.setLayoutManager(new LinearLayoutManager(requireContext()));
        exams.setAdapter(examListAdapter);
        assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
        assignmentAdapter = new AssignmentAdapter(assignmentViewModel, true);
        RecyclerView assignments = view.findViewById(R.id.assignments);
        assignments.setLayoutManager(new LinearLayoutManager(requireContext()));
        assignments.setAdapter(assignmentAdapter);

        TextView courseInfo = binding.courseInfo;
        TextView courseTime = binding.courseTime;
        TextView courseDescription = binding.courseDescription;
        FloatingActionButton deleteButton = binding.deleteButton;
        Button editButton = binding.editButton;

        TextView examText = binding.examText;
        TextView assignmentText = binding.assignmentText;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        String[] daysOfWeek = {"M", "T", "W", "R", "F"};

        courseViewModel.getCourse(courseId).observe(getViewLifecycleOwner(), course -> {
            // Grab & Set Course Info
            int[] repeat = course.getRepeat();
            StringBuilder repeatStringBuilder = new StringBuilder("Repeats every ");
            for (int i = 0; i < repeat.length; i++) {
                if (repeat[i] == 1) {
                    repeatStringBuilder.append(daysOfWeek[i]);
                }
            }
            courseInfo.setText(String.format("%s taught by %s",
                    course.getCourseTitle(),
                    course.getInstructor()));
            String timeView = repeatStringBuilder + " at ";
            courseTime.setText(String.format("%s",
                    timeView + course.getCourseTime().format(formatter)));
            courseDescription.setText(course.getCourseDescription());

            // Grab & Set Exams
            examViewModel.getAllExamsWithCourseId(courseId).observe(getViewLifecycleOwner(), examList -> {
                examText.setVisibility(examList.isEmpty() ? View.VISIBLE : View.GONE);
                examList.sort(Comparator.comparing(Exam::getDateTime));
                examListAdapter.submitList(examList);
            });

            // Grab & Set Assignments
            assignmentViewModel.getAllAssignmentsWithCourseId(courseId).observe(getViewLifecycleOwner(), assignmentList -> {
                assignmentText.setVisibility(assignmentList.isEmpty() ? View.VISIBLE : View.GONE);
                assignmentList.sort(Comparator.comparing(Assignment::getDate));
                assignmentAdapter.submitList(assignmentList);
            });
        });

        deleteButton.setOnClickListener(v -> {
            onDelete(view, courseViewModel);
        });

        editButton.setOnClickListener(v -> {
            CourseFragmentDirections.ActionCourseToEditCourse action = CourseFragmentDirections.actionCourseToEditCourse(courseId);
            Navigation.findNavController(view).navigate(action);
        });
    }

    private void onDelete(@NonNull View view, CourseViewModel courseViewModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Are you sure you want to delete this course?").setPositiveButton("Delete", (dialog, which) -> {
            courseViewModel.delete(courseId);
            Navigation.findNavController(view).navigate(R.id.action_course_to_nav_home);
        }).setNegativeButton("Cancel", (dialog, which) -> {
        }).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
