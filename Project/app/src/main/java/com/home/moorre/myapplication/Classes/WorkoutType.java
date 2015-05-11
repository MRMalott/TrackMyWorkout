package com.home.moorre.myapplication.Classes;

/**
 * Created by Moorre on 2/25/2015.
 */
public class WorkoutType {
    private int _id;
    // is pre-populated with aerobic, and anaerobic
    private String _name;

    public WorkoutType() {

    }

    public WorkoutType(int id, String name) {
        this._id = id;
        this._name = name;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }
}
