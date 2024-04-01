package com.legocats.twinklebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SelectPaper extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_paper);

    }
    public void onPaperClick(View view) {
        Intent intent = new Intent(this, PastPaperActivity.class);
        startActivity(intent);
    }

}
