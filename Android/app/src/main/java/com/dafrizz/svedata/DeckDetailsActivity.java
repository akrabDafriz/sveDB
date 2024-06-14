package com.dafrizz.svedata;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.dafrizz.svedata.model.DeckCards;
import com.dafrizz.svedata.model.BaseResponse;
import com.dafrizz.svedata.model.DeckCards;
import com.dafrizz.svedata.request.BaseAPIService;
import com.dafrizz.svedata.request.UtilsAPI;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeckDetailsActivity extends AppCompatActivity {
    private BaseAPIService mApiService;
    private ListView deckCardsListView;
    private TextView deckNameTextView;
    private DeckCardAdapter deckCardAdapter;
    private List<DeckCards> deckCards;
    private int deckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_details);

        mApiService = UtilsAPI.getApiService();
        deckNameTextView = findViewById(R.id.deck_name);
        deckCardsListView = findViewById(R.id.deck_cards_listview);

        String deckIdString = getIntent().getStringExtra("deck_id");
        if (deckIdString != null) {
            deckId = Integer.parseInt(deckIdString);
        } else {
            deckId = getIntent().getIntExtra("deck_id", -1);
        }

        deckCards = new ArrayList<>();
        deckCardAdapter = new DeckCardAdapter(this, deckCards);
        deckCardsListView.setAdapter(deckCardAdapter);

        fetchDeckDetails(deckId);
    }

    private void fetchDeckDetails(int deckId) {
        mApiService.getDeckById(deckId).enqueue(new Callback<BaseResponse<List<DeckCards>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<DeckCards>>> call, Response<BaseResponse<List<DeckCards>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse<List<DeckCards>> res = response.body();
                    List<DeckCards> cards = res.payload;
                    if (cards != null && !cards.isEmpty()) {
                        deckNameTextView.setText(cards.get(0).deck_name);
                        deckCards.clear();
                        deckCards.addAll(cards);
                        deckCardAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(DeckDetailsActivity.this, "Deck is empty or not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DeckDetailsActivity.this, "Error fetching deck details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<DeckCards>>> call, Throwable t) {
                Toast.makeText(DeckDetailsActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}