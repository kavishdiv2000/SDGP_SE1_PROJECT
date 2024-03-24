package com.legocats.twinklebun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.legocats.twinklebun.ui.StartPaper;

public class ScholarshipPaperList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_paper_list);
    }

    public void startPaper(View view) {
        Intent intent = new Intent(ScholarshipPaperList.this, StartPaper.class);
        startActivity(intent);
        finish();
    }
}