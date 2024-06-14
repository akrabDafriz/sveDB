package com.dafrizz.svedata;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dafrizz.svedata.model.BaseResponse;
import com.dafrizz.svedata.model.Card;
import com.dafrizz.svedata.model.DeckRequest;
import com.dafrizz.svedata.request.BaseAPIService;
import com.dafrizz.svedata.request.UtilsAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateDeckActivity extends AppCompatActivity {
    private BaseAPIService mApiService;
    private Context mContext;
    private CardAdapter availableCardAdapter;
    private SelectedCardAdapter selectedCardAdapter;
    private List<Card> availableCards;
    private List<Card> selectedCards;
    private Map<String, Integer> cardQuantities;

    private static final int FILTER_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deck);

        mContext = this;
        mApiService = UtilsAPI.getApiService();

        EditText deckNameEditText = findViewById(R.id.deckNameEditText);
        Button filterCardsButton = findViewById(R.id.filterCardsButton);
        Button saveDeckButton = findViewById(R.id.saveDeckButton);

        cardQuantities = new HashMap<>();
        availableCards = new ArrayList<>();
        selectedCards = new ArrayList<>();

        ListView availableCardsListView = findViewById(R.id.availableCardsListView);
        ListView selectedCardsListView = findViewById(R.id.selectedCardsListView);

        availableCardAdapter = new CardAdapter(this, availableCards);
        selectedCardAdapter = new SelectedCardAdapter(this, selectedCards, cardQuantities);

        availableCardsListView.setAdapter(availableCardAdapter);
        selectedCardsListView.setAdapter(selectedCardAdapter);

        availableCardsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = availableCards.get(position);
                System.out.println("Onitem dipencet");
                Log.d("CreateDeckActivity", "Available card clicked: " + card.card_name);
                addCardToDeck(card);
            }
        });

        selectedCardsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = selectedCards.get(position);
                Log.d("CreateDeckActivity", "Selected card clicked: " + card.card_name);
                removeCardFromDeck(card);
            }
        });

        filterCardsButton.setOnClickListener(v -> startActivityForResult(new Intent(mContext, FilterCardsActivity.class), FILTER_REQUEST_CODE));

        saveDeckButton.setOnClickListener(v -> {
            String deckName = deckNameEditText.getText().toString();
            String userId = LoginActivity.loggedUser.id; // Replace with actual user ID

            List<DeckRequest> deckRequests = new ArrayList<>();
            for (Card card : selectedCards) {
                String cardName = card.card_name;
                int quantity = cardQuantities.get(cardName);
                deckRequests.add(new DeckRequest(cardName, quantity));
            }

            saveDeck(deckName, userId, deckRequests);
        });

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
                        availableCards.clear();
                        availableCards.addAll(cards);
                        availableCardAdapter.notifyDataSetChanged();
                    }
                } else {
                    viewToast(mContext, "Error fetching data");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Card>>> call, Throwable t) {
                viewToast(mContext, "Network error");
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
                        availableCards.clear();
                        availableCards.addAll(cards);
                        availableCardAdapter.notifyDataSetChanged();
                    }
                } else {
                    viewToast(mContext, "Error fetching data");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Card>>> call, Throwable t) {
                viewToast(mContext, "Network error");
            }
        });
    }

    private void addCardToDeck(Card card) {
        String cardName = card.card_name;
        int quantity = cardQuantities.getOrDefault(cardName, 0);
        cardQuantities.put(cardName, quantity + 1);

        if (!selectedCards.contains(card)) {
            selectedCards.add(card);
        }

        Log.d("CreateDeckActivity", "Card added: " + card.card_name + " - Quantity: " + cardQuantities.get(cardName));
        selectedCardAdapter.notifyDataSetChanged();
    }

    protected void removeCardFromDeck(Card card) {
        String cardName = card.card_name;
        int quantity = cardQuantities.getOrDefault(cardName, 0);

        if (quantity > 1) {
            cardQuantities.put(cardName, quantity - 1);
        } else {
            cardQuantities.remove(cardName);
            selectedCards.remove(card);
        }

        Log.d("CreateDeckActivity", "Card removed: " + card.card_name + " - Remaining Quantity: " + cardQuantities.getOrDefault(cardName, 0));
        selectedCardAdapter.notifyDataSetChanged();
    }

    private void saveDeck(String deckName, String userId, List<DeckRequest> deckRequests) {
        Call<BaseResponse<Integer>> call = mApiService.createDeck(deckName, userId, deckRequests);
        call.enqueue(new Callback<BaseResponse<Integer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse<Integer> res = response.body();
                    viewToast(mContext, "Deck saved with ID: " + res.payload);
                } else {
                    viewToast(mContext, "Error saving deck");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Integer>> call, Throwable t) {
                viewToast(mContext, "Network error");
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

    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}

