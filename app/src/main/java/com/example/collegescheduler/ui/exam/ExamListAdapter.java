package com.example.collegescheduler.ui.exam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.example.collegescheduler.R;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.db.Exam;

public class ExamListAdapter extends ListAdapter<Exam, ExamViewHolder> {

    private View.OnClickListener itemClickListener;

    private ExamViewModel examViewModel;

    public ExamListAdapter(ExamViewModel examViewModel) {
        super(new ExamDiffCallback());
        this.examViewModel = examViewModel;
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exams, parent, false);
        return new ExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
        Exam exam = getItem(position);
        holder.bind(exam);
        holder.setDeleteButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Exam deletedExam = getItem(position);
                    examViewModel.deleteExam(deletedExam);
                }
            }
        });

        holder.setEditButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Exam editedExam = getItem(position);

                    NavDirections action = ExamListFragmentDirections.actionNavExamToModifyExam(true, String.valueOf(editedExam.getExamId()));
                    Navigation.findNavController(v).navigate(action);

                }
            }
        });
    }



    static class ExamDiffCallback extends DiffUtil.ItemCallback<Exam> {

        @Override
        public boolean areItemsTheSame(Exam oldItem, Exam newItem) {
            // Check if the items have the same ID (examId)
            return oldItem.getExamId() == newItem.getExamId();
        }

        @Override
        public boolean areContentsTheSame(Exam oldItem, Exam newItem) {
            // Check if the contents of the items are the same
            return oldItem.equals(newItem);
        }
    }
}
