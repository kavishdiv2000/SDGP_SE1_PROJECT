package com.legocats.twinklebun;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {
    SignInClient oneTapClient;
    BeginSignInRequest signUpRequest;

    Button button,loginButton;

    private static final int REQ_ONE_TAP = 2;
    public boolean showOneTapUI = true;

    private String email,password;
    private TokenManager tokenManager;
    protected EditText emailField,passwordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tokenManager = TokenManager.getInstance(this);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);





        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        button = findViewById(R.id.googleSignInButton);
        loginButton = findViewById(R.id.loginButton);

        oneTapClient = Identity.getSignInClient(this);
        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)

                        .setServerClientId(getString(R.string.Web_id))

                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailField.getText().toString();
                password = passwordField.getText().toString();
                loginButton.setEnabled(false);
                LoginUser();



            }
        });

        ActivityResultLauncher<IntentSenderRequest> activityResultlauncher =
                registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            try {
                                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                                String idToken = credential.getGoogleIdToken();
                                if (idToken !=  null) {
                                    Log.d("TAG", "Got ID token.");
                                }
                            } catch (ApiException e) {
                                Log.d("TAG", Objects.requireNonNull(e.getLocalizedMessage()));
                            }

                        }
                    }
                });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneTapClient.beginSignIn(signUpRequest)
                        .addOnSuccessListener(Login.this, new OnSuccessListener<BeginSignInResult>() {
                            @Override
                            public void onSuccess(BeginSignInResult result) {
                                IntentSenderRequest intentSenderRequest =
                                        new IntentSenderRequest.Builder(result.getPendingIntent().getIntentSender()).build();
                                activityResultlauncher.launch(intentSenderRequest);
                            }
                        })
                        .addOnFailureListener(Login.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", Objects.requireNonNull(e.getLocalizedMessage()));
                            }
                        });

            }
        });


    }

    private void LoginUser(){
        String apiUrl = URIManager.BASE_URI_AUTH+"/login";

        OkHttpClient client = new OkHttpClient();

        // Create the request body
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            Log.e("JSON_ERROR", "Error creating JSON body: " + e.getMessage());
//            return;
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody.toString());

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                Login.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Login.this, "Connection issue, check the internet connection.", Toast.LENGTH_SHORT).show();
                        loginButton.setEnabled(true);
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

                            if(statusCode==401){
                                Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                                loginButton.setEnabled(true);
                            }else if(statusCode==500){
                                Toast.makeText(Login.this, "Server error!", Toast.LENGTH_SHORT).show();
                                loginButton.setEnabled(true);
                            }
                            else{
                                try{
                                    JSONObject jsonResponse = new JSONObject(responseBody);

                                    String accessToken = jsonResponse.getString("accessToken");
                                    String refreshToken = jsonResponse.getString("refreshToken");
                                    tokenManager.storeAccessToken(accessToken);
                                    tokenManager.storeRefreshToken(refreshToken);
                                    final Intent mainIntent =  new Intent(Login.this, Home.class);
                                    startActivity(mainIntent);
                                    finish();


                                }catch (JSONException e){
                                    Log.e("JSON_ERROR", "Error parsing JSON response: " + e.getMessage());

                                }

                            }


                        } catch (Exception e) {
                            Log.e("JSON_ERROR", "Error parsing JSON response: " + e.getMessage());
                            Toast.makeText(Login.this, "Something went wrong, again give a try!", Toast.LENGTH_SHORT).show();
                            loginButton.setEnabled(true);
                        }
                    }
                });
            }
        });
    }


}