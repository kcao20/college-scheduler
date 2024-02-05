package com.example.collegescheduler.ui.exam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentExamListBinding;
import com.example.collegescheduler.db.Exam;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ExamListFragment extends Fragment {

    private FragmentExamListBinding binding;
    private RecyclerView recyclerView;
    private ExamListAdapter examListAdapter;

    private ExamViewModel examViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExamListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        FloatingActionButton addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener(v -> {
            NavDirections action = ExamListFragmentDirections.actionNavExamToModifyExam(false, null);
            Navigation.findNavController(v).navigate(action);
        });

        recyclerView = view.findViewById(R.id.examList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        examViewModel = new ViewModelProvider(this).get(ExamViewModel.class);

        examListAdapter = new ExamListAdapter(examViewModel);
        recyclerView.setAdapter(examListAdapter);

        LiveData<List<Exam>> examsLiveData = examViewModel.getAllExams();

        examsLiveData.observe(getViewLifecycleOwner(), new Observer<List<Exam>>() {
            @Override
            public void onChanged(List<Exam> examList) {
                examListAdapter.submitList(examList);
            }

        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
