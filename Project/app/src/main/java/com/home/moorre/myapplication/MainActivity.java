package com.home.moorre.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
<<<<<<< HEAD
import android.widget.Button;

=======
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.home.moorre.myapplication.Classes.Workout;
import com.home.moorre.myapplication.DB.MyDBHandler;
import com.home.moorre.myapplication.DB.PopulateWorkouts;
>>>>>>> dev
import com.home.moorre.myapplication.Logs.MainLogPage;
import com.home.moorre.myapplication.Workouts.AddWorkoutPage;
import com.home.moorre.myapplication.Workouts.ViewWorkouts;


public class MainActivity extends ActionBarActivity {
    Button addWorkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD

        // Add listener to buttons
        ((Button)findViewById(R.id.btAddWorkout)).setOnClickListener(new ButtonClickListener());
        ((Button)findViewById(R.id.btViewWorkout)).setOnClickListener(new ButtonClickListener());
        ((Button)findViewById(R.id.btViewLogs)).setOnClickListener(new ButtonClickListener());
    }
=======
>>>>>>> dev

        // Add listener to buttons
        ((Button) findViewById(R.id.btAddWorkout)).setOnClickListener(new ButtonClickListener());
        ((Button) findViewById(R.id.btViewLogs)).setOnClickListener(new ButtonClickListener());
        ((Button) findViewById(R.id.btViewWorkout)).setOnClickListener(new ButtonClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    // Listener for buttons
    private class ButtonClickListener implements View.OnClickListener {
        public void onClick(View v) {
            Button b = (Button) (v);
            int buttonId = b.getId();

            switch (buttonId) {
                case (R.id.btAddWorkout):
                    Intent intent = new Intent(MainActivity.this, AddWorkoutPage.class);
                    startActivity(intent);
                    break;
                case (R.id.btViewWorkout):
                    intent = new Intent(MainActivity.this, ViewWorkouts.class);
                    startActivity(intent);
                    break;
                case (R.id.btViewLogs):
                    intent = new Intent(MainActivity.this, MainLogPage.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
