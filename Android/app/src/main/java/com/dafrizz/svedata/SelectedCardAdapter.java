package com.dafrizz.svedata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dafrizz.svedata.model.Card;

import java.util.List;
import java.util.Map;

public class SelectedCardAdapter extends BaseAdapter {
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
                ((CreateDeckActivity) context).removeCardFromDeck(card);
            }
        });

        return convertView;
    }
}



