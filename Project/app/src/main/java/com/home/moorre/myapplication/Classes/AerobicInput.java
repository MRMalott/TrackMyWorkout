package com.home.moorre.myapplication.Classes;


/**
 * Created by Moorre on 3/21/2015.
 * Class for logged aerobic inputs with time, distance, etc
 */
public class AerobicInput {
    private int _logId, _seconds, _feet;

    public void AeroebicInput() {

    }

    public int getLogId() {
        return _logId;
    }

    public void setLogId(int _logId) {
        this._logId = _logId;
    }

    public int getSeconds() {
        return _seconds;
    }

    public void setSeconds(int _seconds) {
        this._seconds = _seconds;
    }

    public int getFeet() {
        return _feet;
    }

    public void setFeet(int _feet) {
        this._feet = _feet;
    }
}
