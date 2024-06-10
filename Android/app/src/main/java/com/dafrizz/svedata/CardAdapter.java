package com.dafrizz.svedata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dafrizz.svedata.model.Card;

import java.util.List;

public class CardAdapter extends BaseAdapter {
    private Context context;
    private List<Card> cardList;

    public CardAdapter(Context context, List<Card> cardList) {
        this.context = context;
        this.cardList = cardList;
        System.out.println("Adapter terpanggil");
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

        nameTextView.setText(card.card_name);
        classTextView.setText(String.valueOf(card.sveclass));
        typeTextView.setText(String.valueOf(card.type));
        costTextView.setText(String.valueOf(card.cost));
        statsTextView.setText(card.stats);

        return convertView;
    }
}
