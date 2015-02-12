package com.home.moorre.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class AddWorkoutPage extends ActionBarActivity {
    TextView idView;
    EditText workoutBox;
    EditText descriptionBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_page);

        idView = (TextView) findViewById(R.id.tvId);
        workoutBox = (EditText) findViewById(R.id.tvName);
        descriptionBox = (EditText) findViewById(R.id.tvDesc);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_workout_page, menu);
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

    /*
    DBHandler
     */

    public void newWorkout (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String desc = descriptionBox.getText().toString(), name = workoutBox.getText().toString();

        // Check that information is right here
        if (desc.isEmpty() || name.isEmpty()) {
            return;
        }

        dbHandler.addWorkout(new Workout(name, desc));
        workoutBox.setText("");
        descriptionBox.setText("");
    }
}
