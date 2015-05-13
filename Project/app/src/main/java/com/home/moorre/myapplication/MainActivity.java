package com.home.moorre.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.home.moorre.myapplication.Classes.Workout;
import com.home.moorre.myapplication.DB.MyDBHandler;
import com.home.moorre.myapplication.DB.PopulateWorkouts;
import com.home.moorre.myapplication.Logs.MainLogPage;
import com.home.moorre.myapplication.Workouts.AddWorkoutPage;
import com.home.moorre.myapplication.Workouts.FilterWorkouts;
import com.home.moorre.myapplication.Workouts.ViewSingleWorkout;

public class MainActivity extends ActionBarActivity {
    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Custom font
        String fontPath = "fonts/SFEspressoShack.otf";
        Button textBt1 = (Button) findViewById(R.id.btAddWorkout);
        Button textBt2 = (Button) findViewById(R.id.btViewWorkout);
        Button textBt3 = (Button) findViewById(R.id.btViewLogs);
        Button textBt4 = (Button) findViewById(R.id.btRandomWorkout);
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        textBt1.setTypeface(tf);
        textBt2.setTypeface(tf);
        textBt3.setTypeface(tf);
        textBt4.setTypeface(tf);

        //If database empty, ask to load pre-made workouts
        MyDBHandler db = new MyDBHandler(this, null, null, 1);
        if(db.isEmpty()) {
            db.close();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Would you like to load default workouts?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    PopulateWorkouts pop = new PopulateWorkouts(getApplicationContext());
                    pop.generateWorkouts();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else db.close();

        //Add listener to buttons
        findViewById(R.id.btAddWorkout).setOnClickListener(new ButtonClickListener());
        findViewById(R.id.btViewWorkout).setOnClickListener(new ButtonClickListener());
        findViewById(R.id.btViewLogs).setOnClickListener(new ButtonClickListener());
        findViewById(R.id.btRandomWorkout).setOnClickListener(new ButtonClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent homeIntent = new Intent(this, MainActivity.class);
            startActivity(homeIntent);
            return true;
        }
        if (id == R.id.action_workouts) {
            Intent workoutIntent = new Intent(this, FilterWorkouts.class);
            startActivity(workoutIntent);
            return true;
        }
        if (id == R.id.action_logs) {
            Intent logIntent = new Intent(this, MainLogPage.class);
            startActivity(logIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Listener for buttons
    private class ButtonClickListener implements View.OnClickListener {
        public void onClick(View v) {
            MyDBHandler db = new MyDBHandler(getApplicationContext(), null, null, 1);
            Button b = (Button) (v);
            int buttonId = b.getId();

            switch (buttonId) {
                case (R.id.btAddWorkout):
                    Intent intent = new Intent(MainActivity.this, AddWorkoutPage.class);
                    startActivity(intent);
                    break;
                case (R.id.btViewWorkout):
                    if(db.isEmpty()) {
                        Toast.makeText(MainActivity.this, "No workouts found", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        intent = new Intent(MainActivity.this, FilterWorkouts.class);
                        startActivity(intent);
                    }
                    break;
                case (R.id.btViewLogs):
                    intent = new Intent(MainActivity.this, MainLogPage.class);
                    startActivity(intent);
                    break;
                case (R.id.btRandomWorkout):
                    try {
                        Workout workout = db.randomWorkout();
                        intent = new Intent(MainActivity.this, ViewSingleWorkout.class);
                        intent.putExtra("key1", workout.getName());
                        startActivity(intent);
                    }
                    catch(Exception e){
                        Toast.makeText(MainActivity.this, "No workouts found", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Press again to close the app.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }
}
