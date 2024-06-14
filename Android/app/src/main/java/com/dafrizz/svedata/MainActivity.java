package com.dafrizz.svedata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.dafrizz.svedata.model.User;
import com.dafrizz.svedata.request.BaseAPIService;
import com.dafrizz.svedata.request.UtilsAPI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private BaseAPIService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mApiService = UtilsAPI.getApiService();

        setupButtons();

        setupBottomNavigationView();
    }

    private void setupButtons() {
        Button toCards = findViewById(R.id.toCardDatabase);
        Button toDecks = findViewById(R.id.toDecks);
        Button toLists = findViewById(R.id.toLists);

        toCards.setOnClickListener(v -> {
            moveActivity(AllCardsActivity.class);
            viewToast("Opening Cards");
        });

        toDecks.setOnClickListener(v -> {
            moveActivity(AllDeckActivity.class);
            viewToast("Opening Decks");
        });

        toLists.setOnClickListener(v -> {
            moveActivity(AllListActivity.class);
            viewToast("Opening Lists");
        });
    }

    private void setupBottomNavigationView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.navigation_main) {
                    moveActivity(MainActivity.class);
                    return true;
                } else if (id == R.id.navigation_all_cards) {
                    moveActivity(AllCardsActivity.class);
                    return true;
                } else if (id == R.id.navigation_all_decks) {
                    moveActivity(AllDeckActivity.class);
                    return true;
                } else if (id == R.id.navigation_all_lists) {
                    moveActivity(AllListActivity.class);
                    return true;
                } else if (id == R.id.navigation_profile) {
                    moveActivity(ProfileActivity.class);
                    return true;
                }
                return false;
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }

    private void moveActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    private void viewToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
