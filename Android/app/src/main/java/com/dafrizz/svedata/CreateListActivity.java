package com.dafrizz.svedata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dafrizz.svedata.model.BaseResponse;
import com.dafrizz.svedata.model.Card;
import com.dafrizz.svedata.request.BaseAPIService;
import com.dafrizz.svedata.request.UtilsAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateListActivity extends AppCompatActivity {
    private BaseAPIService mApiService;
    private Context mContext;
    private ListView availableCardsListView;
    private ListView selectedCardsListView;
    private CreateListActivity.CardAdapter availableCardAdapter;
    private CreateListActivity.SelectedCardAdapter selectedCardAdapter;
    private List<Card> availableCards;
    private List<Card> selectedCards;
    private Map<String, Integer> cardQuantities;
    private List<String> cardNames;
    private List<Integer> quantities;
    private static final int FILTER_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        mContext = this;
        mApiService = UtilsAPI.getApiService();

        EditText listNameEditText = findViewById(R.id.listNameEditText);
        Button filterCardsButton = findViewById(R.id.filterCardsButton);
        Button saveListButton = findViewById(R.id.saveListButton);

        cardQuantities = new HashMap<>();
        availableCards = new ArrayList<>();
        selectedCards = new ArrayList<>();
        cardNames = new ArrayList<>();
        quantities = new ArrayList<>();

        availableCardsListView = findViewById(R.id.availableCardsListView);
        selectedCardsListView = findViewById(R.id.selectedCardsListView);

        availableCardAdapter = new CreateListActivity.CardAdapter(this, availableCards);
        selectedCardAdapter = new CreateListActivity.SelectedCardAdapter(this, selectedCards, cardQuantities);

        availableCardsListView.setAdapter(availableCardAdapter);
        selectedCardsListView.setAdapter(selectedCardAdapter);

        filterCardsButton.setOnClickListener(v -> startActivityForResult(new Intent(mContext, FilterCardsActivity.class), FILTER_REQUEST_CODE));

        saveListButton.setOnClickListener(v -> {
            String listName = listNameEditText.getText().toString();
            if (listName.isEmpty()) {
                viewToast(mContext, "List name cannot be empty");
                return;
            }
            String userId = LoginActivity.loggedUser.id; // Replace with actual user ID

            //List<ListRequest> listRequests = new ArrayList<>();
            for (Card card : selectedCards) {
                System.out.println("Zie Card Name is "+card.card_name);
                String cardName = card.card_name;
                cardNames.add(cardName);
                int quantity = cardQuantities.get(cardName);
                quantities.add(quantity);
                //listRequests.add(new ListRequest(cardName, quantity));
            }
            //System.out.println(listRequests);

            saveList(listName, userId);
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

    private void addCardToList(Card card) {
        String cardName = card.card_name;
        int quantity = cardQuantities.getOrDefault(cardName, 0);
        cardQuantities.put(cardName, quantity + 1);
        if(cardQuantities.get(cardName) > 3){
            viewToast(mContext, "No more than 3 cards in the list");
            cardQuantities.put(cardName, 3);
        }
        //System.out.println("cardName: "+cardName+" and quantity: "+quantity);
        if (!selectedCards.contains(card)) {
            selectedCards.add(card);
        }
        selectedCardAdapter.notifyDataSetChanged();
    }

    protected void removeCardFromList(Card card) {
        String cardName = card.card_name;
        int quantity = cardQuantities.getOrDefault(cardName, 0);

        if (quantity > 1) {
            cardQuantities.put(cardName, quantity - 1);
        } else {
            cardQuantities.remove(cardName);
            selectedCards.remove(card);
        }

        selectedCardAdapter.notifyDataSetChanged();
    }

    private void saveList(String listName, String userId) {
        mApiService.createList(listName, userId, cardNames, quantities).enqueue(new Callback<BaseResponse<Integer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse<Integer> res = response.body();
                    viewToast(mContext, "List saved with ID: " + res.payload);
                } else {
                    viewToast(mContext, "Error saving list");
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

    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    private class CardAdapter extends BaseAdapter {
        private Context context;
        private List<Card> cardList;

        public CardAdapter(Context context, List<Card> cardList) {
            this.context = context;
            this.cardList = cardList;
        }

        @Override
        public int getCount() {
            return cardList.size();
        }

        @Override
        public Object getItem(int position) {
            return cardList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
            }

            Card card = cardList.get(position);

            TextView nameTextView = convertView.findViewById(R.id.nameTextView);
            TextView classTextView = convertView.findViewById(R.id.classTextView);
            TextView typeTextView = convertView.findViewById(R.id.typeTextView);
            TextView costTextView = convertView.findViewById(R.id.costTextView);
            TextView statsTextView = convertView.findViewById(R.id.statsTextView);
            Button actionButton = convertView.findViewById(R.id.actionButton);

            nameTextView.setText(card.card_name);
            classTextView.setText(String.valueOf(card.sveclass));
            typeTextView.setText(String.valueOf(card.type));
            costTextView.setText(String.valueOf(card.cost));
            statsTextView.setText(card.stats);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addCardToList(card);
                }
            });

            return convertView;
        }
    }

    // Adapter for selected cards
    private class SelectedCardAdapter extends BaseAdapter {
        private Context context;
        private List<Card> cardList;
        private Map<String, Integer> cardQuantities;

        public SelectedCardAdapter(Context context, List<Card> cardList, Map<String, Integer> cardQuantities) {
            this.context = context;
            this.cardList = cardList;
            this.cardQuantities = cardQuantities;
        }

        @Override
        public int getCount() {
            return cardList.size();
        }

        @Override
        public Object getItem(int position) {
            return cardList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
            }

            Card card = cardList.get(position);

            TextView nameTextView = convertView.findViewById(R.id.nameTextView);
            TextView classTextView = convertView.findViewById(R.id.classTextView);
            TextView typeTextView = convertView.findViewById(R.id.typeTextView);
            TextView costTextView = convertView.findViewById(R.id.costTextView);
            TextView statsTextView = convertView.findViewById(R.id.statsTextView);
            Button actionButton = convertView.findViewById(R.id.actionButton);

            nameTextView.setText(card.card_name);
            classTextView.setText(card.sveclass);
            typeTextView.setText(card.type);
            costTextView.setText(String.valueOf(card.cost));
            statsTextView.setText(card.stats);

            actionButton.setText("Remove");
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeCardFromList(card);
                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeCardFromList(card);
                }
            });

            return convertView;
        }
    }
}