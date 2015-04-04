package com.home.moorre.myapplication.Classes;

import android.graphics.Bitmap;

/**
 * Created by Moorre on 2/13/2015.
 */
public class Picture {
    private int _id;
    private Bitmap _pic;
    private boolean _main;

    public Picture(int id, Bitmap pic, boolean main) {
        this._id = id;
        this._pic = pic;
        this._main = main;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public Bitmap getPic() {
        return _pic;
    }

    public void setPic(Bitmap pic) {
        this._pic = pic;
    }

    public boolean getMain() {
        return _main;
    }

    public void setMain(boolean _main) {
        this._main = _main;
    }

}
