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

import com.home.moorre.myapplication.DB.MyDBHandler;
import com.home.moorre.myapplication.R;


public class AddLogPage extends ActionBarActivity {
    DatePicker addLogDate;
    Button addLogBt;
    Spinner workoutTypeDrop;
    String workoutTypeDropValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log_page);

        addLogDate = (DatePicker)findViewById(R.id.addLogDate);
        addLogBt = (Button)findViewById(R.id.addLogBt);

        workoutTypeDrop = (Spinner)findViewById(R.id.ddWorkoutType);
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
}
