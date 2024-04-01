package com.legocats.twinklebun;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ErrorActivity extends AppCompatActivity {
    Button errorBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);


        errorBtn  = findViewById(R.id.try_again_btn);

        errorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent mainIntent =  new Intent(ErrorActivity.this, SplashScreen.class);
                ErrorActivity.this.startActivity(mainIntent);
                ErrorActivity.this.finish();
            }
        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

}