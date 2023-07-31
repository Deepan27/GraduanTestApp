package com.deepcode.graduantestapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.deepcode.graduantestapp.R;
import com.deepcode.graduantestapp.fragment.PostCreateFragment;
import com.deepcode.graduantestapp.fragment.PostListFragment;
import com.deepcode.graduantestapp.fragment.UserProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        // Set the initial fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new PostListFragment())
                .commit();
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment selectedFragment = null;

        if (item.getItemId() == R.id.menu_post_list) {
            selectedFragment = new PostListFragment();
        } else if (item.getItemId() == R.id.menu_post_create) {
            selectedFragment = new PostCreateFragment();
        } else if (item.getItemId() == R.id.menu_user_profile) {
            selectedFragment = new UserProfileFragment();
        }

        if (selectedFragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true;
        }

        return false;
    }
}