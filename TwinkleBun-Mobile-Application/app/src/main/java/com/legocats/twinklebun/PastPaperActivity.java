package com.legocats.twinklebun;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class PastPaperActivity extends AppCompatActivity {

    private int[] questionImages = {
            R.drawable.question1, // Replace with actual image resources
            R.drawable.question2,
            // Add corresponding image resources for each question
    };

    private String[][] answers = {
            {"A. London", "B. Paris", "C. Berlin", "D. Rome"},
            {"A. Pacific Ocean", "B. Atlantic Ocean", "C. Indian Ocean", "D. Arctic Ocean"},
            // Add corresponding answers for each question
    };

    private int currentQuestionIndex = 0;

    private RadioGroup answersRadioGroup;
    private ImageView questionImageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_paper_activity);

        questionImageView = findViewById(R.id.imageQuestion);
        answersRadioGroup = findViewById(R.id.allAnswers);


        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex = (currentQuestionIndex + 1) % questionImages.length;
                displayQuestion();
            }
        });

        displayQuestion();
    }

    private void displayQuestion() {
        // Set the image for the question
        questionImageView.setImageResource(questionImages[currentQuestionIndex]);

        // Clear previously added radio buttons
        answersRadioGroup.removeAllViews();

        // Add new radio buttons for the current question's answers
        for (int i = 0; i < answers[currentQuestionIndex].length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(answers[currentQuestionIndex][i]);
            answersRadioGroup.addView(radioButton);
        }
    }
}
