package com.legocats.twinklebun;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class PastPaperActivity extends AppCompatActivity {

    private int[] questionImages = {
            R.drawable.question1,
            R.drawable.question2,
    };

    private String[][] answers = {
            {"A. London", "B. Paris", "C. Berlin", "D. Rome"},
            {"A. Pacific Ocean", "B. Atlantic Ocean", "C. Indian Ocean", "D. Arctic Ocean"},
    };

    private int currentQuestionIndex = 0;

    private RadioGroup answersRadioGroup;
    private ImageView questionImageView;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_paper_activity);

        questionImageView = findViewById(R.id.imageQuestion);
        answersRadioGroup = findViewById(R.id.allAnswers);
        nextButton = findViewById(R.id.nextButton);

        nextButton.setEnabled(false); // Disable Next button initially

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnswerSelected()) {
                    if (currentQuestionIndex == questionImages.length - 1) {
                        endPaper();
                    } else {
                        currentQuestionIndex = (currentQuestionIndex + 1) % questionImages.length;
                        displayQuestion();
                        nextButton.setEnabled(false); // Disable Next button again for the next question
                    }
                }
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
            radioButton.setId(i); // Set unique ID for each radio button
            answersRadioGroup.addView(radioButton);
        }

        // Enable Next button only when an answer is selected
        answersRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                nextButton.setEnabled(true);
            }
        });
    }

    private boolean isAnswerSelected() {
        int checkedRadioButtonId = answersRadioGroup.getCheckedRadioButtonId();
        return checkedRadioButtonId != -1;
    }

    private void endPaper() {
        finish();
    }
}
