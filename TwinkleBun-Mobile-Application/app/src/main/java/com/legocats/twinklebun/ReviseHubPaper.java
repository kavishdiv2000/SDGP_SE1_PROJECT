package com.legocats.twinklebun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ReviseHubPaper extends AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView rvQuestions;
    private QuestionAdapter adapter;
    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_hub_paper);

        rvQuestions = findViewById(R.id.rv_questions);
        rvQuestions.setLayoutManager(new LinearLayoutManager(this));

        // Assuming you have already parsed the JSON response and have a list of Question objects
//        JsonReader reader = new JsonReader(new StringReader(responseBody));
//        reader.setLenient(true); // Set the JsonReader to be lenient
//        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        Intent intent = getIntent();
        if (intent != null) {
            jsonResponse = intent.getStringExtra("PAPER_DATA");
//            textView.setText(textData);

        }

        List<Question> questions = parseJsonResponse(jsonResponse);


        adapter = new QuestionAdapter(questions);
        rvQuestions.setAdapter(adapter);
    }

    private List<Question> parseJsonResponse(String jsonResponse) {
        List<Question> questions = new ArrayList<>();
        try {
            JSONObject mainObject = new JSONObject(jsonResponse);
            JSONArray responseArray = mainObject.getJSONArray("response");

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

    public void submitAnswers(View view) {
        List<String> userAnswers = adapter.getUserAnswers();
//        int score = calculateScore(userAnswers);
        // Show the score to the user
    }

//    private int calculateScore(List<String> userAnswers) {
//        int score = 0;
//        for (int i = 0; i < userAnswers.size(); i++) {
//            String userAnswer = userAnswers.get(i);
//            String correctAnswer = adapter.getItem(i).getCorrectAnswer();
//            if (userAnswer != null && userAnswer.equals(correctAnswer)) {
//                score++;
//            }
//        }
//        return score;
//    }
}