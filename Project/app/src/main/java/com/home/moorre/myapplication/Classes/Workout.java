package com.home.moorre.myapplication.Classes;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Moorre on 2/5/2015.
 */
public class Workout {
    private int _id;
    private String _name, _description;
    private ArrayList<Bitmap> _pictures;
    private int  _workoutTypeId, _muscleGroupId;
    private ArrayList<String> _mainRegions, _subRegions;
    private boolean _checkPictures, _checkMainRegions,  _checkSubRegions;
    // has foreign keys to _pictures and regions tables

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


    public int getMuscleGroupId() {
        return _muscleGroupId;
    }

    public void setMuscleGroupId(int _muscleGroupId) {
        this._muscleGroupId = _muscleGroupId;
    }

    public int getWorkoutTypeId() {
        return _workoutTypeId;
    }

    public void setWorkoutTypeId(int _workoutTypeId) {
        this._workoutTypeId = _workoutTypeId;
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

    public ArrayList<Bitmap> getPictures() {
        return _pictures;
    }

    public void setPictures(ArrayList<Bitmap> _pictures) {
        this._pictures = new ArrayList<Bitmap>(_pictures);
    }

    public ArrayList<String> getMainRegions() {
        return _mainRegions;
    }

    public void setMainRegions(ArrayList<String> _mainRegions) {
        this._mainRegions = new ArrayList<String>(_mainRegions);
    }

    public ArrayList<String> getSubRegions() {
        return _subRegions;
    }

    public void setSubRegions(ArrayList<String> subRegions) {
        this._subRegions = new ArrayList<String>(subRegions);
    }

    public boolean getCheckPictures() {
        return _checkPictures;
    }

    public void setCheckPictures(boolean _checkPictures) {
        this._checkPictures = _checkPictures;
    }

    public boolean getCheckMainRegions() {
        return _checkMainRegions;
    }

    public void setCheckMainRegions(boolean _checkRegions) {
        this._checkMainRegions = _checkRegions;
    }

    public boolean getCheckSubRegions() {
        return _checkSubRegions;
    }

    public void setCheckSubRegions(boolean _checkRegions) {
        this._checkSubRegions = _checkRegions;
    }
}
