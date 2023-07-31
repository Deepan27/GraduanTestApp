package com.deepcode.graduantestapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deepcode.graduantestapp.ApiClient;
import com.deepcode.graduantestapp.R;
import com.deepcode.graduantestapp.interfaces.ApiService;
import com.deepcode.graduantestapp.model.Bearer;
import com.deepcode.graduantestapp.model.FormData;
import com.deepcode.graduantestapp.util.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        Call<Bearer> call = apiService.login(formData);
        call.enqueue(new Callback<Bearer>() {
            @Override
            public void onResponse(Call<Bearer> call, Response<Bearer> response) {
                if (response.body()!=null) {
                    Bearer bearer = response.body();
                    TokenManager.getInstance(LoginActivity.this).saveAccessToken(bearer.getToken());
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Bearer> call, Throwable t) {

            }
        });
    }
}