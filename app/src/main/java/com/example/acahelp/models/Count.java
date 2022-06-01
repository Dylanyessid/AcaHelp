package com.example.acahelp.models;

public class Count {
    int positives = 0;
    int negatives = 0;

    public Count(int positives, int negatives) {
        this.positives = positives;
        this.negatives = negatives;
    }

    public int getPositives() {
        return positives;
    }

    public void setPositives(int positives) {
        this.positives = positives;
    }

    public int getNegatives() {
        return negatives;
    }

    public void setNegatives(int negatives) {
        this.negatives = negatives;
    }
}
