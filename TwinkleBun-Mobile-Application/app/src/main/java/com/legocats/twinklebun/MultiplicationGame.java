package com.legocats.twinklebun;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class MultiplicationGame extends AppCompatActivity {

    private int noHearts, score, timesTable, attempt;

    private Button bt1,bt2,bt3,bt4;
    private TextView displayQuiz,displayAns, displayScore,displayAttempt;
    private final OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();

    private TextView text;
    private ImageView close;
    private int times;
    //    String button_num;
    private String[] btnStore = new String[4];
    private String[] value = new String[4]; //value[0] saves the true occurance.
    final private HashMap<String,Button>btnMapDic = new HashMap<String,Button>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplication_game);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        score = 0;
        attempt = 0;







        bt1  = findViewById(R.id.button);
        bt2  = findViewById(R.id.button2);
        bt3  = findViewById(R.id.button3);
        bt4  = findViewById(R.id.button4);

//        displayAns = findViewById(R.correct);
        displayQuiz = findViewById(R.id.cal);
        displayScore = findViewById(R.id.score);
        displayAttempt = findViewById(R.id.attempts);

        timesTable = 2;


        text =  displayQuiz;
//        text.setText(String.valueOf((int)(Math.random()*13)));

        bt1 = findViewById(R.id.button);
        bt2 = findViewById(R.id.button2);
        bt3 = findViewById(R.id.button3);
        bt4 = findViewById(R.id.button4);
        close = findViewById(R.id.close);

        displayScore.setText("Score: "+score);
        displayAttempt.setText("Attempt: "+attempt);


        btnMapDic.put("1",bt1);
        btnMapDic.put("2",bt2);
        btnMapDic.put("3",bt3);
        btnMapDic.put("4",bt4);

        times = 2;
        colorBack();



        bt1.setOnClickListener(view -> {

            buttonTextResponding("1");

            System.out.println(btnMapDic);

            view.postDelayed(new Runnable() {
                @Override
                public void run() {

                    colorBack();
//                    text.setText(Arrays.toString(btnStore)+Arrays.toString(value));
                }
            }, 3000);

        });


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonTextResponding("2");

                System.out.println(btnMapDic);

                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        colorBack();
//                        text.setText(Arrays.toString(btnStore)+Arrays.toString(value));
                    }
                }, 3000);

            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonTextResponding("3");

                System.out.println(btnMapDic);

                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        colorBack();
//                        text.setText(Arrays.toString(btnStore)+Arrays.toString(value));
                    }
                }, 3000);

            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonTextResponding("4");

                System.out.println(btnMapDic);

                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        colorBack();
//                        text.setText(Arrays.toString(btnStore)+Arrays.toString(value));
                    }
                }, 3000);

            }
        });




    }

    protected void generate(){
        value = new String[]{null, null, null, null};
        for (int i = 0; i < 4; i++) {
            while(true){
                String temp = String.valueOf((int)(Math.random()*13));
                if(!Arrays.toString(value).contains(temp)){
                    value[i]=temp;
                    break;
                }
            }
        }

        btnStore = new String[]{null, null, null, null};
        for (int i = 0; i < 4; i++) {
            while(true){
                String temp = String.valueOf((int)((Math.random()*4)+1));
                if(!Arrays.toString(btnStore).contains(temp)){
                    btnStore[i]=temp;
                    break;
                }
            }
        }

    }

    protected void buttonMapValue(){

        for (int i =0; i<4; i++) {
            Objects.requireNonNull(btnMapDic.get(btnStore[i])).setText(String.valueOf(Integer.parseInt(value[i])*times));

        }

    }
    protected void buttonTextResponding(String button_num){
        Button btn = btnMapDic.get(button_num);
        Button correct_btn = btnMapDic.get(btnStore[0]);
        assert btn != null;
        assert correct_btn != null;
        attempt++;
        displayAttempt.setText("Attempt: "+attempt);

        if(btnStore[0].equals(button_num)){
            score += 10;
            displayScore.setText("Score: "+score);
            btn.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            btn.setTextColor(ContextCompat.getColor(this, R.color.black));

        }else{

            btn.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
            correct_btn.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            correct_btn.setTextColor(ContextCompat.getColor(this, R.color.black));

        }
        bt1.setEnabled(false);
        bt2.setEnabled(false);
        bt3.setEnabled(false);
        bt4.setEnabled(false);

    }


    protected void colorBack(){

        bt1.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500));
        bt2.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500));
        bt3.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500));
        bt4.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500));

        bt1.setTextColor(ContextCompat.getColor(this, R.color.white));
        bt2.setTextColor(ContextCompat.getColor(this, R.color.white));
        bt3.setTextColor(ContextCompat.getColor(this, R.color.white));
        bt4.setTextColor(ContextCompat.getColor(this, R.color.white));
        generate();
        buttonMapValue();
        String textToBeDisplayed = "2 X "+value[0]+" = ?";
        text.setText(textToBeDisplayed);
        bt1.setEnabled(true);
        bt2.setEnabled(true);
        bt3.setEnabled(true);
        bt4.setEnabled(true);



    }



}



