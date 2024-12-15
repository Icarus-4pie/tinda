package com.example.tinda;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class act_savings extends AppCompatActivity {
    private Button savingButton;
    private RecyclerView recyclerView;
    private SavingsAdapter adapter;
    private List<Savings> savingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_savings);

        // Initialize UI components
        savingButton = findViewById(R.id.addSavingsButton);
        recyclerView = findViewById(R.id.recyclerView);

        // Configure RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        savingsList = new ArrayList<>();
        adapter = new SavingsAdapter(savingsList);
        recyclerView.setAdapter(adapter);

        // Load data from Firebase
        loadSavingsData();

        // Add new savings button click listener
        savingButton.setOnClickListener(v -> {
            Intent intent = new Intent(act_savings.this, addSavings.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadSavingsData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("savings");

        // Notify user that data fetching has started
        Toast.makeText(this, "Attempting to fetch savings data...", Toast.LENGTH_SHORT).show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear existing data to avoid duplicates
                savingsList.clear();

                if (snapshot.exists() && snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String tourName = data.child("tourName").getValue(String.class);
                        Double targetAmount = data.child("targetAmount").getValue(Double.class);
                        String targetDate = data.child("targetDate").getValue(String.class);
                        Double addFunds = data.child("addFunds").getValue(Double.class);

                        // Provide default values if any field is null
                        if (tourName == null) tourName = "Unnamed Tour";
                        if (targetAmount == null) targetAmount = 0.0;
                        if (targetDate == null) targetDate = "No Target Date";
                        if (addFunds == null) addFunds = 0.0;

                        savingsList.add(new Savings(tourName, targetAmount, targetDate, addFunds));
                    }
                    Toast.makeText(act_savings.this, "Savings data fetched successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    // No data exists in the database
                    Toast.makeText(act_savings.this, "No savings data found.", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Notify the user of the error
                Toast.makeText(act_savings.this, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

// RecyclerView Adapter
class SavingsAdapter extends RecyclerView.Adapter<SavingsAdapter.SavingsViewHolder> {
    private final List<Savings> savingsList;

    public SavingsAdapter(List<Savings> savingsList) {
        this.savingsList = savingsList;
    }

    @NonNull
    @Override
    public SavingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.act_svng_recycler_item2, parent, false);
        return new SavingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavingsViewHolder holder, int position) {
        Savings currentSavings = savingsList.get(position);

        // Set data to views with null-safe checks
        holder.tourName.setText(currentSavings.getTourName() != null ? currentSavings.getTourName() : "Unnamed Tour");
        holder.targetDate.setText("by: " +
                (currentSavings.getTargetDate() != null && !currentSavings.getTargetDate().isEmpty()
                        ? currentSavings.getTargetDate()
                        : "No Target Date"));
        holder.progressAmount.setText(
                String.format("%.2f of %.2f PHP", currentSavings.getAddFunds(), currentSavings.getTargetAmount())
        );

        // Avoid division by zero
        double percentage = currentSavings.getTargetAmount() > 0
                ? (currentSavings.getAddFunds() / currentSavings.getTargetAmount()) * 100
                : 0.0;
        holder.progressPercentage.setText(String.format("%.2f%%", percentage));
    }

    @Override
    public int getItemCount() {
        return savingsList.size();
    }
    public static class SavingsViewHolder extends RecyclerView.ViewHolder {
        TextView tourName, targetDate, progressAmount, progressPercentage;

        public SavingsViewHolder(@NonNull View itemView) {
            super(itemView);
            tourName = itemView.findViewById(R.id.savingsName);
            targetDate = itemView.findViewById(R.id.savingsDate);
            progressAmount = itemView.findViewById(R.id.currentAmount);
            progressPercentage = itemView.findViewById(R.id.progressPercentage);
        }
    }
}

// Savings Data Model
class Savings {
    private String tourName;
    private Double targetAmount;
    private String targetDate;
    private Double addFunds;

    public Savings(String tourName, Double targetAmount, String targetDate, Double addFunds) {
        this.tourName = tourName;
        this.targetAmount = targetAmount;
        this.targetDate = targetDate;
        this.addFunds = addFunds;
    }

    public String getTourName() {
        return tourName;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public Double getAddFunds() {
        return addFunds;
    }
}
