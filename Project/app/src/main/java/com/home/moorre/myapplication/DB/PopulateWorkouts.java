package com.home.moorre.myapplication.DB;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;

import com.home.moorre.myapplication.Classes.Workout;

import java.util.ArrayList;

/**
 * Created by mrm1992 on 3/19/2015.
 */
public class PopulateWorkouts {
    MyDBHandler db;

    public PopulateWorkouts(Context context) {
        db = new MyDBHandler(context, null, null, 1);
    }

    /**
     * Add initial workouts to the database
     * @return whether it uploaded all the workouts
     */
    public boolean generateWorkouts() {
        if (!addPushup()) {
            return false;
        }

        return true;
    }

    private boolean addPushup() {
        Workout workout = new Workout();
        workout.setId(0);
        workout.setName("push up");
        workout.setDescription("Get down push up");
        workout.setPictures(new ArrayList<Bitmap>());
        workout.setCheckMainRegions(true);
        workout.setCheckSubRegions(true);
        workout.setCheckPictures(false);
        workout.setMuscleGroupId(2);
        workout.setWorkoutTypeId(0);
        ArrayList<String> mainregions = new ArrayList<String>(2);
        mainregions.add("triceps");
        mainregions.add("pectorals");
        workout.setMainRegions(mainregions);
        ArrayList<String> subregions = new ArrayList<String>(2);
        subregions.add("deltoids");
        subregions.add("abs");
        workout.setSubRegions(subregions);

        try {
            db.addWorkout(workout);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
