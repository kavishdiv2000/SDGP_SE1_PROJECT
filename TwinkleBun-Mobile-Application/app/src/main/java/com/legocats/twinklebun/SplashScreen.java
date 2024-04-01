package com.legocats.twinklebun;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;



public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 2000; // 2 seconds
    private TokenManager tokenManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//        Log.i("MyTag","app started");
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                final Intent mainIntent =  new Intent(SplashScreen.this, Login.class);
//                SplashScreen.this.startActivity(mainIntent);
//                SplashScreen.this.finish();
//
//            }
//        },3000);
        tokenManager = TokenManager.getInstance(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkTokensAndNavigate();
            }
        }, SPLASH_TIMEOUT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    private void checkTokensAndNavigate() {
        String refreshToken = tokenManager.getRefreshToken();

        if (refreshToken != null) {
            // Validate refresh token
            validateRefreshToken(refreshToken);
        } else {
            navigateToLoginScreen();
//            navigateToSignUpScreen();
        }
    }



    private void validateRefreshToken(String refreshToken) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), "{\"refreshToken\":\"" + refreshToken + "\"}");

        Request request = new Request.Builder()
                .url(URIManager.BASE_URI_AUTH+"/refresh")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle network failure
                e.printStackTrace();
                navigateToErrorScreen();
                // You can navigate to an error screen or show an error message
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int statusCode = response.code();

                if(statusCode==200) {
                    navigateToHomeScreen();
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            navigateToSignUpScreen();
                        }
                    });
                }

            }
        });
    }


    private void navigateToHomeScreen() {
        // Start the HomeActivity
        Intent intent = new Intent(SplashScreen.this, Home.class);
        startActivity(intent);
        finish();
    }

    private void navigateToSignUpScreen() {
        // Start the SignUpActivity
        Intent intent = new Intent(SplashScreen.this, signup.class);
        startActivity(intent);
        finish();
    }

    private void navigateToLoginScreen() {
        // Start the SignUpActivity
        Intent intent = new Intent(SplashScreen.this, Login.class);
        startActivity(intent);
        finish();
    }

    private void navigateToErrorScreen() {
        // Start the LoginActivity
        Intent intent = new Intent(SplashScreen.this, ErrorActivity.class);
        startActivity(intent);
        finish();
    }





}