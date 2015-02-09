package com.home.moorre.myapplication;

/**
 * Created by Moorre on 2/5/2015.
 */
public class Workout {
    private int _id;
    private String _name, _description;

    public Workout() {

    }

    public Workout(int inId, String inName, String inDescription) {
        this._id = inId;
        this._name = inName;
        this._description = inDescription;
    }

    public Workout(String inName, String inDescription) {
        this._name = inName;
        this._description = inDescription;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String _description) {
        this._description = _description;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }
}
