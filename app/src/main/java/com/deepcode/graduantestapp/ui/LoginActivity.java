package com.deepcode.graduantestapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deepcode.graduantestapp.ApiClient;
import com.deepcode.graduantestapp.R;
import com.deepcode.graduantestapp.interfaces.ApiService;
import com.deepcode.graduantestapp.model.FormData;
import com.deepcode.graduantestapp.util.TokenManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button signIn;
    private EditText etEmail, etPassword;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        apiService = ApiClient.getClient(this).create(ApiService.class);


        signIn.setOnClickListener(v -> validateSignIn());
    }


    private void initViews() {
        signIn = findViewById(R.id.btnSignIn);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
    }

    private void validateSignIn() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (!email.isEmpty() || !password.isEmpty()) {
            login(email, password);
        } else {
            Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show();
        }
    }

    private void login(String email, String password) {
        FormData formData = new FormData(email, password);
        Call<ResponseBody> call = apiService.login(formData);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null) {
                    String responseString = null;
                    try {
                        responseString = response.body().string();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    TokenManager.getInstance(LoginActivity.this).saveAccessToken(responseString);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getStackTrace());
            }
        });
    }
}