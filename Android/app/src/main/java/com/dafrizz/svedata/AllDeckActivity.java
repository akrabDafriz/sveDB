package com.dafrizz.svedata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dafrizz.svedata.model.BaseResponse;
import com.dafrizz.svedata.model.DeckList;
import com.dafrizz.svedata.request.BaseAPIService;
import com.dafrizz.svedata.request.UtilsAPI;
import com.dafrizz.svedata.model.DeckCards;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllDeckActivity extends AppCompatActivity {
    private BaseAPIService mApiService;
    private Context mContext;
    private Button createDeckButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> deckNames;
    public static List<DeckList> decks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_deck);

        mContext = this;
        mApiService = UtilsAPI.getApiService();
        createDeckButton = findViewById(R.id.create_deck_button);

        createDeckButton.setOnClickListener(v->moveActivity(mContext, CreateDeckActivity.class));

        handleList();
    }

    protected void handleList() {
        listView = findViewById(R.id.deckListView);
        deckNames = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deckNames);
        listView.setAdapter(adapter);

        mApiService.getAllDecks().enqueue(new Callback<BaseResponse<List<DeckList>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<DeckList>>> call, Response<BaseResponse<List<DeckList>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse<List<DeckList>> res = response.body();
                    decks = res.payload;
                    if (decks != null) {
                        for (DeckList deck : decks) {
                            deckNames.add(deck.deck_name);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(mContext, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<DeckList>>> call, Throwable t) {
                Toast.makeText(mContext, "Network error", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (decks != null && position < decks.size()) {
                DeckList selectedDeck = decks.get(position);
                Intent intent = new Intent(mContext, DeckDetailsActivity.class);
                intent.putExtra("deck_id", selectedDeck.id); // Ensure this is an integer
                startActivity(intent);
            }
        });
    }

    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
}

