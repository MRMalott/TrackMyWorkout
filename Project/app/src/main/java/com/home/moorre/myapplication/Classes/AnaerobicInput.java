package com.home.moorre.myapplication.Classes;

import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by Moorre on 3/21/2015.
 */
public class AnaerobicInput {
    /** Holds sets reps */
    private ArrayList<Set> _sets;
    private int _logId;

    public void AnaerobicInput(ArrayList<Set> sets) {
        this._sets = new ArrayList<Set>(sets);
    }

    public int getLogId() {
        return _logId;
    }

    public void setLogId(int _logId) {
        this._logId = _logId;
    }

    public void setSets(ArrayList<Set> sets) {
        this._sets = new ArrayList<Set>(sets);
    }

    public ArrayList<Set> getSets() {
        return this._sets;
    }
}
