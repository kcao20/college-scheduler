package com.example.collegescheduler.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;

import org.w3c.dom.Text;

class HomeViewHolder extends RecyclerView.ViewHolder {
    private final TextView courseItemView;

    private HomeViewHolder(View itemView) {
        super(itemView);
        courseItemView = itemView.findViewById(R.id.textView);
    }

    static HomeViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new HomeViewHolder(view);
    }

    public TextView getCourseItemView() {
        return courseItemView;
    }

    public void bind(String text) {
        courseItemView.setText(text);
    }

}
