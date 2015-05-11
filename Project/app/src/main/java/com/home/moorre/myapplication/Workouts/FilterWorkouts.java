package com.home.moorre.myapplication.Workouts;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.home.moorre.myapplication.Classes.Workout;
import com.home.moorre.myapplication.DB.MyDBHandler;
import com.home.moorre.myapplication.MainActivity;
import com.home.moorre.myapplication.R;

public class FilterWorkouts extends ActionBarActivity implements View.OnClickListener{
    Button aerobicBt, anaerobicBt, yogaBt, allBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_filter);

        //Buttons
        aerobicBt = (Button) findViewById(R.id.filterAerBt);
        anaerobicBt = (Button) findViewById(R.id.filterAnBt);
        yogaBt = (Button) findViewById(R.id.filterYogaBt);
        allBt = (Button) findViewById(R.id.filterAllBt);

        //Custom font
        String fontPath = "fonts/SFEspressoShack.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        aerobicBt.setTypeface(tf);
        anaerobicBt.setTypeface(tf);
        yogaBt.setTypeface(tf);
        allBt.setTypeface(tf);

        //Button listeners
        anaerobicBt.setOnClickListener(this);
        aerobicBt.setOnClickListener(this);
        yogaBt.setOnClickListener(this);
        allBt.setOnClickListener(this);
    }

    public void onClick (View v) {
        MyDBHandler db = new MyDBHandler(getApplicationContext(), null, null, 1);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.filterAerBt:
                if(db.isTypeEmpty(0)) {
                    Toast.makeText(FilterWorkouts.this, "No aerobic workouts found",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    intent.putExtra("filterkey", "aerobic");
                    intent.setClass(FilterWorkouts.this, ViewWorkouts.class);
                    startActivity(intent);
                }
                break;
            case R.id.filterAnBt:
                if(db.isTypeEmpty(1)) {
                    Toast.makeText(FilterWorkouts.this, "No strength workouts found",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    intent.putExtra("filterkey", "strength");
                    intent.setClass(FilterWorkouts.this, ViewWorkouts.class);
                    startActivity(intent);
                }
                break;
            case R.id.filterYogaBt:
                if(db.isTypeEmpty(2)) {
                    Toast.makeText(FilterWorkouts.this, "No yoga workouts found",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    intent.putExtra("filterkey", "yoga");
                    intent.setClass(FilterWorkouts.this, ViewWorkouts.class);
                    startActivity(intent);
                }
                break;
            case R.id.filterAllBt:
                intent.putExtra("filterkey", "all");
                intent.setClass(FilterWorkouts.this, ViewWorkouts.class);
                startActivity(intent);
                break;
        }
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

    }
