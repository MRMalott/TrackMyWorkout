package com.home.moorre.myapplication.Classes;

/**
 * Created by Moorre on 2/14/2015.
 */
public class Regions {
    private int _id;
    private String _name;

    public Regions() {

    }

    public Regions(int id, String name) {
        this._id = id;
        this._name = name;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

}
