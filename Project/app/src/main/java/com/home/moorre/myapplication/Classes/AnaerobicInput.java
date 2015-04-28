package com.home.moorre.myapplication.Classes;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moorre on 3/21/2015.
 */
public class AnaerobicInput {
    /** Holds sets reps */
    private List<Set> _sets;
    private int _logId;

    public AnaerobicInput() {
        this._sets = new ArrayList<Set>();
    }

    public AnaerobicInput(List<Set> sets) {
        this._sets = new ArrayList<Set>(sets);
    }

    public int getLogId() {
        return _logId;
    }

    public void setLogId(int _logId) {
        this._logId = _logId;
    }

    public void setSets(List<Set> sets) {
        this._sets = new ArrayList<Set>(sets);
    }

    public List<Set> getSets() {
        return this._sets;
    }
}
