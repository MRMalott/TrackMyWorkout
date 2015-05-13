package com.home.moorre.myapplication.Classes;

/**
 * Created by Moorre on 2/13/2015.
 */
public class WorkoutRegions {
    private int _workoutId, _regionId;
    private boolean _main;

    public WorkoutRegions() {

    }

    public WorkoutRegions(int id, int regionId, boolean main) {
        this._workoutId = id;
        this._regionId = regionId;
        this._main = main;
    }

    public int getWorkoutId() {
        return _workoutId;
    }

    public void setWorkoutId(int _workoutId) {
        this._workoutId = _workoutId;
    }

    public int getRegionId() {
        return _regionId;
    }

    public void setRegionId(int _regionId) {
        this._regionId = _regionId;
    }

    public boolean getMain() {
        return _main;
    }

    public void setMain(boolean main) {
        this._main = main;
    }

}
