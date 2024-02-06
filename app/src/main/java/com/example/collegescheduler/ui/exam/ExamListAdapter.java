package com.example.collegescheduler.ui.exam;

import android.app.AlertDialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private Context context;

    public ExamListAdapter(ExamViewModel examViewModel, Context context) {
        super(new ExamDiffCallback());
        this.examViewModel = examViewModel;
        this.context = context;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    showDetails(exam);
                }
            }
        });
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


    private void showDetails(Exam exam) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View customView = inflater.inflate(R.layout.cuystom_exam_dialog, null);

        TextView detailsTextView = customView.findViewById(R.id.details);

        detailsTextView.setText(exam.getExamDetails());

        builder.setView(customView);

        builder.setCancelable(true);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
