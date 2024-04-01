package com.legocats.twinklebun;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

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

public class TokenManager {

    private static final String PREF_NAME = "TokenPrefs";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_ACCESS_TOKEN = "access_token";

    private static TokenManager instance;
    private SharedPreferences sharedPreferences;
    private OkHttpClient client;

    private TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        client = new OkHttpClient();
    }

    public static synchronized TokenManager getInstance(Context context) {
        if (instance == null) {
            instance = new TokenManager(context);
        }
        return instance;
    }

    public void storeRefreshToken(String refreshToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_REFRESH_TOKEN, refreshToken);
        editor.apply();
    }

    public String getRefreshToken() {
        return sharedPreferences.getString(KEY_REFRESH_TOKEN, null);
    }

    public void storeAccessToken(String accessToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }

    public void clearTokens() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_REFRESH_TOKEN);
        editor.remove(KEY_ACCESS_TOKEN);
        editor.apply();
    }

    public void generateAccessToken(final OnAccessTokenGeneratedListener listener){

        String refreshToken = getRefreshToken();
        if (refreshToken != null) {
            RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), "{\"refreshToken\":\"" + refreshToken + "\"}");

            Request request = new Request.Builder()
                    .url(URIManager.BASE_URI_AUTH+"/refresh")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    Log.e("API_ERROR", "API request failed: " + e.getMessage());

                    listener.onFailure();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int statusCode = response.code();
                    if (statusCode == 500) { // OK
                        listener.onFailure();
                    } else if(statusCode==401){
                        listener.onUnauthorized();
                    } else if (statusCode==403) {
                        listener.onForbidden();

                    }else
                    {
                        String responseBody = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody);
                            String newAccessToken = jsonObject.getString("accessToken");

                            storeAccessToken(newAccessToken);

                            listener.onSuccess();
                        } catch (Exception e) {
                            e.printStackTrace();
//                            listener.onFailure();
                        }


                    }
                }
            });
        } else {
            // Refresh token is not available
            listener.onForbidden();
        }
    }

    public interface OnAccessTokenGeneratedListener {
        void onSuccess();
        void onFailure();
        void onUnauthorized(); //401
        void onForbidden(); //403
    }

}