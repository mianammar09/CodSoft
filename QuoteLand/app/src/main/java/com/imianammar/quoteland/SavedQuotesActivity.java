package com.imianammar.quoteland;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SavedQuotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_quotes);

        TextView savedQuotesTextView = findViewById(R.id.saved_quotes_text);
        SharedPreferences preferences = getSharedPreferences("SavedQuotes", Context.MODE_PRIVATE);

        // Retrieve saved quotes
        String savedQuotes = preferences.getString("quotes", "");
        savedQuotesTextView.setText(savedQuotes);
    }
}

