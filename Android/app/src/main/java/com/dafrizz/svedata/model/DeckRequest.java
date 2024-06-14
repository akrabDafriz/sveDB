package com.dafrizz.svedata.model;
import java.util.List;


public class DeckRequest {
    public String cardName;
    public int quantity;

    public DeckRequest(String cardName, int quantity) {
        this.cardName = cardName;
        this.quantity = quantity;
    }
}

