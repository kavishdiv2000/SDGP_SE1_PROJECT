package com.legocats.twinklebun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviseHubPaper extends AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView rvQuestions;
    private QuestionAdapter adapter;
    private String jsonResponse;
    private List<Question> questions;
    private Button submitBtn;
    private TextView countdownTextView;
    private CountDownTimer countDownTimer;
    private long timeTake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_hub_paper);
        countdownTextView = findViewById(R.id.countdownTextView);

        rvQuestions = findViewById(R.id.rv_questions);
        submitBtn = findViewById(R.id.btn_submit);
        rvQuestions.setLayoutManager(new LinearLayoutManager(this));


        Intent intent = getIntent();
        if (intent != null) {
            jsonResponse = intent.getStringExtra("PAPER_DATA");
            timeTake = Long.parseLong(Objects.requireNonNull(intent.getStringExtra("TIME_TAKEN")));
//            textView.setText(textData);

        }

        startCountdownTimer(timeTake);

        questions = parseJsonResponse(jsonResponse);


        adapter = new QuestionAdapter(questions);
        rvQuestions.setAdapter(adapter);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitAnswers();
            }
        });



    }

    private List<Question> parseJsonResponse(String jsonResponse) {
        List<Question> questions = new ArrayList<>();
        try {
            JSONObject mainObject = new JSONObject(jsonResponse);
            JSONArray responseArray = mainObject.getJSONArray("questions");

            for (int i = 0; i < responseArray.length(); i++) {
                JSONObject questionObject = responseArray.getJSONObject(i);
                String question = ((i+1)+")"+questionObject.getString("question"));
                JSONObject optionsObject = questionObject.getJSONObject("options");
                String correctAnswer = questionObject.getString("correct_answer");

                String[] options = new String[4];
                options[0] = optionsObject.getString("1");
                options[1] = optionsObject.getString("2");
                options[2] = optionsObject.getString("3");
                options[3] = optionsObject.getString("4");

                Question q = new Question(question,options,correctAnswer);


                questions.add(q);
            }
        } catch (JSONException e) {
            Log.e("JSON_ERROR", "Error parsing JSON response: " + e.getMessage());
//            throw new RuntimeException(e);
        }
        return questions;
    }

    public void submitAnswers() {
        List<String> userAnswers = adapter.getUserAnswers();


        int score = calculateScore(userAnswers);

        double ratio = Math.round(((score*1.0)/(userAnswers.size()*1.0))*100);
        Log.d("Score", ratio+" "+score);

        Intent intent = new Intent(ReviseHubPaper.this, ScoreActivity.class);
        intent.putExtra("SCORE", String.valueOf(ratio));
        startActivity(intent);
        finish();

    }

   private int calculateScore(List<String> userAnswers) {
      int score = 0;
        for (int i = 0; i < userAnswers.size(); i++) {
            String userAnswer = userAnswers.get(i);
//            String correctAnswer = adapter.getItem(i).getCorrectAnswer();
            String correctAnswer = questions.get(i).getCorrectAnswer();

            Log.d("Items", userAnswer+" "+correctAnswer);

            if (userAnswer != null && userAnswer.equals(correctAnswer)) {
                score++;
            }
        }
        return score;
    }



    private void startCountdownTimer(long durationInMinutes) {
        long durationInMillis = durationInMinutes * 60 * 1000;
        countDownTimer = new CountDownTimer(durationInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountdownText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                countdownTextView.setText("00:00:00");
                submitAnswers();
            }
        }.start();
    }

    private void updateCountdownText(long millisUntilFinished) {
        int hours = (int) (millisUntilFinished / (1000 * 60 * 60));
        int minutes = (int) (millisUntilFinished / (1000 * 60)) % 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        countdownTextView.setText(timeString);
    }

//    private void navigateToScoreScreen() {
//        Intent intent = new Intent(this, ScoreActivity.class);
//        startActivity(intent);
//    }




}