package com.home.moorre.myapplication.Workouts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.home.moorre.myapplication.Classes.Workout;
import com.home.moorre.myapplication.DB.MyDBHandler;
import com.home.moorre.myapplication.MainActivity;
import com.home.moorre.myapplication.R;
import java.util.ArrayList;
import java.util.List;

public class ViewSingleWorkout extends ActionBarActivity implements View.OnClickListener {
    Button deleteBt, editBt;
    List<Workout> recvWorkout;
    RecvWorkoutsAdapter recvWorkoutsAdapter;
    ListView recvWorkoutLv;
    Workout workoutRecv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_workout);

        //Get the workout name
        Intent intent = getIntent();
        String nameMessage = intent.getStringExtra("key1");

        //Custom font
        String fontPath = "fonts/SFEspressoShack.otf";
        TextView customTV = (TextView) findViewById(R.id.customtv);
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        customTV.setTypeface(tf);
        customTV.setText(nameMessage);

        //Get full workout by name
        MyDBHandler db = getDb();
        workoutRecv = db.findFullWorkoutByName(cleanName(nameMessage));

        recvWorkout = new ArrayList<>();
        recvWorkoutsAdapter = new RecvWorkoutsAdapter();
        recvWorkoutLv = (ListView) findViewById(R.id.RecvWorkoutLv);
        recvWorkoutLv.setAdapter(recvWorkoutsAdapter);

        //Buttons
        deleteBt = (Button) findViewById(R.id.deleteBt);
        editBt = (Button) findViewById(R.id.editBt);

        //Button listeners
        deleteBt.setOnClickListener(this);
        editBt.setOnClickListener(this);

        try {
            fillWorkouts();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_view_single_workout, menu);
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
    public void onClick (View v) {

        switch(v.getId()){
            case R.id.deleteBt:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                MyDBHandler db = getDb();
                                db.deleteWorkout(workoutRecv.getName());

                                Intent intent=new Intent();
                                intent.setClass(ViewSingleWorkout.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setMessage("Delete this workout?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                break;
            case R.id.editBt:
                Intent intent=new Intent();
                intent.setClass(ViewSingleWorkout.this, EditWorkout.class);
                intent.putExtra("editKey", workoutRecv.getName());
                startActivity(intent);
                break;
        }
    }

    public void fillWorkouts() throws Exception {
        try {
            // Ignore if there are no workouts found
            if (workoutRecv == null) return;

            List<Workout> qResults = new ArrayList<>();
            qResults.add(workoutRecv);

            recvWorkout.clear();
            recvWorkout.addAll(qResults);
        } catch(Exception e) {
            throw e;
        }

        recvWorkoutsAdapter.notifyDataSetChanged();
    }

    class RecvWorkoutsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return recvWorkout.size();
        }

        @Override
        public Object getItem(int position) {
            return recvWorkout.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Workout workout = recvWorkout.get(position);
            MyDBHandler dbHandler = getDb();

            // If null then create new view
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) ViewSingleWorkout.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.workout_view_single, parent, false);
            }

            // Set new view's fields

            // description
            TextView descTv = (TextView)convertView.findViewById(R.id.displayWorkoutDescTv2);
            descTv.setText(workout.getDescription());

            // workout type
            TextView workoutTypeTv = (TextView)convertView.findViewById(R.id.displayWorkoutTypeTv2);
            workoutTypeTv.setText(dbHandler.findWorkoutTypeNameById(workout.getWorkoutTypeId()));

            // muscle group
            TextView muscleGroupTv = (TextView)convertView.findViewById(R.id.displayMuscleGroupTv2);
            muscleGroupTv.setText(dbHandler.findMuscleGroupNameById(workout.getMuscleGroupId()));

            dbHandler.close();

            // pictures
            LinearLayout horLinearImageView = (LinearLayout) convertView.findViewById(R.id.displayPictureLl2);

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
                for (String region : workout.getMainRegions()) {
                    mainRegionsText += region + ", ";
                    System.out.print("Found main region " + region);
                }
                mainRegionsText = mainRegionsText.substring(0, mainRegionsText.length()-2);

                TextView mainRegionsTv = (TextView)convertView.findViewById(R.id.displayMainRegionsTv2);
                mainRegionsTv.setText(mainRegionsText);
            } else {
                TextView mainRegionsTv = (TextView)convertView.findViewById(R.id.displayMainRegionsTv2);
                mainRegionsTv.setText("");
            }

            // sub regions
            if (workout.getCheckSubRegions()) {
                String subRegionsText = "";
                for (String region : workout.getSubRegions()) {
                    subRegionsText += region + ", ";
                    System.out.print("Found subregion " + region);
                }
                subRegionsText = subRegionsText.substring(0, subRegionsText.length()-2);

                TextView subRegionsTv = (TextView)convertView.findViewById(R.id.displaySubRegionsTv2);
                subRegionsTv.setText(subRegionsText);
            } else {
                TextView subRegionsTv = (TextView)convertView.findViewById(R.id.displaySubRegionsTv2);
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
