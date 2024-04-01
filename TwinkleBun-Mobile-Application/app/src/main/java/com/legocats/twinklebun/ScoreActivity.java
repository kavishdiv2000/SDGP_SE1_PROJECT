package com.legocats.twinklebun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    private String score;
    private TextView scoreTxtV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoreTxtV = findViewById(R.id.scoreValue);


        Intent intent = getIntent();
        if (intent != null) {
            score = intent.getStringExtra("SCORE");
        }
        scoreTxtV.setText(score);
    }
}