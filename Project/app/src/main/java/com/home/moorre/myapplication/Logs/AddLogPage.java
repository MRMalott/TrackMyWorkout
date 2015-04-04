package com.home.moorre.myapplication.Logs;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
<<<<<<< HEAD
import android.widget.Toast;

import com.home.moorre.myapplication.Classes.AnaerobicInput;
import com.home.moorre.myapplication.Classes.WorkoutLog;
import com.home.moorre.myapplication.Classes.Set;
import com.home.moorre.myapplication.DB.MyDBHandler;
import com.home.moorre.myapplication.R;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

=======

import com.home.moorre.myapplication.DB.MyDBHandler;
import com.home.moorre.myapplication.R;

>>>>>>> dev

public class AddLogPage extends ActionBarActivity {
    DatePicker addLogDate;
    Button addLogBt;
    Spinner workoutTypeDrop;
    String workoutTypeDropValue;
<<<<<<< HEAD
    EditText notesEt;
=======

>>>>>>> dev

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log_page);

        addLogDate = (DatePicker)findViewById(R.id.addLogDate);
<<<<<<< HEAD

        addLogBt = (Button)findViewById(R.id.addLogBt);
        addLogBt.setOnClickListener(new addListener());

        workoutTypeDrop = (Spinner)findViewById(R.id.logWorkoutType);
=======
        addLogBt = (Button)findViewById(R.id.addLogBt);

        workoutTypeDrop = (Spinner)findViewById(R.id.ddWorkoutType);
>>>>>>> dev
        ArrayAdapter<String> workoutDropAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MyDBHandler.WORKOUT_TYPE_IDS.keySet().toArray(new String[MyDBHandler.WORKOUT_TYPE_IDS.size()]));
        workoutDropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workoutTypeDrop.setAdapter(workoutDropAdapter);
        workoutTypeDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItem, int pos, long id)
            {
                Object item = parent.getItemAtPosition(pos);
                workoutTypeDropValue = item.toString();
                // Determines what attributes to display
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                workoutTypeDropValue = "anaerobic";
            }
        });

<<<<<<< HEAD
        notesEt = (EditText)findViewById(R.id.notesEV);

=======
>>>>>>> dev
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
<<<<<<< HEAD

    public class addListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (checkInputs()) {
                addNewLog();
            }
        }
    }

    public boolean checkInputs() {
        // Check workout date
        addLogDate = (DatePicker)findViewById(R.id.addLogDate);
        try {
            Date date = new Date(getDateFromDatePicker(addLogDate)); // from util to sql
        } catch(Exception e) {
            return false;
        }

        // Check inputs


        return true;
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

        return calendar.getTimeInMillis() - calendar.getTimeInMillis()%1000;
    }

    public void addNewLog() {
        WorkoutLog log = new WorkoutLog();
        MyDBHandler db = new MyDBHandler(this, null, null, 1);

        java.util.Date currentDate= new java.util.Date();

        log.setCreationDate(currentDate.getTime());
        log.setWorkoutDate(getDateFromDatePicker(addLogDate));

        System.out.println("-----\nsaving date\n " + new Date(log.getWorkoutDate()).toString() + " " + new Date(log.getWorkoutDate()).getTime());

        log.setIsAerobic(false);
        log.setLoggedWorkout(db.findFullWorkoutById(1));

        AnaerobicInput anaerobic = new AnaerobicInput();
        ArrayList<Set> sets = new ArrayList<Set>(); // three sets
        sets.add(new Set(4, 30));
        sets.add(new Set(3, 40));
        sets.add(new Set(2, 50));
        anaerobic.setSets(sets);
        log.setAnaerobic(anaerobic);
        log.setNotes(notesEt.getText().toString());

        try {
            db.addFullLog(log);
            Toast addLogSuccessToast = Toast.makeText(this, "Added Log", Toast.LENGTH_SHORT);
            addLogSuccessToast.show();
        }catch(Exception e) {
            e.printStackTrace();
            Toast addLogFailToast = Toast.makeText(this, "Couldn't add log", Toast.LENGTH_SHORT);
            addLogFailToast.show();
        } finally {
            db.close();
        }
    }
=======
>>>>>>> dev
}
