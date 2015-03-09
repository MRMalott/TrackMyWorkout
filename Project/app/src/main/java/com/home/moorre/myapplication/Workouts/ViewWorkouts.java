package com.home.moorre.myapplication.Workouts;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.home.moorre.myapplication.Classes.Workout;
import com.home.moorre.myapplication.MyDBHandler;
import com.home.moorre.myapplication.R;


public class ViewWorkouts extends ActionBarActivity {
    TextView idView;
    EditText workoutBox;
    EditText descriptionBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workouts);

        idView = (TextView) findViewById(R.id.tvId);
        workoutBox = (EditText) findViewById(R.id.tvName);
        descriptionBox = (EditText) findViewById(R.id.tvDesc);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_workouts, menu);
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

    public void lookupWorkout (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        Workout workout =
                dbHandler.findWorkout(workoutBox.getText().toString());

        if (workout != null) {
            idView.setText(String.valueOf(workout.getId()));
            descriptionBox.setText(String.valueOf(workout.getDescription()));
        } else {
            idView.setText("No Match Found");
        }
    }
}
