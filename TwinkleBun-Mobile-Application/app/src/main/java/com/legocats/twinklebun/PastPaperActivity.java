package com.legocats.twinklebun;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PastPaperActivity extends AppCompatActivity {

    private final int[] questionImages = {
            R.drawable.q1, R.drawable.q2, R.drawable.q3, R.drawable.q4, R.drawable.q5, R.drawable.q6, R.drawable.q7, R.drawable.q8, R.drawable.q9, R.drawable.q10,
            R.drawable.q11, R.drawable.q12, R.drawable.q13, R.drawable.q14, R.drawable.q15, R.drawable.q16, R.drawable.q17, R.drawable.q18, R.drawable.q19, R.drawable.q20,
            R.drawable.q21, R.drawable.q22, R.drawable.q23, R.drawable.q24, R.drawable.q25, R.drawable.q26, R.drawable.q27, R.drawable.q28, R.drawable.q29, R.drawable.q30,
            R.drawable.q31, R.drawable.q32, R.drawable.q33, R.drawable.q34, R.drawable.q35, R.drawable.q36, R.drawable.q37, R.drawable.q38, R.drawable.q39, R.drawable.q40
    };

    private final int[][] answers = {
            {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3},
            {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3},
            {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3},
            {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}
    };

    private final int[] correctAnswers = {
            1, 2, 3, 1, 3, 3, 2, 1, 3, 3,
            2, 3, 3, 2, 3, 3, 2, 2, 1, 3,
            2, 1, 2, 2, 3, 2, 3, 2, 1, 2,
            3, 1, 2, 3, 3, 3, 1, 2, 1, 1

    };

    private int currentQuestionIndex = 0;
    private int totalQuestions;

    private RadioGroup answersRadioGroup;
    private ImageView questionImageView;
    private Button nextButton;
    private TextView textViewScore;

    private int correctAnswersCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_paper_activity);

        questionImageView = findViewById(R.id.imageQuestion);
        answersRadioGroup = findViewById(R.id.allAnswers);
        nextButton = findViewById(R.id.nextButton);
        textViewScore = findViewById(R.id.textViewScore);

        totalQuestions = questionImages.length;

        nextButton.setEnabled(false);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnswerSelected()) {
                    calculateMarks();
                    currentQuestionIndex++;
                    if (currentQuestionIndex < totalQuestions) {
                        displayQuestion();
                        nextButton.setEnabled(false);
                    } else {
                        displayScore(); // Show score when all questions are answered
                        onFinishPaper();
                    }
                    updateScore(); // Update score after each question is answered
                }
            }
        });


        answersRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                nextButton.setEnabled(true); // Enable Next button when an answer is selected
            }
        });

        displayQuestion();
    }

    private void displayQuestion() {
        // Set the image for the question
        questionImageView.setImageResource(questionImages[currentQuestionIndex]);

        answersRadioGroup.removeAllViews();

        // Add new radio buttons for the current question's answers
        for (int i = 0; i < answers[currentQuestionIndex].length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(String.valueOf(answers[currentQuestionIndex][i]));
            radioButton.setId(i); // Set unique ID for each radio button
            answersRadioGroup.addView(radioButton);
        }
    }

    private boolean isAnswerSelected() {
        int checkedRadioButtonId = answersRadioGroup.getCheckedRadioButtonId();
        return checkedRadioButtonId != -1;
    }

    private void calculateMarks() {
        int selectedRadioButtonId = answersRadioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            if (Integer.parseInt(selectedRadioButton.getText().toString()) == correctAnswers[currentQuestionIndex]) {
                correctAnswersCount++;
            }
        }
    }

    private void updateScore() {
        textViewScore.setText("Score: " + correctAnswersCount + "/" + totalQuestions);
    }

    private void displayScore() {
        String scoreMessage = "You scored " + correctAnswersCount + " out of " + totalQuestions;
        Toast.makeText(this, scoreMessage, Toast.LENGTH_LONG).show();
    }

    public void onFinishPaper() {
        setContentView(R.layout.paper_over);
        TextView textViewFinalScore = findViewById(R.id.textScore);
        textViewFinalScore.setText("Your Test Score: " + correctAnswersCount + "/" + totalQuestions);
    }

}
