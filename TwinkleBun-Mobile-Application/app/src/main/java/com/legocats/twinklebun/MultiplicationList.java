package com.legocats.twinklebun;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

public class MultiplicationList extends AppCompatActivity {
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplication_list);

        b1 = findViewById(R.id.game_btn);
        b1.setOnClickListener(view -> {
            Intent intent = new Intent(this, MultiplicationGame.class);
            startActivity(intent);

        });



    }
}