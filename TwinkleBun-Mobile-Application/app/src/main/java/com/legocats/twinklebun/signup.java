package com.legocats.twinklebun;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class signup extends AppCompatActivity {
    private SignInClient oneTapClient;
    private BeginSignInRequest signUpRequest;

    Button button,signupBtn;
    private static final int REQ_ONE_TAP = 2;
    private boolean showOneTapUI = true;
    private TokenManager tokenManager;
    private EditText emailField,passwordField, nameField, comPasswordField;
    private String email,password,username, rePassword;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        tokenManager = TokenManager.getInstance(this);

        button = findViewById(R.id.googleButton);
        emailField = findViewById(R.id.signup_email);
        passwordField = findViewById(R.id.signup_password);
        nameField = findViewById(R.id.signup_name);
        comPasswordField = findViewById(R.id.signup_repeatPassword);
        signupBtn= findViewById(R.id.signUpButton);

//
//        oneTapClient = Identity.getSignInClient(this);
//        signUpRequest = BeginSignInRequest.builder()
//                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        .setServerClientId(getString(R.string.web_client_id))
//                        .setFilterByAuthorizedAccounts(false)
//                        .build())
//                .build();
//
//        ActivityResultLauncher<IntentSenderRequest> activityResultLauncher =
//                registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == RESULT_OK) {
//                            try {
//                                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
//                                String idToken = credential.getGoogleIdToken();
//                                if (idToken !=  null) {
//                                    String email = credential.getId();
//                                    Toast.makeText(getApplicationContext(), "Email: "+email, Toast.LENGTH_SHORT).show();
////                                    Log.d("TAG", "Got ID token.");
//                                }
//                            } catch (ApiException e) {
//                                e.printStackTrace();
////                                Log.d("TAG", Objects.requireNonNull(e.getLocalizedMessage()));
//                            }
//
//                        }
//                    }
//                });
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                oneTapClient.beginSignIn(signUpRequest)
//                        .addOnSuccessListener(signup.this, new OnSuccessListener<BeginSignInResult>() {
//                            @Override
//                            public void onSuccess(BeginSignInResult result) {
//                                IntentSenderRequest intentSenderRequest =
//                                        new IntentSenderRequest.Builder(result.getPendingIntent().getIntentSender()).build();
//                                activityResultLauncher.launch(intentSenderRequest);
//                            }
//                        })
//                        .addOnFailureListener(signup.this, new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // No Google Accounts found. Just continue presenting the signed-out UI.
//                                Log.d("TAG", e.getLocalizedMessage());
//                            }
//                        });
//
//            }
//        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailField.getText().toString();
                password = passwordField.getText().toString();
                rePassword = comPasswordField.getText().toString();
                username = nameField.getText().toString();

                if(password.equals(rePassword)){
                    signupBtn.setEnabled(false);
                    SignUpUser();
                }else{
                    Toast.makeText(signup.this, "Password and repeat password does not match!", Toast.LENGTH_SHORT).show();
                }



            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, Login.class);
                startActivity(intent);

            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


    }

    private void SignUpUser(){
        String apiUrl = URIManager.BASE_URI_AUTH+"/signup";

        OkHttpClient client = new OkHttpClient();

        // Create the request body
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", username);
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


                signup.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(signup.this, "Connection issue, check the internet connection.", Toast.LENGTH_SHORT).show();
                        signupBtn.setEnabled(true);
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

                            if(statusCode==400){
                                Toast.makeText(signup.this, "User already exist!", Toast.LENGTH_SHORT).show();
                                signupBtn.setEnabled(true);
                            }else if(statusCode==500){
                                Toast.makeText(signup.this, "Server error!", Toast.LENGTH_SHORT).show();
                                signupBtn.setEnabled(true);
                            }
                            else if(statusCode==201){

//                                    JSONObject jsonResponse = new JSONObject(responseBody);

//                                    String accessToken = jsonResponse.getString("accessToken");
//                                    String refreshToken = jsonResponse.getString("refreshToken");
//                                    tokenManager.storeAccessToken(accessToken);
//                                    tokenManager.storeRefreshToken(refreshToken);
                                    final Intent mainIntent =  new Intent(signup.this, Login.class);
                                    startActivity(mainIntent);
                                    finish();


                            }


                        } catch (Exception e) {
                            Log.e("JSON_ERROR", "Error parsing JSON response: " + e.getMessage());
                            Toast.makeText(signup.this, "Something went wrong, again give a try!", Toast.LENGTH_SHORT).show();
                            signupBtn.setEnabled(true);
                        }
                    }
                });
            }
        });
    }




}