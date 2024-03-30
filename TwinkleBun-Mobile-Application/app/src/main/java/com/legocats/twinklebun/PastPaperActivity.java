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
            R.drawable.question1,
            R.drawable.question2,
            R.drawable.q5,
    };

    private final int[][] answers = {
            {1, 2, 3},
            {2, 3, 5},
            {2, 3, 5}
    };

    private final int[] correctAnswers = {
            1, // Correct answer for question 1
            3,  // Correct answer for question 2
            5   // Add correct answers for each question
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
}
