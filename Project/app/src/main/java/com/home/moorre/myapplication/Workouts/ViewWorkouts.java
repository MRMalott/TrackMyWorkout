package com.home.moorre.myapplication.Workouts;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.home.moorre.myapplication.Classes.Workout;
import com.home.moorre.myapplication.DB.MyDBHandler;
import com.home.moorre.myapplication.R;

import java.util.ArrayList;


public class ViewWorkouts extends ActionBarActivity {
    EditText lookupTv;
    TextView errorTv;
    LinearLayout mainHolderView;
    Button lookupBt;
    ArrayList<LinearLayout> visibleWorkouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workouts);

        lookupTv = (EditText) findViewById(R.id.lookupTv);
        errorTv = (TextView) findViewById(R.id.errorTv);
        mainHolderView = (LinearLayout) findViewById(R.id.mainHolder);
        lookupBt = (Button) findViewById(R.id.lookupBt);
        lookupBt.setOnClickListener(new LookupListener());
        visibleWorkouts = new ArrayList<LinearLayout>();
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
            MyDBHandler dbHandler = getDb();

            Button lookupBt = (Button)view;

            if (lookupTv.getText().length() == 0) {
              return;
            }

            Workout workout =
                    dbHandler.findFullWorkoutByName(lookupTv.getText().toString());

            if (workout != null) {
                addWorkoutDisplay(workout);
                errorTv.setText(null);
            } else {
                errorTv.setText("No Match Found");
            }
        }
    }

    /*
        DB
     */

    public MyDBHandler getDb() {
        return new MyDBHandler(this, null, null, 1);
    }

    /**
     * Gets expandable layout given a workout
     * @param workout
     */
    public void addWorkoutDisplay(Workout workout) {
        MyDBHandler dbHandler = getDb(); // for name lookups

        // id
        TextView idTv = new TextView(this);
        idTv.setText(String.valueOf(workout.getId()));
        mainHolderView.addView(idTv);

        // name
        TextView nameTitleTv = new TextView(this);
        nameTitleTv.setText("Name");
        mainHolderView.addView(nameTitleTv);

        TextView nameTv = new TextView(this);
        nameTv.setText(workout.getName());
        mainHolderView.addView(nameTv);

        // description
        TextView descTitleTv = new TextView(this);
        descTitleTv.setText("Description");
        mainHolderView.addView(descTitleTv);

        TextView descTv = new TextView(this);
        descTv.setText(workout.getDescription());
        mainHolderView.addView(descTv);

        // workout type
        TextView workoutTypeTitleTv = new TextView(this);
        workoutTypeTitleTv.setText("Workout Type");
        mainHolderView.addView(workoutTypeTitleTv);

        TextView workoutTypeTv = new TextView(this);
        workoutTypeTv.setText(dbHandler.findWorkoutTypeNameById(workout.getWorkoutTypeId()));
        mainHolderView.addView(workoutTypeTv);

        // muscle group
        TextView muscleGroupTitleTv = new TextView(this);
        muscleGroupTitleTv.setText("Muscle Group");
        mainHolderView.addView(muscleGroupTitleTv);

        TextView muscleGroupTv = new TextView(this);
        muscleGroupTv.setText(dbHandler.findMuscleGroupNameById(workout.getMuscleGroupId()));
        mainHolderView.addView(muscleGroupTv);

        // pictures
        TextView picTv = new TextView(this);
        picTv.setText("Pictures");
        mainHolderView.addView(picTv);

        HorizontalScrollView imageScrollView = new HorizontalScrollView(this);
        HorizontalScrollView.LayoutParams imgFileParams = new HorizontalScrollView.LayoutParams(HorizontalScrollView.LayoutParams.MATCH_PARENT,
                HorizontalScrollView.LayoutParams.WRAP_CONTENT);
        imageScrollView.setLayoutParams(imgFileParams);

        LinearLayout horLinearImageView = new LinearLayout(this);
        LinearLayout.LayoutParams horLinearImageViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        horLinearImageView.setLayoutParams(horLinearImageViewParams);
        horLinearImageView.setOrientation(LinearLayout.HORIZONTAL);

        // now add images
        if (workout.getCheckPictures()) {
            for (Bitmap img : workout.getPictures()) {
                LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(100, 100);// width, height
                ImageView imgBox = new ImageView(this);
                imgBox.setLayoutParams(imgParams);
                imgBox.setImageBitmap(img);
                horLinearImageView.addView(imgBox);
            }
        }
        imageScrollView.addView(horLinearImageView);
        mainHolderView.addView(imageScrollView);

        // main regions
        if (workout.getCheckMainRegions()) {
            String mainRegionsText = "";
            for (String region : workout.getMainRegions()) {
                mainRegionsText += region + " ";
            }
            TextView mainRegionsTitleTv = new TextView(this);
            mainRegionsTitleTv.setText("Main Regions");
            mainHolderView.addView(mainRegionsTitleTv);

            TextView mainRegionsTv = new TextView(this);
            mainRegionsTv.setText(mainRegionsText);
            mainHolderView.addView(mainRegionsTv);
        }

        // sub regions
        if (workout.getCheckSubRegions()) {
            String subRegionsText = "";
            for (String region : workout.getSubRegions()) {
                subRegionsText += region + " ";
            }
            TextView subRegionsTitleTv = new TextView(this);
            subRegionsTitleTv.setText("Sub Regions");
            mainHolderView.addView(subRegionsTitleTv);

            TextView subRegionsTv = new TextView(this);
            subRegionsTv.setText(subRegionsText);
            mainHolderView.addView(subRegionsTv);
        }
    }
}
