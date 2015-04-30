package com.home.moorre.myapplication.Classes;

/**
 * Created by Moorre on 2/25/2015.
 */
public class MuscleGroup {
    private int _id;
    // can be arms, chest, abdominal, legs
    private String _name;

    public MuscleGroup() {

    }

    public MuscleGroup(int id, String name) {
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

    public void setId(int id) {
        this._id = id;
    }

}
