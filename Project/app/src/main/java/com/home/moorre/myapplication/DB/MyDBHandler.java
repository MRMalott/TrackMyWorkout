package com.home.moorre.myapplication.DB;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.util.Base64;
        import android.util.Log;

        import com.home.moorre.myapplication.Classes.AnaerobicInput;
        import com.home.moorre.myapplication.Classes.Set;
        import com.home.moorre.myapplication.Classes.Workout;

        import java.io.ByteArrayInputStream;
        import java.io.ByteArrayOutputStream;
        import java.io.InputStream;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.HashMap;
        import java.util.Map;


/**
 * Created by Moorre on 2/5/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 34;
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

    private static final String TABLE_LOGS = "logs";
    // col id col workoutId
    private static final String COL_WORKOUT_DATE = "_workoutDate";
    private static final String COL_CREATION_DATE = "_creationDate";
    private static final String COL_IS_AEROBIC = "_isAerobic";
    private static final String COL_NOTES = "_notes";

    private static final String TABLE_AEROBIC_INPUTS = "aerobicInputs";
    private static final String COL_LOG_ID = "_logId";
    private static final String COL_SECONDS = "_seconds";
    private static final String COL_FEET = "_feet";

    private static final String TABLE_ANAEROBIC_INPUTS = "anaerobicInputs";
    // col logId
    private static final String COL_SET_ID = "_setId";

    private static final String TABLE_SETS = "sets";
    // col setsId
    private static final String COL_SET_NUMBER = "_setNumber";
    private static final String COL_REPS = "_reps";
    private static final String COL_WEIGHT = "_weight";

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
        /* -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
        Create workouts tables
        -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_- */
        String CREATE_WORKOUTS_TABLE = "Create table " + TABLE_WORKOUTS + "(" +
                COL_ID + " integer primary key autoincrement not null, " +
                COL_NAME + " text not null," +
                COL_DESC + " text," +
                COL_WORKOUT_TYPE_ID + " integer not null references " + TABLE_WORKOUT_TYPES + "(" + COL_ID + ")," +
                COL_MUSCLE_GROUP_ID + " integer not null references " + TABLE_MUSCLE_GROUPS + "(" + COL_ID + ")," +
                COL_CHECK_PICTURES + " integer not null," +
                COL_CHECK_MAIN_REGIONS + " integer not null," +
                COL_CHECK_SUB_REGIONS + " integer not null)";
        db.execSQL(CREATE_WORKOUTS_TABLE);

        String CREATE_PICTURES_TABLE = "Create table " + TABLE_PICTURES + "(" +
                COL_WORKOUT_ID + " integer not null references " + TABLE_WORKOUTS +"(" + COL_ID + ")," +
                COL_PIC + " blob not null," +
                COL_MAIN + " integer not null)";
        db.execSQL(CREATE_PICTURES_TABLE);

        String CREATE_WORKOUT_REGIONS_TABLE  = "Create table " + TABLE_WORKOUT_REGIONS + "(" +
                COL_WORKOUT_ID + " integer not null references " + TABLE_WORKOUTS +"(" + COL_ID + ")," +
                COL_REGION_ID + " integer not null," +
                COL_MAIN + " integer not null)";
        db.execSQL(CREATE_WORKOUT_REGIONS_TABLE);

        String CREATE_REGIONS_TABLE = "Create table " + TABLE_REGIONS  + "(" +
                COL_ID + " integer nul null primary key," +
                COL_NAME + " text not null)";
        db.execSQL(CREATE_REGIONS_TABLE);

        String CREATE_WORKOUT_TYPES_TABLE = "Create table " + TABLE_WORKOUT_TYPES + "(" +
                COL_ID + " integer not null primary key," +
                COL_NAME + " text not null)";
        db.execSQL(CREATE_WORKOUT_TYPES_TABLE);

        String CREATE_MUSCLE_GROUPS_TABLE = "Create table " + TABLE_MUSCLE_GROUPS + "(" +
                COL_ID + " integer not null primary key," +
                COL_NAME + " text not null)";
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

        //PopulateWorkouts.generateWorkouts(db);

        /* -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
        Create logs tables
        -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_- */

        String CREATE_LOGS_TABLE = "Create table " + TABLE_LOGS + "(" +
                COL_ID + " integer primary key autoincrement not null," +
                COL_WORKOUT_ID + " integer not null references " + TABLE_WORKOUTS + "(" + COL_ID + ")," +
                COL_WORKOUT_DATE + "integer not null," +
                COL_CREATION_DATE + " integer not null," +
                COL_IS_AEROBIC + " integer not null," +
                COL_NOTES + " text)";
        db.execSQL(CREATE_LOGS_TABLE);

        String CREATE_AEROBIC_TABLE = "Create table " + TABLE_AEROBIC_INPUTS + "(" +
                COL_LOG_ID + " integer references " + TABLE_LOGS + "(" + COL_ID + ")," +
                COL_SECONDS + " integer," +
                COL_FEET + " integer)";
        db.execSQL(CREATE_AEROBIC_TABLE);

        String CREATE_ANAEROBIC_TABLE = "Create table " + TABLE_ANAEROBIC_INPUTS + "(" +
                COL_LOG_ID + " integer references " + TABLE_LOGS + "(" + COL_ID + ")," +
                COL_SET_ID + " integer autoincrement not null," +
                "primary key (" + COL_LOG_ID + "," + COL_SET_ID +"))";
        db.execSQL(CREATE_ANAEROBIC_TABLE);

        String CREATE_SETS_TABLE = "Create tables " + TABLE_SETS + "(" +
                COL_SET_ID + " integer references " + TABLE_ANAEROBIC_INPUTS + "(" + COL_SET_ID + ")," +
                COL_SET_NUMBER + " integer not null," +
                COL_REPS + " integer not null," +
                COL_WEIGHT + " integer)";
        db.execSQL(CREATE_SETS_TABLE);

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
    public void addWorkout(Workout workout) throws Exception {
        // assure name is not present
        try {
            findWorkoutIdByName(workout.getName());
        } catch(NullPointerException ne) {
            throw new Exception("exists");
        }

        //  insert into primary table
         addWorkoutTableInfo(workout);

        // find auto assigned id of workout
        workout.setId(findWorkoutIdByName(workout.getName()));

        // insert pictures
        if (workout.getCheckPictures()) {
            addWorkoutPictureInfo(workout);
        }

        // insert into workout regions table
        addWorkoutRegionsInfo(workout);
    }

    private void addWorkoutTableInfo(Workout workout){
        SQLiteDatabase db = this.getWritableDatabase(); // open db for writting

        ContentValues workoutVals = new ContentValues();
        workoutVals.put(COL_NAME, workout.getName().toLowerCase());
        workoutVals.put(COL_DESC, workout.getDescription());
        workoutVals.put(COL_WORKOUT_TYPE_ID, workout.getWorkoutTypeId());
        workoutVals.put(COL_MUSCLE_GROUP_ID, workout.getMuscleGroupId());
        workoutVals.put(COL_CHECK_PICTURES, workout.getCheckPictures());
        workoutVals.put(COL_CHECK_MAIN_REGIONS, workout.getCheckMainRegions());
        workoutVals.put(COL_CHECK_SUB_REGIONS, workout.getCheckSubRegions());

        db.insert(TABLE_WORKOUTS, null, workoutVals);

        db.close();
    }

    private void addWorkoutPictureInfo(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase(); // open db for writting

        int picCount = 0;
        int whetherMain = 1; // The first is the main
        for (Bitmap pic : workout.getPictures()) {
            // Convert the image into byte array
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            pic.compress(Bitmap.CompressFormat.PNG, 100, out);
            byte[] buffer = out.toByteArray();

            ContentValues picVals = new ContentValues();
            picVals.put(COL_WORKOUT_ID, workout.getId());
            picVals.put(COL_PIC, buffer);
            picVals.put(COL_MAIN, whetherMain);
            whetherMain = 0;

            db.insert(TABLE_PICTURES, null, picVals);
            picCount++;
        }

        System.out.println("There were " + workout.getPictures().size() + " pictures\n" + picCount + " were added");
        db.close();
    }

    private void addWorkoutRegionsInfo(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase(); // open db for writting

        if (workout.getCheckMainRegions()) {
            for (String region : workout.getMainRegions()) {
                ContentValues mainRegionVals = new ContentValues();
                mainRegionVals.put(COL_WORKOUT_ID, workout.getId());
                mainRegionVals.put(COL_REGION_ID, REGION_IDS.get(region));
                mainRegionVals.put(COL_MAIN, 1); // is a main region

                db.insert(TABLE_WORKOUT_REGIONS, null, mainRegionVals);
            }
        }

        if (workout.getCheckSubRegions()) {
            for (String region : workout.getSubRegions()) {
                ContentValues subRegionVals = new ContentValues();
                subRegionVals.put(COL_WORKOUT_ID, workout.getId());
                subRegionVals.put(COL_REGION_ID, REGION_IDS.get(region));
                subRegionVals.put(COL_MAIN, 0); // is a sub region

                db.insert(TABLE_WORKOUT_REGIONS, null, subRegionVals);
            }
        }

        db.close();
    }

    public void addFullLog(com.home.moorre.myapplication.Classes.Log log) throws Exception {
        addLogInformation(log);

        addInputs(log);
    }

    private void addLogInformation(com.home.moorre.myapplication.Classes.Log log) {
        SQLiteDatabase db = this.getWritableDatabase(); // open db for writting

        ContentValues logInfo = new ContentValues();
        logInfo.put(COL_WORKOUT_ID, log.getLoggedWorkout().getId());
        logInfo.put(COL_WORKOUT_DATE, log.getWorkoutDate().getTime());
        logInfo.put(COL_CREATION_DATE, log.getCreationDate().getTime());
        logInfo.put(COL_NOTES, log.getNotes());

        log.setId(findLastLogId());

        db.insert(TABLE_LOGS, null, logInfo);

        db.close();
    }

    private void addInputs(com.home.moorre.myapplication.Classes.Log log) {
        SQLiteDatabase db = this.getWritableDatabase(); // open db for writting

        // Determine which type the 'set' is
        if(log.isAerobic()) {
            ContentValues aerobicInputs = new ContentValues();
            aerobicInputs.put(COL_LOG_ID, log.getId());
            aerobicInputs.put(COL_FEET, log.getAerobic().getFeet());
            aerobicInputs.put(COL_SECONDS, log.getAerobic().getSeconds());
            db.insert(TABLE_AEROBIC_INPUTS, null, aerobicInputs);
        } else {
            ArrayList<Set> sets = log.getAnaerobic().getSets();
            for(int pos = 0; pos < sets.size(); pos++) {
                ContentValues anaerobicInputs = new ContentValues();
                anaerobicInputs.put(COL_LOG_ID, log.getId());
                anaerobicInputs.put(COL_SET_NUMBER, pos + 1);
                anaerobicInputs.put(COL_REPS, sets.get(pos).getReps());
                anaerobicInputs.put(COL_WEIGHT, sets.get(pos).getWeight());
                db.insert(TABLE_ANAEROBIC_INPUTS, null, anaerobicInputs);
            }
        }

        db.close();
    }

    /*
     get / select handlers
      */
    public int findWorkoutIdByName(String workoutName) throws NullPointerException {
        int id = 0; // will never return 0
        SQLiteDatabase db = this.getReadableDatabase(); // open db for writting
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

    public Workout findFullWorkoutByName(String workoutName) {
        Workout workout = findWorkoutInfoByName(workoutName);

        if(workout ==  null) {
          return null;
        } // send back null if none found

        if (!workout.getCheckPictures()) {
            workout.setPictures(new ArrayList<Bitmap>());
        } else {
            workout.setPictures(findPicturesByWorkoutId(workout.getId()));
        }

        ArrayList<ArrayList<String>> regions = findRegionNamesByWorkoutId(workout.getId());

        workout.setMainRegions(new ArrayList<String>(regions.get(0)));

        workout.setSubRegions(new ArrayList<String>(regions.get(1)));

        workout.setCheckMainRegions(workout.getMainRegions().isEmpty() ? false : true);

        workout.setCheckSubRegions(workout.getSubRegions().isEmpty() ? false : true);

        return workout;
    }

    public Workout findWorkoutInfoByName(String workoutName) {
        String query = "Select * from " + TABLE_WORKOUTS +  " where " +
                COL_NAME + " = \"" + workoutName + "\"";

        SQLiteDatabase db = this.getReadableDatabase(); // open db for reading

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
            workout.setWorkoutTypeId(cursor.getInt(3));
            workout.setMuscleGroupId(cursor.getInt(4));
            workout.setCheckPictures(cursor.getString(5).equals("1") ? true : false);
            workout.setCheckMainRegions(cursor.getString(6).equals("1") ? true : false);
            workout.setCheckSubRegions(cursor.getString(7).equals("1") ? true : false);
            cursor.close();
        } else {
            workout = null;
        }

        db.close();
        return workout;
    }

    public String findWorkoutTypeNameById(int id) {
        String query = "Select " + COL_NAME + " from " + TABLE_WORKOUT_TYPES + " where " +
                COL_ID + " = \"" + id + "\"";

        SQLiteDatabase db = this.getReadableDatabase(); // open db for reading

        Cursor cursor = db.rawQuery(query, null);

        String name = null;
        // Move the cursor to the first row
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            name = cursor.getString(0);
            cursor.close();
        }

        db.close();
        return name;
    }

    public String findMuscleGroupNameById(int id) {
        String query = "Select " + COL_NAME + " from " + TABLE_MUSCLE_GROUPS + " where " +
                COL_ID + " = \"" + id + "\"";

        SQLiteDatabase db = this.getReadableDatabase(); // open db for reading

        Cursor cursor = db.rawQuery(query, null);

        String name = null;
        // Move the cursor to the first row
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            name = cursor.getString(0);
            cursor.close();
        }

        db.close();
        return name;
    }

    public ArrayList<Bitmap> findPicturesByWorkoutId(int id) {
        ArrayList<Bitmap> pictures = new ArrayList<Bitmap>();

        String query = "Select * from " + TABLE_PICTURES + " where " +
                COL_WORKOUT_ID + " = \"" + id + "\"";

        SQLiteDatabase db = this.getReadableDatabase(); // open db for reading

        Cursor cursor = db.rawQuery(query, null);

        System.out.println("findPicturesByWorkoutId\n" + id + " " + cursor.getCount());

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {
                //Bitmap bm = BitmapFactory.decodeByteArray(cursor.getBlob(1), 0, cursor.getBlob(1).length);
                //byte[] decodedString = Base64.decode(cursor.getBlob(1), Base64.DEFAULT);
                //InputStream inputStream = new ByteArrayInputStream((decodedString));
                //Bitmap bm = BitmapFactory.decodeStream(inputStream);
                ByteArrayInputStream imageStream = new ByteArrayInputStream(cursor.getBlob(1));
                Bitmap bm = BitmapFactory.decodeStream(imageStream);

                if (bm == null) {
                    System.out.println("Couldn't add picture");
                } else {
                    // Add to front if main image
                    if (cursor.getString(2).equals("1")) {
                        pictures.add(0, bm);
                    } else {
                        pictures.add(bm);
                    }
                }

                cursor.moveToNext();
            }

            cursor.close();
        }

        db.close();
        System.out.println("Found " + pictures.size() + " pictures");
        return pictures;
    }

    /**
     * Could be two separate calls but this would be inefficient
     * @return Array with two lists of strings, one for main and another for sub regions
     */
    public ArrayList<ArrayList<String>> findRegionNamesByWorkoutId(int workoutId) {
        ArrayList<ArrayList<String>> regions = new ArrayList<ArrayList<String>>(2);
        regions.add(new ArrayList<String>());
        regions.add(new ArrayList<String>());


        String query = "Select " + TABLE_REGIONS + "." + COL_NAME + "," + TABLE_WORKOUT_REGIONS + "." + COL_MAIN + " from " +
                TABLE_WORKOUT_REGIONS + " inner join " + TABLE_REGIONS + " on " +
                TABLE_WORKOUT_REGIONS + "." + COL_REGION_ID + " = " + TABLE_REGIONS + "." + COL_ID +
                " where " + TABLE_WORKOUT_REGIONS + "." + COL_WORKOUT_ID + " = " + "\"" + workoutId + "\"";

        SQLiteDatabase db = this.getReadableDatabase(); // Open db for changing

        // Get results query
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            // Go through results
            while (!cursor.isAfterLast()) {
                // Each cursor point is a found region for main or sub
                String name = cursor.getString(0);
                boolean main = cursor.getString(1).equals("1") ? true : false;

                // Add to appropriate array
                if (main) {
                    regions.get(0).add(name);
                } else {
                    regions.get(1).add(name);
                }

                cursor.moveToNext();
            }

            cursor.close();
        }

        db.close();
        return regions;
    }

    private int findLastLogId() {
        SQLiteDatabase db = this.getWritableDatabase(); // open db for writting
        Cursor cursor = db.query(TABLE_LOGS, new String[]{COL_ID}, null, null, null, null, null);
        cursor.moveToLast();
        int id = cursor.getInt(0);
        System.out.println("Found last id to be " + id);

        cursor.close();
        db.close();

        return id;
    }

    /*
     Deletion handlers
      */
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
