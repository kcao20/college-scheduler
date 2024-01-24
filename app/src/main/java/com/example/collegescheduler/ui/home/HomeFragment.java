package com.example.collegescheduler.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.collegescheduler.databinding.FragmentHomeBinding;
import com.google.android.material.textfield.TextInputEditText;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;

        final TextInputEditText courseID = binding.courseID;
        final TextInputEditText courseTitle = binding.courseTitle;
        final Button saveButton = binding.saveButton;
        final Button queryButton = binding.queryButton;

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = courseID.getText().toString();
                String title = courseTitle.getText().toString();
                homeViewModel.onSaveButtonClick(cid, title);
            }
        });

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = courseID.getText().toString();
                homeViewModel.onQueryButtonClick(textView, cid);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}