package com.deepcode.graduantestapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.deepcode.graduantestapp.ApiClient;
import com.deepcode.graduantestapp.R;
import com.deepcode.graduantestapp.interfaces.ApiService;
import com.deepcode.graduantestapp.model.FormData;
import com.deepcode.graduantestapp.model.User;
import com.deepcode.graduantestapp.ui.LoginActivity;
import com.deepcode.graduantestapp.util.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserProfileFragment extends Fragment {

    private TextView tvName;
    private EditText etUpdateName;
    private Button btnUpdateName, btnLogout;
    private ApiService apiService;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        tvName = view.findViewById(R.id.tvName);
        etUpdateName = view.findViewById(R.id.etUpdateName);
        btnUpdateName = view.findViewById(R.id.btnUpdateName);
        btnLogout = view.findViewById(R.id.btnLogout);

        apiService = ApiClient.getClient(getContext()).create(ApiService.class);
        getUsers();

        btnUpdateName.setOnClickListener(v -> {
            if (etUpdateName.getText()!=null){
            updateUser(etUpdateName.getText().toString());
            }
        });

        btnLogout.setOnClickListener(v -> logout());

        return view;
    }

    private void getUsers() {
        Call<User> call = apiService.getUser("Bearer " + TokenManager.getInstance(getContext()));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    user = response.body();
                    tvName.setText(user.getName());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void updateUser(String updatedName){

        FormData data = new FormData(updatedName);

        Call<String> call = apiService.updateProfile("Bearer " + TokenManager.getInstance(getContext()),data);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body()!=null){
                    getUsers();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "Update Users Failed", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onFailure: " + t.getStackTrace());
            }
        });
    }

    private void logout(){
        Call<String> call = apiService.logout("Bearer " + TokenManager.getInstance(getContext()));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body()!=null){
                    String strLogout = response.body();
                    if (strLogout.equals("Logout")){
                        TokenManager.getInstance(getContext()).clearAccessToken();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getStackTrace());
            }
        });
    }

}