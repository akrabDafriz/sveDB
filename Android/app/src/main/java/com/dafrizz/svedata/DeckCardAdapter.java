package com.dafrizz.svedata;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dafrizz.svedata.model.DeckCards;

import java.util.List;

public class DeckCardAdapter extends BaseAdapter {
    private Context context;
    private List<DeckCards> deckCardList;

    public DeckCardAdapter(Context context, List<DeckCards> deckCardList) {
        this.context = context;
        this.deckCardList = deckCardList;
    }

    @Override
    public int getCount() {
        return deckCardList.size();
    }

    @Override
    public Object getItem(int position) {
        return deckCardList.get(position);
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

        DeckCards card = deckCardList.get(position);

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

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CardDetailsActivity.class);
            intent.putExtra("card_id", card.card_id);
            context.startActivity(intent);
        });

        actionButton.setOnClickListener(v -> Log.d("DeckCardAdapter", "Action button clicked for " + card.card_name));

        return convertView;
    }
}

