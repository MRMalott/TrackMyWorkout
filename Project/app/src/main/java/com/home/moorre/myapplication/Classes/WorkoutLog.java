package com.home.moorre.myapplication.Classes;

/**
 * Created by Moorre on 3/21/2015.
 */
public class WorkoutLog {
    private int _id;
    private long _workoutDate;
    private long _creationDate;
    private Workout _loggedWorkout;
    private String workoutName;
    private int workoutId;
    private AerobicInput _aerobic;
    private AnaerobicInput _anaerobic;
    private boolean _usesSets;
    private String _notes;

    public void WorkoutLog() {

    }


    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public long getCreationDate() {
        return _creationDate;
    }

    public void setCreationDate(long _creationDate) {
        this._creationDate = _creationDate;
    }

    public Workout getLoggedWorkout() {
        return _loggedWorkout;
    }

    public void setLoggedWorkout(Workout _loggedWorkout) {
        this._loggedWorkout = _loggedWorkout;
    }

    public void setAerobic(AerobicInput aerobicInputs) {
        this._aerobic = aerobicInputs;
    }

    public AerobicInput getAerobic() {
        return this._aerobic;
    }

    public void setAnaerobic(AnaerobicInput anaerobicInputs) {
        this._anaerobic = anaerobicInputs;
    }

    public AnaerobicInput getAnaerobic() {
        return this._anaerobic;
    }

    public void setUsesSets(boolean isAerobic) {
        this._usesSets = isAerobic;
    }

    public boolean usesSets() {
        return _usesSets;
    }

    public String getNotes() {
        return _notes;
    }

    public void setNotes(String _notes) {
        this._notes = _notes;
    }

    public long getWorkoutDate() {
        return _workoutDate;
    }

    public void setWorkoutDate(long _workoutDate) {
        this._workoutDate = _workoutDate;
    }
}
