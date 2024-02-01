package com.example.collegescheduler.ui.home;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.Course;

public class HomeListAdapter extends ListAdapter<Course, HomeViewHolder> {

    public HomeListAdapter(@NonNull DiffUtil.ItemCallback<Course> diffCallback) {
        super(diffCallback);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return HomeViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        Course current = getItem(position);
        holder.bind(current.getCid() + "\n" + current.getCourseTitle());
        HomeFragmentDirections.ActionNavHomeToAddCourse action =
                HomeFragmentDirections.actionNavHomeToAddCourse()
                        .setCourseId(current.getCid())
                        .setCourseTitle(current.getCourseTitle());
        holder.itemView.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(action);
        });
    }

    static class CourseDiff extends DiffUtil.ItemCallback<Course> {

        @Override
        public boolean areItemsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.getCid().equals(newItem.getCid());
        }
    }
}
