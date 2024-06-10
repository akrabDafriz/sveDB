package com.dafrizz.svedata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.dafrizz.svedata.model.User;
import com.dafrizz.svedata.request.BaseAPIService;
import com.dafrizz.svedata.request.UtilsAPI;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private BaseAPIService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mApiService = UtilsAPI.getApiService();
        Button toCards = findViewById(R.id.toCardDatabase);
        Button toDecks = findViewById(R.id.toDecks);
        Button toLists = findViewById(R.id.toLists);

        toCards.setOnClickListener(v->{
            moveActivity(mContext, AllCardsActivity.class);
            viewToast(mContext, "Opening Cards");
        });

        toDecks.setOnClickListener(v->{
            moveActivity(mContext, AllDeckActivity.class);
            viewToast(mContext, "Opening Decks");
        });

        toLists.setOnClickListener(v->{
            moveActivity(mContext, AllListActivity.class);
            viewToast(mContext, "Opening Lists");
        });
    }
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}