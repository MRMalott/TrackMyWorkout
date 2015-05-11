package com.home.moorre.myapplication.Logs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.home.moorre.myapplication.Classes.AerobicInput;
import com.home.moorre.myapplication.Classes.AnaerobicInput;
import com.home.moorre.myapplication.Classes.Workout;
import com.home.moorre.myapplication.Classes.WorkoutLog;
import com.home.moorre.myapplication.Classes.Set;
import com.home.moorre.myapplication.DB.MyDBHandler;
import com.home.moorre.myapplication.R;
import com.home.moorre.myapplication.Workouts.ViewWorkouts;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddLogPage extends ActionBarActivity {
    DatePicker addLogDate;
    Button addLogBt;
    Button selectWorkoutBt;
    EditText notesEt;
    LinearLayout inputLayoutArea;
    List<LinearLayout> inputLayouts;
    TextView logWorkoutNameTv;
    boolean ready;
    Workout currentWorkout; // will only be set when ready

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log_page);

        addLogDate = (DatePicker)findViewById(R.id.addLogDate);

        addLogBt = (Button)findViewById(R.id.addLogBt);
        addLogBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ready) {
                    addNewLog();
                } else {
                    Toast.makeText(AddLogPage.this, "Select a workout first", Toast.LENGTH_SHORT).show();
                }
            }
        } );

        selectWorkoutBt = (Button)findViewById(R.id.selectWorkoutBt);
        selectWorkoutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToViewWorkoutPage();
            }
        });

        notesEt = (EditText)findViewById(R.id.notesEV);

        logWorkoutNameTv = (TextView)findViewById(R.id.selectWorkoutNameTv);

        // Inputs area
        inputLayoutArea = (LinearLayout)findViewById(R.id.inputsLV);
        inputLayouts = new ArrayList<LinearLayout>();
        Bundle params = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras(); // retrieve intent parameters
        if (params.getBoolean("logLookup", false)) {
            // find the workouts info
            MyDBHandler db = new MyDBHandler(this, null, null, 1);
            currentWorkout = db.findFullWorkoutByName(params.getString("selectedWorkout"));

            // set the inputs area
            if(MyDBHandler.doesWorkoutUseSets(currentWorkout.getWorkoutTypeId())) {
                makeInputsSets();
            } else {
                makeInputsAerobic();
            }

            // show what the workout is
            logWorkoutNameTv.setText(params.getString("selectedWorkout"));

            ready = true; // can now add the log

            db.close();
        } else {
            ready = false; // must select workout
        }

        System.out.println("There are " + inputLayouts.size() + " layouts ");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_log_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Get the date from the date picker widget
     * @param datePicker
     * @return a java.util.Date
     */
    public static long getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);

        return calendar.getTimeInMillis() - calendar.getTimeInMillis() % 1000;
    }

    public void goToViewWorkoutPage() {
        Intent intent = new Intent(AddLogPage.this, ViewWorkouts.class);
        intent.putExtra("logLookup", true); // says this is from log page
        intent.putExtra("filterkey", "all");
        startActivity(intent);
    }

    public void addNewLog() {
        WorkoutLog log = new WorkoutLog();
        MyDBHandler db = new MyDBHandler(this, null, null, 1);

        java.util.Date currentDate = new java.util.Date();

        log.setCreationDate(currentDate.getTime());
        log.setWorkoutDate(getDateFromDatePicker(addLogDate));

        log.setLoggedWorkout(db.findFullWorkoutById(1));

        // uses the saved workouts
        log.setUsesSets(MyDBHandler.doesWorkoutUseSets(currentWorkout.getWorkoutTypeId()));

        if(log.usesSets()) {
            log.setAnaerobic(getPageSets());
        } else {
            log.setAerobic(getAerobicInputs());
        }

        log.setNotes(notesEt.getText().toString());

        try {
            db.addFullLog(log);
            Toast.makeText(this, "Added Log", Toast.LENGTH_SHORT).show();
            finish();
        }catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Couldn't add log", Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }

    public void makeInputsAerobic() {
        // Create set layout
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inputArea = (LinearLayout)inflater.inflate(R.layout.aerobic_view, null, false);

        // add layout to list cleared and view
        inputLayoutArea.removeAllViewsInLayout();
        inputLayoutArea.addView(inputArea);
        inputLayouts.clear();
        inputLayouts.add(inputArea);
    }

    public void makeInputsSets() {
        // Create set layout
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inputArea = (LinearLayout)inflater.inflate(R.layout.set_layout_view, null, false);

        // add layout to cleared list and view
        inputLayoutArea.removeAllViewsInLayout();
        inputLayoutArea.addView(inputArea);
        inputLayouts.clear();
    }

    public AerobicInput getAerobicInputs() {
        LinearLayout aerobicInputArea = inputLayouts.get(0);

        int minutes = getIntegerValue(((EditText) aerobicInputArea.findViewById(R.id.workoutTimeMinutesEv)).getText().toString());
        int seconds = getIntegerValue(((EditText)aerobicInputArea.findViewById(R.id.workoutTimeSecondsEv)).getText().toString());
        int totalSeconds = (minutes * 60) + seconds;

        int miles = getIntegerValue(((EditText)aerobicInputArea.findViewById(R.id.workoutDistanceMilesEv)).getText().toString());
        int yards = getIntegerValue(((EditText) aerobicInputArea.findViewById(R.id.workoutDistanceYardsEv)).getText().toString());
        int totalFeet = (miles * 5280) + (yards * 3);

        AerobicInput aerobicInput = new AerobicInput();
        aerobicInput.setSeconds(totalSeconds);
        aerobicInput.setFeet(totalFeet);

        return aerobicInput;
    }

    public int getIntegerValue(String input) {
        if (input.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(input);
        }
    }

    public AnaerobicInput getPageSets() {
        List<Set> sets = new ArrayList<Set>();

        for (LinearLayout setLayout : inputLayouts) {
            int setReps = getIntegerValue(((EditText)setLayout.findViewById(R.id.repAmountEv)).getText().toString());
            double setWeight = getIntegerValue(((EditText)setLayout.findViewById(R.id.repWeightEv)).getText().toString());

            sets.add(new Set(setReps, setWeight));
        }

        return new AnaerobicInput(sets);
    }

    /**
     * Called by xml set button
     * Removes the last set
     */
    public void removeSet(View v) {
        inputLayouts.remove(inputLayouts.size()-1); // remove last
        inputLayoutArea.removeViewAt(inputLayouts.size() - 1);
    }

    /**
     * Called by xml set button
     * Adds a set layout to the end
     */
    public void addSet(View v) {
        // Create set layout
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout newSet = (LinearLayout)inflater.inflate(R.layout.set_view, null, false);

        // add layout to list and view
        inputLayoutArea.addView(newSet);
        inputLayouts.add(newSet);
    }
}
