package com.home.moorre.myapplication.DB;

import android.content.Context;
import android.graphics.Bitmap;
import com.home.moorre.myapplication.Classes.Workout;
import java.util.ArrayList;

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
        return !(!addPushup() || !addSitup() || !addBicepCurl() || !addBenchPress() || !addWristCurl()
                || !addFlutterKick() || !addBarbellRow() || !addBenchDip() || !addRearDeltRow()
                || !addTreadmillJog() || !addChildsPose() || !addSprint());
    }

    private boolean addPushup() {
        Workout workout = new Workout();
        workout.setName("push up");
        workout.setDescription("Get down on stomach and push up, lower down.");
        workout.setPictures(new ArrayList<Bitmap>());
        workout.setCheckMainRegions(true);
        workout.setCheckSubRegions(true);
        workout.setCheckPictures(false);
        workout.setMuscleGroupId(2);
        workout.setWorkoutTypeId(1);
        ArrayList<String> mainregions = new ArrayList<>(2);
        mainregions.add("triceps");
        mainregions.add("pectorals");
        workout.setMainRegions(mainregions);
        ArrayList<String> subregions = new ArrayList<>(2);
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

    private boolean addSitup() {
        Workout workout = new Workout();
        workout.setName("sit up");
        workout.setDescription("Get down on back and sit up, recline.");
        workout.setPictures(new ArrayList<Bitmap>());
        workout.setCheckMainRegions(true);
        workout.setCheckSubRegions(true);
        workout.setCheckPictures(false);
        workout.setMuscleGroupId(3);
        workout.setWorkoutTypeId(1);
        ArrayList<String> mainregions = new ArrayList<>(1);
        mainregions.add("abs");
        workout.setMainRegions(mainregions);
        ArrayList<String> subregions = new ArrayList<>(1);
        subregions.add("abs");
        workout.setSubRegions(subregions);

        try {
            db.addWorkout(workout);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private boolean addBicepCurl() {
        Workout workout = new Workout();
        workout.setName("bicep curl");
        workout.setDescription("Raise weight with one arm and then raise with the other arm.");
        workout.setPictures(new ArrayList<Bitmap>());
        workout.setCheckMainRegions(true);
        workout.setCheckSubRegions(true);
        workout.setCheckPictures(false);
        workout.setMuscleGroupId(2);
        workout.setWorkoutTypeId(1);
        ArrayList<String> mainregions = new ArrayList<>(1);
        mainregions.add("biceps");
        workout.setMainRegions(mainregions);
        ArrayList<String> subregions = new ArrayList<>(3);
        subregions.add("triceps");
        subregions.add("forearms");
        workout.setSubRegions(subregions);

        try {
            db.addWorkout(workout);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private boolean addBenchPress() {
        Workout workout = new Workout();
        workout.setName("bench press");
        workout.setDescription("Get on back and press weights up, lower weights down.");
        workout.setPictures(new ArrayList<Bitmap>());
        workout.setCheckMainRegions(true);
        workout.setCheckSubRegions(true);
        workout.setCheckPictures(false);
        workout.setMuscleGroupId(1);
        workout.setWorkoutTypeId(1);
        ArrayList<String> mainregions = new ArrayList<>(2);
        mainregions.add("pectorals");
        workout.setMainRegions(mainregions);
        ArrayList<String> subregions = new ArrayList<>(1);
        subregions.add("triceps");
        subregions.add("deltoids");
        workout.setSubRegions(subregions);

        try {
            db.addWorkout(workout);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private boolean addWristCurl() {
        Workout workout = new Workout();
        workout.setName("wrist curl");
        workout.setDescription("Moving only the wrists, curl weight up then down.");
        workout.setPictures(new ArrayList<Bitmap>());
        workout.setCheckMainRegions(true);
        workout.setCheckSubRegions(true);
        workout.setCheckPictures(false);
        workout.setMuscleGroupId(2);
        workout.setWorkoutTypeId(1);
        ArrayList<String> mainregions = new ArrayList<>(1);
        mainregions.add("forearms");
        workout.setMainRegions(mainregions);
        ArrayList<String> subregions = new ArrayList<>(1);
        subregions.add("forearms");
        workout.setSubRegions(subregions);

        try {
            db.addWorkout(workout);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private boolean addFlutterKick() {
        Workout workout = new Workout();
        workout.setName("flutter kicks");
        workout.setDescription("Lay face down on bench and keep legs straight. Kick up and down.");
        workout.setPictures(new ArrayList<Bitmap>());
        workout.setCheckMainRegions(true);
        workout.setCheckSubRegions(true);
        workout.setCheckPictures(false);
        workout.setMuscleGroupId(5);
        workout.setWorkoutTypeId(1);
        ArrayList<String> mainregions = new ArrayList<>(1);
        mainregions.add("glutes");
        workout.setMainRegions(mainregions);
        ArrayList<String> subregions = new ArrayList<>(1);
        subregions.add("hamstrings");
        workout.setSubRegions(subregions);

        try {
            db.addWorkout(workout);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private boolean addBarbellRow() {
        Workout workout = new Workout();
        workout.setName("barbell row");
        workout.setDescription("Lean forward and pull weight up and inward. Then lower weight.");
        workout.setPictures(new ArrayList<Bitmap>());
        workout.setCheckMainRegions(true);
        workout.setCheckSubRegions(true);
        workout.setCheckPictures(false);
        workout.setMuscleGroupId(4);
        workout.setWorkoutTypeId(1);
        ArrayList<String> mainregions = new ArrayList<>(1);
        mainregions.add("mid back");
        workout.setMainRegions(mainregions);
        ArrayList<String> subregions = new ArrayList<>(3);
        subregions.add("biceps");
        subregions.add("lats");
        subregions.add("deltoids");
        workout.setSubRegions(subregions);

        try {
            db.addWorkout(workout);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private boolean addBenchDip() {
        Workout workout = new Workout();
        workout.setName("bench dip");
        workout.setDescription("Keep elbows in and lower your body. Then raise back up.");
        workout.setPictures(new ArrayList<Bitmap>());
        workout.setCheckMainRegions(true);
        workout.setCheckSubRegions(true);
        workout.setCheckPictures(false);
        workout.setMuscleGroupId(2);
        workout.setWorkoutTypeId(1);
        ArrayList<String> mainregions = new ArrayList<>(1);
        mainregions.add("triceps");
        workout.setMainRegions(mainregions);
        ArrayList<String> subregions = new ArrayList<>(2);
        subregions.add("pectorals");
        subregions.add("deltoids");
        workout.setSubRegions(subregions);

        try {
            db.addWorkout(workout);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private boolean addRearDeltRow() {
        Workout workout = new Workout();
        workout.setName("rear delt row");
        workout.setDescription("Lean forward and pull weight up, lower weight.");
        workout.setPictures(new ArrayList<Bitmap>());
        workout.setCheckMainRegions(true);
        workout.setCheckSubRegions(true);
        workout.setCheckPictures(false);
        workout.setMuscleGroupId(0);
        workout.setWorkoutTypeId(1);
        ArrayList<String> mainregions = new ArrayList<>(1);
        mainregions.add("deltoids");
        workout.setMainRegions(mainregions);
        ArrayList<String> subregions = new ArrayList<>(3);
        subregions.add("biceps");
        subregions.add("lats");
        subregions.add("mid back");
        workout.setSubRegions(subregions);

        try {
            db.addWorkout(workout);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private boolean addTreadmillJog() {
        Workout workout = new Workout();
        workout.setName("treadmill jog");
        workout.setDescription("Run in place using proper form.");
        workout.setPictures(new ArrayList<Bitmap>());
        workout.setCheckMainRegions(true);
        workout.setCheckSubRegions(true);
        workout.setCheckPictures(false);
        workout.setMuscleGroupId(5);
        workout.setWorkoutTypeId(0);
        ArrayList<String> mainregions = new ArrayList<>(1);
        mainregions.add("quads");
        workout.setMainRegions(mainregions);
        ArrayList<String> subregions = new ArrayList<>(2);
        subregions.add("hamstrings");
        subregions.add("glutes");
        workout.setSubRegions(subregions);

        try {
            db.addWorkout(workout);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private boolean addSprint() {
        Workout workout = new Workout();
        workout.setName("sprints");
        workout.setDescription("Run fast for a short distance. Take a short rest and repeat.");
        workout.setPictures(new ArrayList<Bitmap>());
        workout.setCheckMainRegions(true);
        workout.setCheckSubRegions(true);
        workout.setCheckPictures(false);
        workout.setMuscleGroupId(5);
        workout.setWorkoutTypeId(0);
        ArrayList<String> mainregions = new ArrayList<>(1);
        mainregions.add("quads");
        workout.setMainRegions(mainregions);
        ArrayList<String> subregions = new ArrayList<>(2);
        subregions.add("hamstrings");
        subregions.add("glutes");
        workout.setSubRegions(subregions);

        try {
            db.addWorkout(workout);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private boolean addChildsPose() {
        Workout workout = new Workout();
        workout.setName("child's pose");
        workout.setDescription("Pull arms back in toward your body slowly.");
        workout.setPictures(new ArrayList<Bitmap>());
        workout.setCheckMainRegions(true);
        workout.setCheckSubRegions(true);
        workout.setCheckPictures(false);
        workout.setMuscleGroupId(4);
        workout.setWorkoutTypeId(2);
        ArrayList<String> mainregions = new ArrayList<>(1);
        mainregions.add("lower back");
        workout.setMainRegions(mainregions);
        ArrayList<String> subregions = new ArrayList<>(2);
        subregions.add("mid back");
        subregions.add("glutes");
        workout.setSubRegions(subregions);

        try {
            db.addWorkout(workout);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
