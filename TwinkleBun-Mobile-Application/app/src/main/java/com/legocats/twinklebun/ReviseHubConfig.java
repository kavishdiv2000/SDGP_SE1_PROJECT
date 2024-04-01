package com.legocats.twinklebun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReviseHubConfig extends AppCompatActivity {

    private TextView textView;
    private NumberPicker numberOfQuiz , timeTakenPerQuiz;
    private Button btnGenerate;
    private String textData;
    private TokenManager tokenManager;
//    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_hub_config);
//        textView = findViewById(R.id.testText);
//
        Intent intent = getIntent();
        if (intent != null) {
            textData = intent.getStringExtra("SCANNED_DATA");
//            textView.setText(textData);

        }
        tokenManager = TokenManager.getInstance(this);

        numberOfQuiz = findViewById(R.id.numberPicker_questions);
        numberOfQuiz.setMaxValue(10);
        numberOfQuiz.setMinValue(1);
        numberOfQuiz.setValue(1);

        timeTakenPerQuiz = findViewById(R.id.numberPicker_timePerQuestion);
        timeTakenPerQuiz.setMaxValue(60);
        timeTakenPerQuiz.setMinValue(1);
        numberOfQuiz.setValue(1);

        btnGenerate = findViewById(R.id.button_generate);




        btnGenerate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 tokenManager.generateAccessToken(new TokenManager.OnAccessTokenGeneratedListener() {
                     @Override
                     public void onSuccess() {
                         ReviseHubConfig.this.runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 Log.i("MyTag", "button pressed");
                                 Log.d("refresh token outside", "onsuccess");
                                 btnGenerate.setEnabled(false);
                                    makeApiRequest();}
                         });
                     }

                     @Override
                     public void onFailure() {
                         ReviseHubConfig.this.runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 Toast.makeText(ReviseHubConfig.this, "Connection issue, check the internet connection.", Toast.LENGTH_SHORT).show();
                                 btnGenerate.setEnabled(true);
                                 Log.d("refresh token outside", "revisehubconfig");
                             }
                         });

                     }

                     @Override
                     public void onForbidden() {//403
//                         Toast.makeText(ReviseHubConfig.this, "Something went wrong, try login again!", Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(ReviseHubConfig.this, SplashScreen.class);
                         startActivity(intent);
                         finish();

                     }

                     @Override
                     public void onUnauthorized() {//401
//                         Toast.makeText(ReviseHubConfig.this, "Something went wrong, try login again!", Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(ReviseHubConfig.this, SplashScreen.class);
                         startActivity(intent);
                         finish();

                     }
                 });



             }
        });


    }

    private void makeApiRequest(){


        String apiUrl = URIManager.BASE_URI_PAPER+"/process-generate-content";
        String jwtToken = tokenManager.getAccessToken();

        OkHttpClient client = new OkHttpClient();

        // Create the request body
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("data", textData);
            jsonBody.put("num", String.valueOf(numberOfQuiz.getValue()));
        } catch (JSONException e) {
            Log.e("JSON_ERROR", "Error creating JSON body: " + e.getMessage());
//            return;
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody.toString());

        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + jwtToken)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ReviseHubConfig.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ReviseHubConfig.this, "Connection issue, check the internet connection.", Toast.LENGTH_SHORT).show();
                        btnGenerate.setEnabled(true);
                        Log.e("API_ERROR", "API request failed: " + e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();
                final int statusCode = response.code();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Log.d("myTag",responseBody);
                            Log.d("myTag","hi response passed");

                            if(statusCode == 500){
                                Toast.makeText(ReviseHubConfig.this, "Server Error, try again!", Toast.LENGTH_SHORT).show();
                                btnGenerate.setEnabled(true);
                            }else if(statusCode==400){
                                try{
                                    JSONObject jsonResponse = new JSONObject(responseBody);

                                    String errorMessage = jsonResponse.getString("message");
                                    Toast.makeText(ReviseHubConfig.this, errorMessage, Toast.LENGTH_SHORT).show();
                                    btnGenerate.setEnabled(true);



                                }catch (JSONException e){
                                    Log.e("JSON_ERROR", "Error parsing JSON response: " + e.getMessage());
                                    Toast.makeText(ReviseHubConfig.this, "Something went wrong, try again!", Toast.LENGTH_SHORT).show();

                                }
                            }else if(statusCode == 401 || statusCode == 403){
                                Toast.makeText(ReviseHubConfig.this, "Something went wrong, try login again!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ReviseHubConfig.this, ErrorActivity.class);
                                startActivity(intent);
                                finish();
                            } else{
                                int time = numberOfQuiz.getValue() * timeTakenPerQuiz.getValue();

                            Intent intent = new Intent(ReviseHubConfig.this, ReviseHubPaper.class);
                            intent.putExtra("PAPER_DATA", responseBody);
                            intent.putExtra("TIME_TAKEN",String.valueOf(time));
                            startActivity(intent);
                            finish();

                            }


                        } catch (Exception e) {
                            Log.e("JSON_ERROR", "Error parsing JSON response: " + e.getMessage());
                            Toast.makeText(ReviseHubConfig.this, "Something went wrong, again give a try!", Toast.LENGTH_SHORT).show();
                            btnGenerate.setEnabled(true);
                        }
                    }
                });
            }
        });









    }

    private void handleApiResponse(JsonObject jsonObject) {
        // Do something with the JsonObject, e.g., display it in the TextView
//        textView.setText(jsonObject.toString());
        Log.i("MyTag",jsonObject.toString());
    }


}