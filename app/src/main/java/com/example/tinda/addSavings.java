package com.example.tinda;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class addSavings extends AppCompatActivity {

    private EditText cebuTourText, targetAmountText, targetDateText, addFundsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_savings);

        // Back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(addSavings.this, act_savings.class);
            startActivity(intent);
            finish();
        });

        // Initialize inputs
        cebuTourText = findViewById(R.id.cebuTourText);
        targetAmountText = findViewById(R.id.targetAmountText);
        targetDateText = findViewById(R.id.targetDateText);
        addFundsText = findViewById(R.id.addFundsText);

        // Save button
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveSavings());

        targetDateText.setOnClickListener(v -> {
            // Get the current date
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Create a DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    addSavings.this,
                    (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                        // Format the date as M/D/Y and set it in the EditText
                        String formattedDate = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                        targetDateText.setText(formattedDate);
                    },
                    year, month, day);

            datePickerDialog.show();
        });

    }

    private void saveSavings() {
        // Validate and fetch data
        String tourName = cebuTourText.getText().toString().trim();
        String targetAmountStr = targetAmountText.getText().toString().trim();
        String targetDate = targetDateText.getText().toString().trim();
        String addFundsStr = addFundsText.getText().toString().trim();

        if (tourName.isEmpty() || targetAmountStr.isEmpty() || targetDate.isEmpty() || addFundsStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double targetAmount;
        double addFunds;

        try {
            targetAmount = Double.parseDouble(targetAmountStr);
            addFunds = Double.parseDouble(addFundsStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
            return;
        }
        // Create Savings object
        savings savings = new savings(tourName, targetAmount, targetDate, addFunds);

        // Get Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("savings");

        // Generate a unique key for each savings entry
        String key = databaseRef.push().getKey();

        // Save data to Firebase
        if (key != null) {
            databaseRef.child(key).setValue(savings).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Savings saved successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(this, "Failed to save savings", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Error generating key", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        cebuTourText.setText("");
        targetAmountText.setText("");
        targetDateText.setText("");
        addFundsText.setText("");
    }
}