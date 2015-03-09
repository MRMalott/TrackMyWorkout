package com.home.moorre.myapplication;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.graphics.Bitmap;

        import com.home.moorre.myapplication.Classes.Workout;

        import java.io.ByteArrayOutputStream;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.HashMap;
        import java.util.Map;


/**
 * Created by Moorre on 2/5/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static  final String DATABASE_NAME = "healthDB.db";

    private static  final String TABLE_WORKOUTS = "workouts";
    // column names for workouts table
    private static  final String COL_ID = "_id";
    private static  final String COL_NAME = "_name";
    private static  final String COL_DESC = "_description";
    private static final String COL_WORKOUT_TYPE_ID = "_workoutTypeId";
    private static final String COL_MUSCLE_GROUP_ID = "_muscleGroupId";
    private static final String COL_CHECK_PICTURES = "_checkPictures";
    private static final String COL_CHECK_MAIN_REGIONS = "_checkMainRegions";
    private static final String COL_CHECK_SUB_REGIONS = "_checkSubRegions";


    private static final String TABLE_PICTURES = "pictures";
    // column names for pictures table
    private static final String COL_WORKOUT_ID = "_workoutId";
    private static final String COL_PIC = "_path";
    private static final String COL_MAIN = "_main";

    private static final String TABLE_WORKOUT_REGIONS = "workoutRegions";
    // column names for workout table
    private static final String COL_REGION_ID = "_regionId";

    private static final String TABLE_REGIONS = "regions";
    // column names for regions tables

    private static final String TABLE_WORKOUT_TYPES = "workoutTypes";
    // col id col name

    private static final String TABLE_MUSCLE_GROUPS = "muscleGroups";
    // col id col name

    // Contains names to id values for different tables
    public static Map<String, Integer> REGION_IDS;
    static {
        Map<String, Integer> mapping = new HashMap<String, Integer>();
        // regions
        mapping.put("trapezius", 0);
        mapping.put("deltoids", 1);
        mapping.put("pectorals", 2);
        mapping.put("triceps", 3);
        mapping.put("biceps", 4);
        mapping.put("abs", 5);
        mapping.put("forearms", 6);
        mapping.put("quads", 7);
        mapping.put("calves", 8);
        mapping.put("lats", 9);
        mapping.put("mid back", 10);
        mapping.put("lower back", 11);
        mapping.put("glutes", 12);
        mapping.put("hamstrings", 13);

        REGION_IDS = Collections.unmodifiableMap(mapping);
    }

    public static Map<String, Integer> GROUPING_IDS;
    static {
        Map<String, Integer> mapping = new HashMap<String, Integer>();
        // muscle group
        mapping.put("shoulders", 0);
        mapping.put("chest", 1);
        mapping.put("arms", 2);
        mapping.put("abs", 3);
        mapping.put("back", 4);
        mapping.put("legs", 5);

        GROUPING_IDS = Collections.unmodifiableMap(mapping);
    }

    public static Map<String, Integer> WORKOUT_TYPE_IDS;
    static {
        Map<String, Integer> mapping = new HashMap<String, Integer>();
        // workout type
        mapping.put("aerobic", 0);
        mapping.put("anaerobic", 1);
        mapping.put("yoga", 2);

        WORKOUT_TYPE_IDS = Collections.unmodifiableMap(mapping);
    }


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public  void onCreate(SQLiteDatabase db) {
        String CREATE_WORKOUTS_TABLE = "Create table " + TABLE_WORKOUTS + "(" +
                COL_ID + " integer not null auntoincrement, " +
                COL_NAME + " text primary key not null," +
                COL_DESC + " text," +

                COL_CHECK_PICTURES + " integer," +
                COL_CHECK_MAIN_REGIONS + " integer," +
                COL_CHECK_SUB_REGIONS + " integer)";
        db.execSQL(CREATE_WORKOUTS_TABLE);

        String CREATE_PICTURES_TABLE = "Create table " + TABLE_PICTURES + "(" +
                COL_WORKOUT_ID + " integer primary key references " + TABLE_WORKOUTS +"(" + COL_ID + ")," +
                COL_PIC + " blob not null," +
                COL_MAIN + " integer)";
        db.execSQL(CREATE_PICTURES_TABLE);

        String CREATE_WORKOUT_REGIONS_TABLE  = "Create table " + TABLE_WORKOUT_REGIONS + "(" +
                COL_WORKOUT_ID + " integer not null primary key references " + TABLE_WORKOUTS +"(" + COL_ID + ")," +
                COL_REGION_ID + " integer not null references " + TABLE_REGIONS +"(" + COL_ID + ")," +
                COL_MAIN + " integer)";
        db.execSQL(CREATE_WORKOUT_REGIONS_TABLE);

        String CREATE_REGIONS_TABLE = "Create table " + TABLE_REGIONS  + "(" +
                COL_ID + " integer primary key," +
                COL_NAME + " text)";
        db.execSQL(CREATE_REGIONS_TABLE);

        String CREATE_WORKOUT_TYPES_TABLE = "Create table " + TABLE_WORKOUT_TYPES + "(" +
                COL_ID + " integer primary key," +
                COL_NAME + " text)";
        db.execSQL(CREATE_WORKOUT_TYPES_TABLE);

        String CREATE_MUSCLE_GROUPS_TABLE = "Create table " + TABLE_MUSCLE_GROUPS + "(" +
                COL_ID + " integer primary key," +
                COL_NAME + " text)";
        db.execSQL(CREATE_MUSCLE_GROUPS_TABLE);

        /*
        Create static fields within specific tables
         */

        // Fill regions table
        for (String region : REGION_IDS.keySet()) {
            ContentValues regionVals = new ContentValues();
            regionVals.put(COL_ID, REGION_IDS.get(region));
            regionVals.put(COL_NAME, region);

            db.insert(TABLE_REGIONS, null, regionVals);
        }

        // Fill muscle groups table
        for (String grouping : GROUPING_IDS.keySet()) {
            ContentValues groupingVals = new ContentValues();
            groupingVals.put(COL_ID, GROUPING_IDS.get(grouping));
            groupingVals.put(COL_NAME, grouping);

            db.insert(TABLE_MUSCLE_GROUPS, null, groupingVals);
        }

        // Fill workout types table
        for (String workoutType : WORKOUT_TYPE_IDS.keySet()) {
            ContentValues workoutTypeVals = new ContentValues();
            workoutTypeVals.put(COL_ID, WORKOUT_TYPE_IDS.get(workoutType));
            workoutTypeVals.put(COL_NAME, workoutType);

            db.insert(TABLE_WORKOUT_TYPES, null, workoutTypeVals);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olderVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_WORKOUTS);
        db.execSQL("drop table if exists " + TABLE_PICTURES);
        db.execSQL("drop table if exists " + TABLE_WORKOUT_REGIONS);
        db.execSQL("drop table if exists " + TABLE_REGIONS);
        db.execSQL("drop table if exists " + TABLE_MUSCLE_GROUPS);
        db.execSQL("drop table if exists " + TABLE_WORKOUT_TYPES);
        onCreate(db);
    }

    /*
    Add Handlers
     */
    public void addWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase(); // open db for writting

        //  insert into primary table
        ContentValues workoutVals = new ContentValues();
        workoutVals.put(COL_NAME, workout.getName());
        workoutVals.put(COL_DESC, workout.getDescription());
        workoutVals.put(COL_WORKOUT_TYPE_ID, workout.getWorkoutTypeId());
        workoutVals.put(COL_MUSCLE_GROUP_ID, workout.getMuscleGroupId());
        workoutVals.put(COL_CHECK_PICTURES, workout.getCheckPictures());
        workoutVals.put(COL_CHECK_MAIN_REGIONS, workout.getCheckMainRegions());
        workoutVals.put(COL_CHECK_SUB_REGIONS, workout.getCheckSubRegions());

        db.insert(TABLE_WORKOUTS, null, workoutVals); // seems to auto assign id

        // find auto assigned id of workout
        int workoutId = 0;
        try {
            workoutId = findWorkoutIdByName(workout.getName());
        } catch (NullPointerException ne) {
            System.err.println("Caught in addWorkout: " + ne.getMessage());
            ne.printStackTrace();
            System.exit(0); // Error out
        }

        // insert pictures
        if (workout.getCheckPictures()) {
            int whetherMain = 1; // The first is the main
            for (Bitmap pic : workout.getPictures()) {
                // Convert the image into byte array
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                pic.compress(Bitmap.CompressFormat.PNG, 100, out);
                byte[] buffer = out.toByteArray();

                ContentValues picVals = new ContentValues();
                picVals.put(COL_ID, workoutId);
                picVals.put(COL_PIC, buffer);
                picVals.put(COL_MAIN, whetherMain);
                whetherMain = 0;

                db.insert(TABLE_PICTURES, null, picVals);
            }
        }


        // insert into workout regions table
        if(workout.getCheckMainRegions()) {
            // invdividually insert main regions
            for (String region : workout.getMainRegions()) {
                ContentValues mainRegionVals = new ContentValues();
                mainRegionVals.put(COL_ID, workoutId);
                mainRegionVals.put(COL_REGION_ID, REGION_IDS.get(region));
                mainRegionVals.put(COL_MAIN, 1); // is a main region

                db.insert(TABLE_WORKOUT_REGIONS, null, mainRegionVals);
            }

            for (String region : workout.getSubRegions()) {
                ContentValues subRegionVals = new ContentValues();
                subRegionVals.put(COL_ID, workoutId);
                subRegionVals.put(COL_REGION_ID, REGION_IDS.get(region));
                subRegionVals.put(COL_MAIN, 0); // is a sub region

                db.insert(TABLE_WORKOUT_REGIONS, null, subRegionVals);
            }
        }

        db.close();
    }

    // get / select handlers
    private int findWorkoutIdByName(String workoutName) throws NullPointerException {
        int id = 0; // will never return 0
        SQLiteDatabase db = this.getWritableDatabase(); // open db for writting
        String findIdQuery = "Select " + COL_ID + " from " + TABLE_WORKOUTS + " where " + COL_NAME + " = \"" + workoutName + "\"";
        Cursor cursor = db.rawQuery(findIdQuery, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            id = Integer.parseInt(cursor.getString(0)); // gets the id
        } else {
            db.close();
            throw new NullPointerException("findWorkoutIdByName found no id for " + workoutName);
        }

        db.close();
        return id;

    }

    public Workout findWorkout(String workoutName) {
        String query = "Select * from " + TABLE_WORKOUTS +  " where " +
                COL_NAME + " = \"" + workoutName + "\"";

        SQLiteDatabase db = this.getWritableDatabase(); // open db for reading

        // Get results from query
        // Can receive multiple rows!
        Cursor cursor = db.rawQuery(query, null);

        Workout workout = new Workout();
        // Move the cursor to the first row
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            workout.setId(Integer.parseInt(cursor.getString(0)));
            workout.setName(cursor.getString(1));
            workout.setDescription(cursor.getString(2));
            cursor.close();
        } else {
            workout = null;
        }

        db.close();
        return workout;
    }

    // Deletion handlers
    public boolean deleteWorkout(String workoutName) {
        boolean result = false;
        String query = "Select * from " + TABLE_WORKOUTS + " where " +
                COL_NAME + " = \"" + workoutName + "\"";

        SQLiteDatabase db = this.getWritableDatabase(); // Open db for changing

        // Get results query
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            String id = cursor.getString(0);
            // question marks correlate to spots in string array
            db.delete(TABLE_WORKOUTS, COL_ID + " = ?", new String[] {id});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}
