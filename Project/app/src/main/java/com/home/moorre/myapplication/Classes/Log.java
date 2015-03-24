package com.home.moorre.myapplication.Classes;

import java.lang.reflect.Array;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Moorre on 3/21/2015.
 */
public class Log {
    private int _id;
    private Date _workoutDate;
    private Timestamp _creationDate;
    private Workout _loggedWorkout;
    private AerobicInput _aerobic;
    private AnaerobicInput _anaerobic;
    private boolean _isAerobic;
    private String _notes;

    public void Log() {

    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public Timestamp getCreationDate() {
        return _creationDate;
    }

    public void setCreationDate(Timestamp _creationDate) {
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

    public void setIsAerobic(boolean isAerobic) {
        this._isAerobic = isAerobic;
    }

    public boolean isAerobic() {
        return _isAerobic;
    }

    public String getNotes() {
        return _notes;
    }

    public void setNotes(String _notes) {
        this._notes = _notes;
    }

    public Date getWorkoutDate() {
        return _workoutDate;
    }

    public void setWorkoutDate(Date _workoutDate) {
        this._workoutDate = _workoutDate;
    }
}
