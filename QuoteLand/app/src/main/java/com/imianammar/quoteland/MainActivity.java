package com.imianammar.quoteland;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView quoteTextView;
    private TextView authorTextView;
    private Button saveButton, savedButton;

    // Define an array of quotes and authors
    private String[] quotes = {
            "In the middle of every difficulty lies opportunity.",
            "The only way to do great work is to love what you do.",
            "Believe you can and you're halfway there.",
            "The best way to predict the future is to invent it.",
            "The only thing we have to fear is fear itself.",
            "It does not matter how slowly you go as long as you do not stop.",
            "The only person you are destined to become is the person you decide to be.",
            "Don't watch the clock; do what it does. Keep going.",
            "The future belongs to those who believe in the beauty of their dreams.",
            "In the end, we will remember not the words of our enemies, but the silence of our friends.",

    };

    private String[] authors = {
            "- Albert Einstein",
            "- Steve Jobs",
            "- Theodore Roosevelt",
            "- Alan Kay",
            "- Franklin D. Roosevelt",
            "- Confucius",
            "- Ralph Waldo Emerson",
            "- Sam Levenson",
            "- Eleanor Roosevelt",
            "- Martin Luther King Jr.",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteTextView = findViewById(R.id.quote_text);
        authorTextView = findViewById(R.id.author_text);
        saveButton = findViewById(R.id.save_button);
        savedButton = findViewById(R.id.show_saved_button);

        // Fetch a random quote
        showRandomQuote();
        scheduleNotification();

        savedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SavedQuotesActivity.class);
                startActivity(intent);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentQuote = quoteTextView.getText().toString();
                String currentAuthor = authorTextView.getText().toString();
                saveQuote(currentQuote, currentAuthor);
            }
        });
    }

    private void scheduleNotification() {
        Intent intent = new Intent(this, QuoteNotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8); // Set the hour when you want to receive the notification (e.g., 0 for midnight)

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void saveQuote(String quote, String author) {
        SharedPreferences preferences = getSharedPreferences("SavedQuotes", Context.MODE_PRIVATE);
        String savedQuotes = preferences.getString("quotes", "");

        // Check if the quote is already in the saved quotes
        if (savedQuotes.contains(quote)) {
            Toast.makeText(this, "Quote already saved!", Toast.LENGTH_SHORT).show();
            return; // Exit the method
        }

        // If not, append the new quote to the existing ones
        savedQuotes = savedQuotes + "\n\n" + quote + " - " + author;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("quotes", savedQuotes);
        editor.apply();

        Toast.makeText(this, "Quote saved!", Toast.LENGTH_SHORT).show();
    }


    private void showRandomQuote() {
        SharedPreferences preferences = getSharedPreferences("QuotePreferences", Context.MODE_PRIVATE);
        long lastChangeTime = preferences.getLong("lastChangeTime", 0);

        long currentTime = System.currentTimeMillis();
        long timeDiff = currentTime - lastChangeTime;
        long oneDayInMillis = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

        String savedQuote = preferences.getString("savedQuote", "");
        String savedAuthor = preferences.getString("savedAuthor", "");

        if (timeDiff >= oneDayInMillis || lastChangeTime == 0 || savedQuote.isEmpty()) {
            // Change the quote if 24 hours have passed since last change, if it's the first time, or if there's no saved quote
            Random random = new Random();
            int randomIndex = random.nextInt(quotes.length);
            String randomQuote = quotes[randomIndex];
            String randomAuthor = authors[randomIndex];
            quoteTextView.setText(randomQuote);
            authorTextView.setText(randomAuthor);

            // Update the last change time and save the new quote
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong("lastChangeTime", currentTime);
            editor.putString("savedQuote", randomQuote);
            editor.putString("savedAuthor", randomAuthor);
            editor.apply();
        } else {
            // Show the saved quote
            quoteTextView.setText(savedQuote);
            authorTextView.setText(savedAuthor);
        }
    }




}

