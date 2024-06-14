package com.dafrizz.svedata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.dafrizz.svedata.request.BaseAPIService;
import com.dafrizz.svedata.request.UtilsAPI;
import com.dafrizz.svedata.model.Card;
import android.widget.Toast;

public class CardDetailsActivity extends AppCompatActivity {
    private TextView cardNameTextView;
    private TextView classTextView;
    private TextView typeTextView;
    private TextView costTextView;
    private TextView statsTextView;
    private TextView effectsTextView;
    private TextView rarityTextView;
    private String cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        cardNameTextView = findViewById(R.id.card_name);
        classTextView = findViewById(R.id.card_class);
        typeTextView = findViewById(R.id.card_type);
        costTextView = findViewById(R.id.card_cost);
        statsTextView = findViewById(R.id.card_stats);
        effectsTextView = findViewById(R.id.card_effects);
        rarityTextView = findViewById(R.id.card_rarity);

        cardId = getIntent().getStringExtra("card_id");
        Card card = getCardById(cardId);

        if (card != null) {
            displayCardDetails(card);
        } else {
            Toast.makeText(this, "Card not found", Toast.LENGTH_SHORT).show();
        }
    }

    private Card getCardById(String cardId) {
        if (AllCardsActivity.cardList != null) {
            for (Card card : AllCardsActivity.cardList) {
                if (card.id.equals(cardId)) {
                    return card;
                }
            }
        }
        return null;
    }


    private void displayCardDetails(Card card) {
        cardNameTextView.setText(card.card_name);
        classTextView.setText(card.sveclass);
        typeTextView.setText(card.type);
        costTextView.setText(String.valueOf(card.cost));
        statsTextView.setText(card.stats);
        effectsTextView.setText(card.effects);
        rarityTextView.setText(card.rarity);
    }
}

