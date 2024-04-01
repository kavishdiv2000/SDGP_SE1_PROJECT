package com.legocats.twinklebun;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private List<Question> questions;
    private List<String> userAnswers;

    public QuestionAdapter(List<Question> questions) {
        this.questions = questions;
        this.userAnswers = new ArrayList<>(Collections.nCopies(questions.size(), null));
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.bind(question);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public List<String> getUserAnswers() {
        return userAnswers;
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQuestion;
        private RadioGroup rgOptions;
        private RadioButton[] rbOptions;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            rgOptions = itemView.findViewById(R.id.rg_options);
            rbOptions = new RadioButton[4];
            rbOptions[0] = itemView.findViewById(R.id.rb_option1);
            rbOptions[1] = itemView.findViewById(R.id.rb_option2);
            rbOptions[2] = itemView.findViewById(R.id.rb_option3);
            rbOptions[3] = itemView.findViewById(R.id.rb_option4);

            rgOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int position = getAdapterPosition();
                    if (checkedId != -1) {
                        RadioButton checkedRadioButton = itemView.findViewById(checkedId);
                        String userAnswer = checkedRadioButton.getText().toString();
                        int selectedRadioButtonIndex = group.indexOfChild(group.findViewById(checkedId));
                        userAnswers.set(position, String.valueOf((selectedRadioButtonIndex+1)));
                    } else {
                        userAnswers.set(position, null);
                    }
                }
            });
        }

        public void bind(Question question) {
            tvQuestion.setText(question.getQuestion());
            String[] options = question.getOptions();
            for (int i = 0; i < rbOptions.length; i++) {
                rbOptions[i].setText(options[i]);
            }
        }
    }
}