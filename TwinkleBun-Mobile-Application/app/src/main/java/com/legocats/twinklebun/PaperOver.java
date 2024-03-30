package com.legocats.twinklebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PaperOver extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.paper_over);
//
//        ImageButton closeButton = findViewById(R.id.closeButton);
//        closeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Navigate to the SelectPaper activity
//                Intent intent = new Intent(PaperOver.this, SelectPaper.class);
//                startActivity(intent);
//            }
//        });
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paper_over);

    }
    public void onExit(View view) {
        Intent intent = new Intent(PaperOver.this, SelectPaper.class);
        startActivity(intent);
    }
}
