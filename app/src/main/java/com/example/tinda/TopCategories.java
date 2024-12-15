package com.example.tinda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tinda.MainActivity;

public class TopCategories extends AppCompatActivity {
    private ImageButton backBtnn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for this activity
        setContentView(R.layout.activity_top_categories);

        // Initialize the back button
        backBtnn = findViewById(R.id.backBtnn);

        backBtnn.setOnClickListener(view -> {
            // Navigate back to the SecondAct activity
            Intent intent = new Intent(TopCategories.this, MainActivity.class);
            startActivity(intent);
        });

        // Set up padding to handle system insets for edge-to-edge design
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.insightTxt), (v, insets) -> {
            WindowInsetsCompat windowInsets = insets;
            v.setPadding(
                    windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insets;
        });
    }
}