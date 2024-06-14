package com.dafrizz.svedata.model;
import java.util.List;


public class DeckRequest {
    public String card_name;
    public int quantity;

    public DeckRequest(String cardName, int quantity) {
        this.card_name = cardName;
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return "cardName: "+this.card_name+" and Quantity: "+this.quantity;
    }
}

