package com.home.moorre.myapplication.DB;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;

        import com.home.moorre.myapplication.Classes.AerobicInput;
        import com.home.moorre.myapplication.Classes.AnaerobicInput;
        import com.home.moorre.myapplication.Classes.Set;
        import com.home.moorre.myapplication.Classes.Workout;
        import com.home.moorre.myapplication.Classes.WorkoutLog;

        import java.io.ByteArrayInputStream;
        import java.io.ByteArrayOutputStream;
        import java.sql.Date;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.Random;


/**
 * Created by Moorre on 2/5/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 54;
    private static final String DATABASE_NAME = "healthDB.db";

    private static final String TABLE_WORKOUTS = "workouts";
    // column names for workouts table
    private static final String COL_ID = "_id";
    private static final String COL_NAME = "_name";
    private static final String COL_DESC = "_description";
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
    // col id
    private static final String COL_WORKOUT_NAME = "_workoutName";
    private static final String COL_WORKOUT_DATE = "_workoutDate";
    private static final String COL_CREATION_DATE = "_creationDate";
    private static final String COL_IS_AEROBIC = "_isAerobic";
    private static final String COL_NOTES = "_notes";

    private static final String TABLE_AEROBIC_INPUTS = "aerobicInputs";
    private static final String COL_LOG_ID = "_logId";
    private static final String COL_SECONDS = "_seconds";
    private static final String COL_FEET = "_feet";

    private static final String TABLE_SETS = "sets";
    // col logId
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
        mapping.put("strength", 1);
        mapping.put("yoga", 2);

        WORKOUT_TYPE_IDS = Collections.unmodifiableMap(mapping);
    }

    /**
     * Says whether a workout (string) will use sets and reps
     *
     * @param workoutType
     * @return true if its a workout type that uses sets
     */
    public static boolean doesWorkoutUseSets(int workoutType) {
        List<Integer> workouts = new ArrayList<Integer>();
        workouts.add(WORKOUT_TYPE_IDS.get("strength"));
        return workouts.contains(workoutType);
    }


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
                COL_WORKOUT_ID + " integer not null references " + TABLE_WORKOUTS + "(" + COL_ID + ")," +
                COL_PIC + " blob not null," +
                COL_MAIN + " integer not null)";
        db.execSQL(CREATE_PICTURES_TABLE);

        String CREATE_WORKOUT_REGIONS_TABLE = "Create table " + TABLE_WORKOUT_REGIONS + "(" +
                COL_WORKOUT_ID + " integer not null references " + TABLE_WORKOUTS + "(" + COL_ID + ")," +
                COL_REGION_ID + " integer," +
                COL_MAIN + " integer)";
        db.execSQL(CREATE_WORKOUT_REGIONS_TABLE);

        String CREATE_REGIONS_TABLE = "Create table " + TABLE_REGIONS + "(" +
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
                COL_WORKOUT_NAME + " text not null," +
                COL_WORKOUT_ID + " integer not null references " + TABLE_WORKOUTS + "(" + COL_ID + ")," +
                COL_WORKOUT_DATE + " numeric not null," +
                COL_CREATION_DATE + " numeric   not null," +
                COL_IS_AEROBIC + " integer not null," +
                COL_NOTES + " text)";
        db.execSQL(CREATE_LOGS_TABLE);

        String CREATE_AEROBIC_TABLE = "Create table " + TABLE_AEROBIC_INPUTS + "(" +
                COL_LOG_ID + " integer references " + TABLE_LOGS + "(" + COL_ID + ")," +
                COL_SECONDS + " integer," +
                COL_FEET + " integer)";
        db.execSQL(CREATE_AEROBIC_TABLE);

        String CREATE_SETS_TABLE = "Create table " + TABLE_SETS + "(" +
                COL_LOG_ID + " integer not null references " + TABLE_LOGS + "(" + COL_ID + ")," +
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
        db.execSQL("drop table if exists " + TABLE_LOGS);
        db.execSQL("drop table if exists " + TABLE_AEROBIC_INPUTS);
        db.execSQL("drop table if exists " + TABLE_SETS);
        onCreate(db);
    }

    /*
    Add Handlers
     */
    public void addWorkout(Workout workout) throws Exception {
        // assure name is not present
        try {
            findWorkoutIdByName(workout.getName());
        } catch (NullPointerException ne) {
            // cleared to go
        }

        //  insert into primary table
        try {
            addWorkoutTableInfo(workout);
        } catch (Exception e) {
            System.err.println("Couldn't add workout table data");
            throw e;
        }

        // find auto assigned id of workout
        try {
            workout.setId(findWorkoutIdByName(workout.getName()));
        } catch (Exception e) {
            System.err.println("Couldn't find auto assigned workout id");
            throw e;
        }

        // insert pictures
        if (workout.getCheckPictures()) {
            try {
                addWorkoutPictureInfo(workout);
            } catch (Exception e) {
                System.err.println("Couldn't add workout picture data");
                throw e;
            }
        }

        // insert into workout regions table
        try {
            addWorkoutRegionsInfo(workout);
        } catch (Exception e) {
            System.err.println("Couldn't add workout regions data");
            throw e;
        }
    }

    private void addWorkoutTableInfo(Workout workout) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase(); // open db for writing

        ContentValues workoutVals = new ContentValues();
        workoutVals.put(COL_NAME, workout.getName());
        workoutVals.put(COL_DESC, workout.getDescription());
        workoutVals.put(COL_WORKOUT_TYPE_ID, workout.getWorkoutTypeId());
        workoutVals.put(COL_MUSCLE_GROUP_ID, workout.getMuscleGroupId());
        workoutVals.put(COL_CHECK_PICTURES, workout.getCheckPictures());
        workoutVals.put(COL_CHECK_MAIN_REGIONS, workout.getCheckMainRegions());
        workoutVals.put(COL_CHECK_SUB_REGIONS, workout.getCheckSubRegions());

        db.insert(TABLE_WORKOUTS, null, workoutVals);

        db.close();
    }

    private void addWorkoutPictureInfo(Workout workout) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase(); // open db for writing

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
        System.out.println("picCount is ....... " + picCount);

        db.close();
    }

    private void addWorkoutRegionsInfo(Workout workout) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase(); // open db for writing

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

    public void addFullLog(WorkoutLog log) throws Exception {
        try {
            addLogInformation(log);
        } catch (Exception e) {
            System.out.println("!!!!\nFailed in addFullLog");
            throw e;
        }

        try {
            addInputs(log);
        } catch (Exception e) {
            System.out.println("!!!!\nFailed in addInputs");
            throw e;
        }
    }

    private void addLogInformation(WorkoutLog log) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase(); // open db for writting

        ContentValues logInfo = new ContentValues();
        logInfo.put(COL_WORKOUT_NAME, log.getLoggedWorkout().getName());
        logInfo.put(COL_WORKOUT_ID, log.getLoggedWorkout().getId());
        logInfo.put(COL_WORKOUT_DATE, log.getWorkoutDate());
        logInfo.put(COL_CREATION_DATE, log.getCreationDate());
        logInfo.put(COL_IS_AEROBIC, log.usesSets() ? 0 : 1);
        logInfo.put(COL_NOTES, log.getNotes());

        try {
            db.insert(TABLE_LOGS, null, logInfo);

            log.setId(findLastLogId()); // Find the auto incremented (largest) id
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        db.close();
    }

    private void addInputs(WorkoutLog log) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase(); // open db for writting

        // Determine which type the 'set' is
        if (!log.usesSets()) {
            ContentValues aerobicInputs = new ContentValues();
            aerobicInputs.put(COL_LOG_ID, log.getId());
            aerobicInputs.put(COL_FEET, log.getAerobic().getFeet());
            aerobicInputs.put(COL_SECONDS, log.getAerobic().getSeconds());
            db.insert(TABLE_AEROBIC_INPUTS, null, aerobicInputs);
        } else {
            List<Set> sets = log.getAnaerobic().getSets();
            for (int pos = 0; pos < sets.size(); pos++) {
                ContentValues setInput = new ContentValues();
                setInput.put(COL_LOG_ID, log.getId());
                setInput.put(COL_SET_NUMBER, pos + 1);
                setInput.put(COL_REPS, sets.get(pos).getReps());
                setInput.put(COL_WEIGHT, sets.get(pos).getWeight());
                try {
                    db.insert(TABLE_SETS, null, setInput);
                } catch (Exception e) {
                    System.out.println("Tried to add set + " + pos + " : " + sets.get(pos).getReps() + " " + sets.get(pos).getWeight());
                    throw e;
                }
            }
        }

        db.close();
    }

    /*
     get / select handlers
      */
    public List<Workout> getAllWorkouts() {
        List<Workout> workouts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase(); // open db for reading
        String query = "Select * from " + TABLE_WORKOUTS + " Order By " + COL_NAME;

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Workout workout = new Workout();
            workout.setId(Integer.parseInt(cursor.getString(0)));
            workout.setName(cursor.getString(1));
            workouts.add(workout);
        }
        cursor.close();
        db.close();
        return workouts;
    }

    public List<Workout> getAllWorkoutsByType(int type) {
        List<Workout> workouts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase(); // open db for reading
        String query = "Select * from " + TABLE_WORKOUTS + " where " + COL_WORKOUT_TYPE_ID +
                " = \"" + type + "\"" + " Order By " + COL_NAME;

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Workout workout = new Workout();
            workout.setId(Integer.parseInt(cursor.getString(0)));
            workout.setName(cursor.getString(1));
            workouts.add(workout);
        }
        cursor.close();
        db.close();
        return workouts;
    }

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

        cursor.close();
        db.close();
        return id;
    }

    public String findWorkoutNameById(int id) throws NullPointerException {
        SQLiteDatabase db = this.getReadableDatabase();
        String name;
        String findNameQuery = "Select " + COL_NAME + " from " + TABLE_WORKOUTS + " where " + COL_ID + " = " + id;
        Cursor cursor = db.rawQuery(findNameQuery, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            name = cursor.getString(0); // gets the id
        } else {
            cursor.close();
            db.close();
            throw new NullPointerException("found no id for " + id);
        }

        cursor.close();
        db.close();
        return name;
    }

    public Workout findFullWorkoutById(int id) {
        String name = findWorkoutNameById(id);
        return findFullWorkoutByName(name);
    }

    public Workout findFullWorkoutByName(String workoutName) {
        Workout workout = findWorkoutInfoByName(workoutName);

        if (workout == null) {
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
        String query = "Select * from " + TABLE_WORKOUTS + " where " +
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

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                //Bitmap bm = BitmapFactory.decodeByteArray(cursor.getBlob(1), 0, cursor.getBlob(1).length);
                //byte[] decodedString = Base64.decode(cursor.getBlob(1), Base64.DEFAULT);
                //InputStream inputStream = new ByteArrayInputStream((decodedString));
                //Bitmap bm = BitmapFactory.decodeStream(inputStream);
                ByteArrayInputStream imageStream = new ByteArrayInputStream(cursor.getBlob(1));
                Bitmap bm = BitmapFactory.decodeStream(imageStream);

                if (bm == null) {
                    System.err.println("Couldn't add picture");
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

        return pictures;
    }

    /**
     * Could be two separate calls but this would be inefficient
     *
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

        cursor.close();
        db.close();

        return id;
    }

    public List<WorkoutLog> findFullLogsByDate(long givenWorkoutDate) {
        List<WorkoutLog> logs = new ArrayList<WorkoutLog>();

        String query = "Select * from " + TABLE_LOGS + " where " +
                COL_WORKOUT_DATE + " = " + givenWorkoutDate;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Collect main data
        if (cursor.moveToFirst()) {
            do {
                WorkoutLog tempLog = new WorkoutLog();
                tempLog.setId(cursor.getInt(0));
                tempLog.setLoggedWorkout(new Workout(cursor.getString(1))); // fill workout later
                tempLog.setWorkoutDate(cursor.getLong(3));
                tempLog.setCreationDate(cursor.getLong(4));
                tempLog.setUsesSets(cursor.getInt(5) == 1 ? false : true);
                tempLog.setNotes(cursor.getString(6));

                // WOW I went through SO much trouble to find out that I wasn't adding the temp log to the list
                logs.add(tempLog);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        /* Collect individual logs' workout data
        for (WorkoutLog log : logs) {
            log.setLoggedWorkout(findFullWorkoutByName(log.getLoggedWorkout().getName()));
        }
        Dont think we need this */

        // Collect individual logs'
        for (WorkoutLog log : logs) {
            if (!log.usesSets()) {
                log.setAerobic(findAerobicInputByLogId(log.getId()));
            } else {
                log.setAnaerobic(findAnaerobicInputByLogId(log.getId()));
            }
        }

        return logs;
    }

    public AerobicInput findAerobicInputByLogId(int id) {
        AerobicInput lookupInput = new AerobicInput();

        String query = "Select * from " + TABLE_AEROBIC_INPUTS + " where " +
                COL_LOG_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            lookupInput.setLogId(id);
            lookupInput.setSeconds(cursor.getInt(1));
            lookupInput.setFeet(cursor.getInt(2));
        }

        cursor.close();
        return lookupInput;
    }

    public AnaerobicInput findAnaerobicInputByLogId(int id) {
        AnaerobicInput lookupInput = new AnaerobicInput();

        String query = "Select * from " + TABLE_SETS + " where " +
                COL_LOG_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Set> sets = new ArrayList<Set>();
        lookupInput.setLogId(id);

        // Get the different sets
        if (cursor.moveToFirst()) {
            do {
                sets.add(new Set(cursor.getInt(2), cursor.getDouble(3))); /* correct order? */
            } while (cursor.moveToNext());
        }
        lookupInput.setSets(sets);

        cursor.close();
        db.close();

        return lookupInput;
    }

    public List<WorkoutLog> findRecentWorkouts() {
        List<WorkoutLog> logs;

        long recentDate = findMostRecentLogDate();
        System.out.println("Most recent is " + (new Date(recentDate)).toString());
        if (recentDate == -1l) {
            logs = new ArrayList<WorkoutLog>();
        } else {
            logs = findFullLogsByDate(recentDate);
        }

        return logs;
    }

    public long findMostRecentLogDate() {
        SQLiteDatabase db = this.getReadableDatabase();
        long date = -1;
        String query = "Select " + COL_WORKOUT_DATE + " from " + TABLE_LOGS + " order by " + COL_WORKOUT_DATE + " desc limit 1";

        Cursor resultsCursor = db.rawQuery(query, null);
        if (resultsCursor.moveToFirst()) {
            date = resultsCursor.getLong(0);
        }

        resultsCursor.close();
        db.close();

        return date;
    }

    /*
     Deletion handlers
      */
    public boolean deleteWorkout(String workoutName) {
        boolean result = false;
        String query = "Select * from " + TABLE_WORKOUTS + " where " +
                COL_NAME + " = \"" + workoutName + "\"";

        SQLiteDatabase db = this.getWritableDatabase(); // Open db for changing
        Cursor cursor = db.rawQuery(query, null);

        /*
        ERROR: May not be deleting the full workout tables
         */
        if (cursor.moveToFirst()) {
            String id = cursor.getString(0);
            // question marks correlate to spots in string array
            db.delete(TABLE_WORKOUTS, COL_ID + " = ?", new String[]{id});
            cursor.close();
            result = true;
        }

        db.close();
        return result;
    }

    /**
     * will have to be sure to pass this value along in the layout
     *
     * @param id of log
     * @return whether it was successful or not
     */
    public boolean deleteLog(int id) {
        boolean result = false;
        String logQuery = "Select * from " + TABLE_LOGS + " where " +
                COL_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor logCursor = db.rawQuery(logQuery, null);

        if (logCursor.moveToFirst()) {
            // log exists now find delete the sub tables
            boolean usesSets = logCursor.getInt(5) == 0 ? false : true;

            if (usesSets) {
                String setQuery = "Select * from " + TABLE_SETS + " where " +
                        COL_LOG_ID + " = " + id;
                Cursor setCursor = db.rawQuery(setQuery, null);

                if (setCursor.moveToFirst()) {
                    db.delete(TABLE_SETS, COL_LOG_ID + " = ?", new String[]{String.valueOf(id)});
                }
                setCursor.close();
            } else {
                String aeQuery = "Select * from " + TABLE_AEROBIC_INPUTS + " where " +
                        COL_LOG_ID + " = " + id;
                Cursor aeCursor = db.rawQuery(aeQuery, null);
                if (aeCursor.moveToFirst()) {
                    db.delete(TABLE_AEROBIC_INPUTS, COL_LOG_ID + " = ?", new String[]{String.valueOf(id)});
                }
                aeCursor.close();
            }

            db.delete(TABLE_LOGS, COL_ID + " = ?", new String[]{String.valueOf(id)});
            logCursor.close();
            result = true;
        }

        db.close();
        return result;
    }

    public boolean simpleDeleteLog(int id) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(TABLE_LOGS, COL_ID + " = ?", new String[]{String.valueOf(id)});

            try {
                db.delete(TABLE_SETS, COL_LOG_ID + " = ?", new String[]{String.valueOf(id)});
            } catch (Exception setsErr) {
                setsErr.printStackTrace();
            }

            try {
                db.delete(TABLE_AEROBIC_INPUTS, COL_LOG_ID + " = ?", new String[]{String.valueOf(id)});
            } catch (Exception aerobicErr) {
                aerobicErr.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            db.close();
            return result;
        }
    }

    // Get random workout (returns workout name)
    public Workout randomWorkout() {
        List<Workout> workouts = new ArrayList<>();
        Workout workoutReturned;
        SQLiteDatabase db = this.getReadableDatabase(); // open db for reading
        String query = "Select * from " + TABLE_WORKOUTS;

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Workout workout = new Workout();
            workout.setId(Integer.parseInt(cursor.getString(0)));
            workout.setName(cursor.getString(1));
            workouts.add(workout);
        }

        Random r = new Random();
        workoutReturned = workouts.get(r.nextInt(workouts.size()));

        cursor.close();
        db.close();
        return workoutReturned;
    }

    //Check for any workouts in db
    public boolean isEmpty() {
        SQLiteDatabase db = this.getReadableDatabase(); // open db for reading
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_WORKOUTS, null);

        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    //Check if db contains workouts by workout type
    public boolean isTypeEmpty(int type) {
        SQLiteDatabase db = this.getReadableDatabase(); // open db for reading
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_WORKOUTS
                + " where " + COL_WORKOUT_TYPE_ID
                + " = \"" + type + "\"", null);

        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    /*
    Update queries
    myDB.update("titles", args, strFilter, new String[] { Integer.toString(ID) })
     */

    public boolean updateWorkout(Workout workout) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        int id = workout.getId();

        try {
            String filter = COL_ID + " = ?";

            ContentValues args = new ContentValues();
            args.put(COL_NAME, workout.getName());
            args.put(COL_DESC, workout.getDescription());
            args.put(COL_WORKOUT_TYPE_ID, workout.getWorkoutTypeId());
            args.put(COL_MUSCLE_GROUP_ID, workout.getMuscleGroupId());
            args.put(COL_CHECK_MAIN_REGIONS, workout.getCheckMainRegions());
            args.put(COL_CHECK_SUB_REGIONS, workout.getCheckSubRegions());
            args.put(COL_CHECK_PICTURES, workout.getCheckPictures());

            db.update(TABLE_WORKOUTS, args, filter, new String[]{Integer.toString(id)});
            db.close();

            updatePictures(workout);

            updateRegions(workout);

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen()) { // do this in case update fails
                db.close();
            }
        }


        return result;
    }

    public void updatePictures(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(TABLE_PICTURES, COL_WORKOUT_ID + " = ?", new String[]{String.valueOf(workout.getId())});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        // now add pics in
        try {
            addWorkoutPictureInfo(workout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRegions(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(TABLE_WORKOUT_REGIONS, COL_WORKOUT_ID + " = ?", new String[]{String.valueOf(workout.getId())});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        try {
            addWorkoutRegionsInfo(workout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateLog(WorkoutLog log) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        int id = log.getId();

        try {
            String filter = COL_ID + " = " + id;

            ContentValues args = new ContentValues();
            args.put(COL_CREATION_DATE, log.getCreationDate());
            args.put(COL_WORKOUT_DATE, log.getWorkoutDate());
            args.put(COL_NOTES, log.getNotes());
            args.put(COL_WORKOUT_ID, log.getWorkoutId());
            args.put(COL_WORKOUT_NAME, log.getWorkoutName());
            args.put(COL_IS_AEROBIC, log.usesSets() == false ? 1 : 0);

            db.update(TABLE_WORKOUTS, args, filter, new String[]{Integer.toString(id)});
            db.close();

            updateInputTables(log);

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }

        return result;
    }

    public void updateInputTables(WorkoutLog log) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(TABLE_AEROBIC_INPUTS, COL_LOG_ID + " = ?" + log.getId(), new String[] {String.valueOf(log.getId())});
        } catch(Exception e) {
            e.printStackTrace();
        }

        try {
            db.delete(TABLE_SETS, COL_LOG_ID + " = ?" + log.getId(), new String[] {String.valueOf(log.getId())});
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        try {
            addInputs(log);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
