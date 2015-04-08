package com.home.moorre.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.home.moorre.myapplication.Logs.MainLogPage;
import com.home.moorre.myapplication.Workouts.AddWorkoutPage;
import com.home.moorre.myapplication.Workouts.ViewWorkouts;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add listener to buttons
        ((Button)findViewById(R.id.btAddWorkout)).setOnClickListener(new ButtonClickListener());
        ((Button)findViewById(R.id.btViewWorkout)).setOnClickListener(new ButtonClickListener());
        ((Button)findViewById(R.id.btViewLogs)).setOnClickListener(new ButtonClickListener());
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

    @Override
    public void onBackPressed(){
        DialogInterface.OnClickListener dialogClickListener2 = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder ab2 = new AlertDialog.Builder(this);
        ab2.setMessage("Exit TrackMyWorkout?").setPositiveButton("Exit", dialogClickListener2)
                .setNegativeButton("Cancel", dialogClickListener2).show();
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
