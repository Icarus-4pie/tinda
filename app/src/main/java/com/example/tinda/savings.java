package com.example.tinda;

public class savings {  // Class name should be in PascalCase
    private String tourName;
    private double targetAmount;
    private String targetDate;
    private double addFunds;

    // Constructor with parameters to initialize the fields
    public savings(String tourName, double targetAmount, String targetDate, double addFunds) {
        this.tourName = tourName;
        this.targetAmount = targetAmount;
        this.targetDate = targetDate;
        this.addFunds = addFunds;
    }

    // Getter and Setter methods for each field
    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public double getAddFunds() {
        return addFunds;
    }

    public void setAddFunds(double addFunds) {
        this.addFunds = addFunds;
    }
}
