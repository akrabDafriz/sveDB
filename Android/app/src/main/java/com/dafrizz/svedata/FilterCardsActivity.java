package com.dafrizz.svedata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dafrizz.svedata.request.BaseAPIService;
import com.dafrizz.svedata.request.UtilsAPI;

import java.util.HashMap;

public class FilterCardsActivity extends AppCompatActivity {
    private Context mContext;
    private EditText nameEditText;
    private Spinner classSpinner;
    private Spinner typeSpinner;
    private EditText costEditText;
    private EditText statsEditText;
    private EditText effectsEditText;
    private Button applyFilterButton;

    private String[] classes = {"-", "Forestcraft", "Swordcraft", "Dragoncraft", "Runecraft", "Abysscraft", "Havencraft", "Neutral"};
    private String[] types = {"-", "Leader", "Follower", "Spell", "Amulet", "Evolved Follower", "Follower Token",
            "SpellToken", "AmuletToken"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_cards);

        nameEditText = findViewById(R.id.nameEditText);
        classSpinner = findViewById(R.id.classSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        costEditText = findViewById(R.id.costEditText);
        statsEditText = findViewById(R.id.statsEditText);
        effectsEditText = findViewById(R.id.effectsEditText);
        applyFilterButton = findViewById(R.id.applyFilterButton);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classes);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        applyFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> filters = new HashMap<>();

                String name = nameEditText.getText().toString().trim();
                if (!name.isEmpty()) {
                    filters.put("name", name);
                }

                String selectedClass = classSpinner.getSelectedItem().toString();
                if (!selectedClass.equals("-")) {
                    filters.put("sveClass", selectedClass);
                }

                String selectedType = typeSpinner.getSelectedItem().toString();
                if (!selectedType.equals("-")) {
                    filters.put("type", selectedType);
                }

                String cost = costEditText.getText().toString().trim();
                if (!cost.isEmpty()) {
                    filters.put("cost", cost);
                }

                String stats = statsEditText.getText().toString().trim();
                if (!stats.isEmpty()) {
                    filters.put("stats", stats);
                }

                String effects = effectsEditText.getText().toString().trim();
                if (!effects.isEmpty()) {
                    filters.put("effects", effects);
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("filters", filters);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}