package com.legocats.twinklebun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

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
//    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_hub_config);
        textView = findViewById(R.id.testText);
//
        Intent intent = getIntent();
        if (intent != null) {
            textData = intent.getStringExtra("SCANNED_DATA");
//            textView.setText(textData);

        }

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

                 Log.i("MyTag", "button pressed");
                 makeApiRequest();

             }
        });









    }

    private void makeApiRequest(){
        String apiUrl = "https://sdgp-se1-project.onrender.com/api/paper/process-generate-content";
        String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiaGkgaG93IGFyZSB5b3UiLCJpYXQiOjE3MTEyNjA1NjksImV4cCI6MTcxMTQzMzM2OX0.puodplWYkaUIUvg43F0qISfsF5TOa9Pb52HDG0TUcaA";

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
                .addHeader("Authorization", "bearer " + jwtToken)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("API_ERROR", "API request failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            textView.setText(responseBody.toString());
                            Log.d("myTag",responseBody.toString());

                            JsonReader reader = new JsonReader(new StringReader(responseBody));
                            reader.setLenient(true); // Set the JsonReader to be lenient
                            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                            handleApiResponse(jsonObject);


                        } catch (Exception e) {
                            Log.e("JSON_ERROR", "Error parsing JSON response: " + e.getMessage());
                        }
                    }
                });
            }
        });








    }

    private void handleApiResponse(JsonObject jsonObject) {
        // Do something with the JsonObject, e.g., display it in the TextView
        textView.setText(jsonObject.toString());
        Log.i("MyTag",jsonObject.toString());
    }


}