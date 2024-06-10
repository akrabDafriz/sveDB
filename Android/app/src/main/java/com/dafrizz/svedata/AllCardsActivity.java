package com.dafrizz.svedata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.dafrizz.svedata.model.BaseResponse;
import com.dafrizz.svedata.model.Card;
import com.dafrizz.svedata.request.BaseAPIService;
import com.dafrizz.svedata.request.UtilsAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCardsActivity extends AppCompatActivity {
    private BaseAPIService mApiService;
    private Context mContext;
    private ListView listView;
    private CardAdapter adapter;
    private List<Card> cardList;

    private static final int FILTER_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cards);

        mContext = this;
        mApiService = UtilsAPI.getApiService();
        Button filterButton = findViewById(R.id.filterButton);

        handleList();
        filterButton.setOnClickListener(v -> startActivityForResult(new Intent(mContext, FilterCardsActivity.class), FILTER_REQUEST_CODE));
    }

    private void handleList() {
        listView = findViewById(R.id.cards_list_view);
        cardList = new ArrayList<>();
        adapter = new CardAdapter(this, cardList);
        listView.setAdapter(adapter);
        fetchAllCards();
    }

    private void fetchAllCards() {
        mApiService.getAllCards().enqueue(new Callback<BaseResponse<List<Card>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Card>>> call, Response<BaseResponse<List<Card>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse<List<Card>> res = response.body();
                    List<Card> cards = res.payload;
                    if (cards != null) {
                        cardList.clear();
                        cardList.addAll(cards);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(mContext, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Card>>> call, Throwable t) {
                Toast.makeText(mContext, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchFilteredCards(HashMap<String, String> filters) {
        mApiService.getCardsFiltered(filters).enqueue(new Callback<BaseResponse<List<Card>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Card>>> call, Response<BaseResponse<List<Card>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse<List<Card>> res = response.body();
                    List<Card> cards = res.payload;
                    if (cards != null) {
                        cardList.clear();
                        cardList.addAll(cards);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(mContext, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Card>>> call, Throwable t) {
                Toast.makeText(mContext, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILTER_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            HashMap<String, String> filters = (HashMap<String, String>) data.getSerializableExtra("filters");
            if (filters != null) {
                fetchFilteredCards(filters);
            }
        }
    }
}