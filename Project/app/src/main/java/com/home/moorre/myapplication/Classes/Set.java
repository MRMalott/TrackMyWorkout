package com.home.moorre.myapplication.Classes;

/**
 * Created by Moorre on 3/21/2015.
 * Holds reps(lbs) and weight for the set
 */
public class Set {
    private int weight, reps; //lbs

    public Set(int reps, int weight) {
        this.weight = weight;
        this.reps = reps;
    }

    public Set() {
    }


    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
