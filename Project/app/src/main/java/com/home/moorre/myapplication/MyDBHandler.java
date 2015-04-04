package com.home.moorre.myapplication;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Moorre on 2/5/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static  final String DATABASE_NAME = "healthDB.db";

    private static  final String TABLE_WORKOUTS = "workouts";
    // column names for workouts table
    private static  final String COL_ID = "_id";
    private static  final String COL_NAME = "_name";
    private static  final String COL_DESC = "_description";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public  void onCreate(SQLiteDatabase db) {
        String CREATE_WORKOUTS_TABLE = "Create table " + TABLE_WORKOUTS + "(" +
                COL_ID + " integer primary key, " + COL_NAME + " text," +
                COL_DESC + " integer)";
        db.execSQL(CREATE_WORKOUTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olderVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_WORKOUTS);
        onCreate(db);
    }

    // Add handlers
    public void addWorkout(Workout workout) {
        // ready the vars for column input
        ContentValues vals = new ContentValues();
        vals.put(COL_NAME, workout.getName());
        vals.put(COL_DESC, workout.getDescription());

        SQLiteDatabase db = this.getWritableDatabase(); // open db for writting

        db.insert(TABLE_WORKOUTS, null, vals);
        db.close();
    }

    // get / select handlers
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
