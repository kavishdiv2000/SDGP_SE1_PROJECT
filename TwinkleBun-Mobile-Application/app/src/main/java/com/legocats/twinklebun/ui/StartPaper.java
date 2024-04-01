//package com.legocats.twinklebun.ui;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.PersistableBundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.legocats.twinklebun.R;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//
//public class StartPaper extends AppCompatActivity {
//
//    TextView tvTimer;
//    TextView tvResult;
//    ImageView ivShowImage;
//
//    //storage technology names and corresponding image resources ids
//    HashMap<String, Integer> map = new HashMap<>();
//    //storage technology names only
//    ArrayList<String> techList = new ArrayList<>();
//    int index;
//    Button btn1, btn2, btn3, btn4;
//    TextView tvPoints;
//    int points;
//    CountDownTimer countDownTimer;
//    long millisUntilFinished;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.start_paper);
//        tvTimer = findViewById(R.id.tvTimer);
//        tvResult = findViewById(R.id.tvResult);
//        ivShowImage = findViewById(R.id.ivShowImage);
//        btn1 = findViewById(R.id.btn1);
//        btn2 = findViewById(R.id.btn2);
//        btn3 = findViewById(R.id.btn3);
//        btn4 = findViewById(R.id.btn4);
//        tvPoints = findViewById(R.id.tvPoints);
//        index = 0;
//        techList.add("Bootstrap");
//        techList.add("GitHub");
//        techList.add("Java");
//        techList.add("MySQL");
//        techList.add("Python");
//        techList.add("Android");
//        techList.add("Php");
//        techList.add("Nodejs");
//        map.put(techList.get(0), R.drawable.bootstrap);
//        map.put(techList.get(1), R.drawable.github);
//        map.put(techList.get(2), R.drawable.java);
//        map.put(techList.get(3), R.drawable.mysql);
//        map.put(techList.get(4), R.drawable.python);
//        map.put(techList.get(5), R.drawable.android);
//        map.put(techList.get(6), R.drawable.php);
//        map.put(techList.get(7), R.drawable.nodejs);
//        Collections.shuffle(techList);
//        millisUntilFinished = 10000;
//        points = 0;
//        startGame();
//    }
//
//    private void startGame(){
//        millisUntilFinished = 10000;
//        tvTimer.setText("" + (millisUntilFinished / 1000) + "s");
//        tvPoints.setText(points + " / " + techList.size());
//        generateQuestions(index);
//        countDownTimer = new CountDownTimer(millisUntilFinished,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                tvTimer.setText("" + (millisUntilFinished / 1000) + "s");
//            }
//
//            @Override
//            public void onFinish() {
//                index++;
//                if (index > techList.size() - 1){
//                    ivShowImage.setVisibility(View.GONE);
//                    btn1.setVisibility(View.GONE);
//                    btn2.setVisibility(View.GONE);
//                    btn3.setVisibility(View.GONE);
//                    btn4.setVisibility(View.GONE);
//                    Intent intent = new Intent(StartPaper.this, GameOver.class);
//                    intent.putExtra("points", points);
//                    startActivity(intent);
//                    finish();
//                }else {
//                    startGame();
//                }
//
//            }
//        }.start();
//    }
//
//    private void generateQuestions(int index) {
//        ArrayList<String> techListTemp = (ArrayList<String>) techList.clone();
//        String correctAnswer = techList.get(index);
//        techListTemp.remove(correctAnswer);
//        Collections.shuffle(techListTemp);
//        ArrayList<String> newList = new ArrayList<>();
//        newList.add(techListTemp.get(0));
//        newList.add(techListTemp.get(1));
//        newList.add(techListTemp.get(2));
//        newList.add(correctAnswer);
//        Collections.shuffle(newList);
//        btn1.setText(newList.get(0));
//        btn2.setText(newList.get(1));
//        btn3.setText(newList.get(2));
//        btn4.setText(newList.get(3));
//        ivShowImage.setImageResource(map.get(techList.get(index)));
//    }
//
//    public void nextQuestion(View view) {
//        countDownTimer.cancel();
//        index++;
//        if (index > techList.size()-1){
//            ivShowImage.setVisibility(View.GONE);
//            btn1.setVisibility(View.GONE);
//            btn2.setVisibility(View.GONE);
//            btn3.setVisibility(View.GONE);
//            btn4.setVisibility(View.GONE);
//            Intent intent = new Intent(StartPaper.this, GameOver.class);
//            intent.putExtra("points", points);
//            startActivity(intent);
//            finish();
//        }else {
//            startGame();
//        }
//    }
//
//    public void answerSelected(View view) {
//        countDownTimer.cancel();
//        String answer = ((Button) view).getText().toString().trim();
//        String correctAnswer = techList.get(index);
//        if(answer.equals(correctAnswer)){
//            points++;
//            tvPoints.setText(points + " / " + techList.size());
//            tvResult.setText("Correct");
//        }else {
//            tvResult.setText("Wrong");
//        }
//    }
//}
