package com.home.moorre.myapplication.Workouts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.moorre.myapplication.Classes.Set;
import com.home.moorre.myapplication.Classes.Workout;
import com.home.moorre.myapplication.Classes.WorkoutLog;
import com.home.moorre.myapplication.DB.MyDBHandler;
import com.home.moorre.myapplication.Logs.AddLogPage;
import com.home.moorre.myapplication.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


public class ViewWorkouts extends ActionBarActivity {
    EditText lookupTv;
    TextView errorTv;
    Button lookupBt;
    List<Workout> foundWorkouts;
    ListView foundWorkoutsLv;
    FoundWorkoutsAdapter foundWorkoutsAdapter;
    boolean fromLogPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workouts);

        lookupTv = (EditText) findViewById(R.id.lookupTv);
        errorTv = (TextView) findViewById(R.id.errorTv);
        lookupBt = (Button) findViewById(R.id.lookupBt);
        lookupBt.setOnClickListener(new LookupListener());

        Bundle params = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras(); // retrieve intent parameters
        fromLogPage = params.getBoolean("logLookup", false); // set the variable
        System.out.println("came from log area: " + fromLogPage);

        foundWorkoutsLv = (ListView) findViewById(R.id.foundWorkoutsLv);
        foundWorkoutsAdapter = new FoundWorkoutsAdapter();
        foundWorkouts = new ArrayList<Workout>();
        foundWorkoutsLv.setAdapter(foundWorkoutsAdapter);
        foundWorkoutsLv.setOnItemClickListener(new itemClickListener());
    }

    public class itemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println("clicked item, log flag is " + fromLogPage);
            if (fromLogPage) {
                Intent intent = new Intent(ViewWorkouts.this, AddLogPage.class);
                intent.putExtra("logLookup", true);
                intent.putExtra("selectedWorkout", foundWorkouts.get(position).getName());
                startActivity(intent); // go back to logs
            } else {
                // do nothing
            }
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



    private class LookupListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button lookupBt = (Button)view;

            try {
                fillWorkouts();
                Toast.makeText(ViewWorkouts.this, "Found workouts", Toast.LENGTH_SHORT).show();
            } catch(Exception e) {
                e.printStackTrace();
                Toast.makeText(ViewWorkouts.this, "Failed to lookup workouts", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void fillWorkouts() throws Exception {
        MyDBHandler db = getDb();

        // Ignore if there is no input
        if (lookupTv.getText().length() == 0) {
            return;
        }

        try {
            Workout foundWorkout = db.findFullWorkoutByName(cleanName(lookupTv.getText().toString()));

            // Ignore if there are no workouts found
            if (foundWorkout == null) {
                return;
            }

            List<Workout> queryResults = new ArrayList<Workout>();
            queryResults.add(foundWorkout);

            foundWorkouts.clear();
            foundWorkouts.addAll(queryResults);
        } catch(Exception e) {
            throw e;
        }

        foundWorkoutsAdapter.notifyDataSetChanged();

        db.close();
    }

    class FoundWorkoutsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return foundWorkouts.size();
        }

        @Override
        public Object getItem(int position) {
            return foundWorkouts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Workout workout = foundWorkouts.get(position);
            MyDBHandler dbHandler = getDb();

            // If null then create new view
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) ViewWorkouts.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.workout_view, parent, false);
            }

            // Set new view's fields
            // name
            TextView nameTv = (TextView)convertView.findViewById(R.id.displayWorkoutNameTv);
            nameTv.setText(workout.getName());

            // description
            TextView descTv = (TextView)convertView.findViewById(R.id.displayWorkoutDescTv);
            descTv.setText(workout.getDescription());

            // workout type
            TextView workoutTypeTv = (TextView)convertView.findViewById(R.id.displayWorkoutDescTv);
            workoutTypeTv.setText(dbHandler.findWorkoutTypeNameById(workout.getWorkoutTypeId()));

            // muscle group
            TextView muscleGroupTv = (TextView)convertView.findViewById(R.id.displayMuscleGroupTv);
            muscleGroupTv.setText(dbHandler.findMuscleGroupNameById(workout.getMuscleGroupId()));

            dbHandler.close();

            // pictures
            LinearLayout horLinearImageView = (LinearLayout) convertView.findViewById(R.id.displayPictureLl);

            // now add images
            if (workout.getCheckPictures()) {
                for (Bitmap img : workout.getPictures()) {
                    LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(100, 100);// width, height
                    ImageView imgBox = new ImageView(convertView.getContext());
                    imgBox.setLayoutParams(imgParams);
                    imgBox.setImageBitmap(img);
                    horLinearImageView.addView(imgBox);
                }
            } else {
                horLinearImageView = new LinearLayout(convertView.getContext());
                LinearLayout.LayoutParams horLinearImageViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                horLinearImageView.setLayoutParams(horLinearImageViewParams);
                horLinearImageView.setOrientation(LinearLayout.HORIZONTAL);
            }

            // main regions
            if (workout.getCheckMainRegions()) {
                String mainRegionsText = "";
                int setNumber = 1;
                for (String region : workout.getMainRegions()) {
                    mainRegionsText += region + " ";
                }

                TextView mainRegionsTv = (TextView)convertView.findViewById(R.id.displayMainRegionsTv);
                mainRegionsTv.setText(mainRegionsText);
            } else {
                TextView mainRegionsTv = (TextView)convertView.findViewById(R.id.displayMainRegionsTv);
                mainRegionsTv.setText("");
            }

            // sub regions
            if (workout.getCheckSubRegions()) {
                String subRegionsText = "";
                for (String region : workout.getSubRegions()) {
                    subRegionsText += region + " ";
                }

                TextView subRegionsTv = (TextView)convertView.findViewById(R.id.displaySubRegionsTv);
                subRegionsTv.setText(subRegionsText);
            } else {
                TextView subRegionsTv = (TextView)convertView.findViewById(R.id.displaySubRegionsTv);
                subRegionsTv.setText("");
            }

            return convertView;
        }
    }


    /*
        Helpers
     */

    public String cleanName(String name) {
        return name.trim().toLowerCase();
    }

    public MyDBHandler getDb() {
        return new MyDBHandler(this, null, null, 1);
    }
}
