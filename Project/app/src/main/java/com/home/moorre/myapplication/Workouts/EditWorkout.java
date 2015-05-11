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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.home.moorre.myapplication.Classes.Workout;
import com.home.moorre.myapplication.DB.MyDBHandler;
import com.home.moorre.myapplication.MainActivity;
import com.home.moorre.myapplication.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditWorkout extends ActionBarActivity implements View.OnClickListener {
    Button saveBt, helpBt;
    List<Workout> recvWorkout;
    RecvWorkoutsAdapter recvWorkoutsAdapter;
    ListView recvWorkoutLv;
    Workout workoutRecv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        //Get the workout name
        Intent intent = getIntent();
        String nameMessage = intent.getStringExtra("editKey");

        //Custom font
        String fontPath = "fonts/SFEspressoShack.otf";
        TextView customTV = (TextView) findViewById(R.id.Bcustomtv);
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
        saveBt = (Button) findViewById(R.id.saveBt);
        helpBt = (Button) findViewById(R.id.helpBt);

        //Button listeners
        saveBt.setOnClickListener(this);
        helpBt.setOnClickListener(this);

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
            case R.id.helpBt:
                Toast.makeText(EditWorkout.this, "Name: Name cannot be blank \n\n" +
                                                 "Description: Any \n\n" +
                                                 "Type: aerobic, strength or yoga \n\n" +
                                                 "Primary: shoulders, chest, arms, \n" +
                                                 "         abs, back or legs \n\n" +
                                                 "Targeted/Secondary: deltoids, \n" +
                                                 "         pectorals, triceps, biceps, \n" +
                                                 "         abs, forearms, quads, \n" +
                                                 "         calves, lats, mid back, \n" +
                                                 "         lower back, glutes, \n" +
                                                 "         hamstrings  ", Toast.LENGTH_LONG).show();
                break;
            case R.id.saveBt:
                // name
                EditText nameText = (EditText) findViewById(R.id.wkoutName1);
                if (nameText.getText().toString().isEmpty()) {
                    Toast.makeText(EditWorkout.this, "Name cannot be blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                workoutRecv.setName(cleanName(nameText.getText().toString()));

                // description
                EditText descText = (EditText) findViewById(R.id.BdisplayWorkoutDescTv2);
                workoutRecv.setDescription(cleanName(descText.getText().toString()));

                // type
                EditText typeText = (EditText) findViewById(R.id.BdisplayWorkoutTypeTv2);
                int workoutTypeId = MyDBHandler.WORKOUT_TYPE_IDS.get(cleanName(typeText.getText().toString())); //workout type
                if(cleanName(typeText.getText().toString()).equalsIgnoreCase("aerobic") ||
                        cleanName(typeText.getText().toString()).equalsIgnoreCase("strength") ||
                        cleanName(typeText.getText().toString()).equalsIgnoreCase("yoga") ||
                        cleanName(typeText.getText().toString()).isEmpty()){
                    workoutRecv.setWorkoutTypeId(workoutTypeId);
                }
                else{
                    Toast err = Toast.makeText(EditWorkout.this, "Error: Invalid workout type", Toast.LENGTH_SHORT);
                    err.show();
                    return;
                }

                // group
                EditText groupText = (EditText) findViewById(R.id.BdisplayMuscleGroupTv2);
                int muscleGroupId = MyDBHandler.GROUPING_IDS.get(cleanName(groupText.getText().toString())); // muscle group
                if(cleanName(groupText.getText().toString()).equalsIgnoreCase("shoulders") ||
                        cleanName(groupText.getText().toString()).equalsIgnoreCase("chest") ||
                        cleanName(groupText.getText().toString()).equalsIgnoreCase("arms") ||
                        cleanName(groupText.getText().toString()).equalsIgnoreCase("abs") ||
                        cleanName(groupText.getText().toString()).equalsIgnoreCase("back") ||
                        cleanName(groupText.getText().toString()).equalsIgnoreCase("legs") ||
                        cleanName(groupText.getText().toString()).isEmpty()){
                    workoutRecv.setMuscleGroupId(muscleGroupId);
                }
                else{
                    Toast err = Toast.makeText(EditWorkout.this, "Error: Invalid primary group", Toast.LENGTH_SHORT);
                    err.show();
                    return;
                }

                // main regions
                EditText mainRegionsText = (EditText) findViewById(R.id.BdisplayMainRegionsTv2);
                String regions = cleanName(mainRegionsText.getText().toString());
                ArrayList<String> mainRegions = new ArrayList<String>(Arrays.asList(regions.split(",")));
                ArrayList<String> main = new ArrayList<String>();
                main.add("trapezius");
                main.add("deltoids");
                main.add("pectorals");
                main.add("triceps");
                main.add("biceps");
                main.add("abs");
                main.add("forearms");
                main.add("quads");
                main.add("calves");
                main.add("lats");
                main.add("mid back");
                main.add("lower back");
                main.add("glutes");
                main.add("hamstrings");

                for (String region : mainRegions) {
                    region = cleanName(region);
                    if(main.contains(region) || region.isEmpty()){}
                    else {
                        Toast err = Toast.makeText(EditWorkout.this, "Error: Invalid targeted area", Toast.LENGTH_SHORT);
                        err.show();
                        return;
                    }
                }
                workoutRecv.setMainRegions(mainRegions);

                // sub regions
                EditText subRegionsText = (EditText) findViewById(R.id.BdisplaySubRegionsTv2);
                String regionsSub = cleanName(subRegionsText.getText().toString());
                ArrayList<String> subRegions = new ArrayList<String>(Arrays.asList(regionsSub.split(",")));
                for (String region : subRegions) {
                    region = cleanName(region);
                    if(main.contains(region) || region.isEmpty()){}
                    else {
                        Toast err = Toast.makeText(EditWorkout.this, "Error: Invalid secondary area", Toast.LENGTH_SHORT);
                        err.show();
                        return;
                    }
                }
                workoutRecv.setSubRegions(subRegions);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                MyDBHandler db = getDb();
                                db.updateWorkout(workoutRecv);

                                Toast success = Toast.makeText(EditWorkout.this, "Workout edited", Toast.LENGTH_SHORT);
                                success.show();
                                finish();

                                Intent intent = new Intent();
                                intent.putExtra("key1", workoutRecv.getName());
                                intent.setClass(EditWorkout.this, ViewSingleWorkout.class);
                                startActivity(intent);

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setMessage("Save changes to this workout?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
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
                LayoutInflater inflater = (LayoutInflater) EditWorkout.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.workout_view_single_editable, parent, false);
            }

            // Set new view's fields

            // name
            EditText nameTv = (EditText)convertView.findViewById(R.id.wkoutName1);
            nameTv.setText(workout.getName());

            // description
            EditText descTv = (EditText)convertView.findViewById(R.id.BdisplayWorkoutDescTv2);
            descTv.setText(workout.getDescription());

            // workout type
            EditText workoutTypeTv = (EditText)convertView.findViewById(R.id.BdisplayWorkoutTypeTv2);
            workoutTypeTv.setText(dbHandler.findWorkoutTypeNameById(workout.getWorkoutTypeId()));


            // muscle group
            EditText muscleGroupTv = (EditText)convertView.findViewById(R.id.BdisplayMuscleGroupTv2);
            muscleGroupTv.setText(dbHandler.findMuscleGroupNameById(workout.getMuscleGroupId()));

            dbHandler.close();

            // pictures
            LinearLayout horLinearImageView = (LinearLayout) convertView.findViewById(R.id.BdisplayPictureLl2);

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

                EditText mainRegionsTv = (EditText)convertView.findViewById(R.id.BdisplayMainRegionsTv2);
                mainRegionsTv.setText(mainRegionsText);
            } else {
                EditText mainRegionsTv = (EditText)convertView.findViewById(R.id.BdisplayMainRegionsTv2);
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

                EditText subRegionsTv = (EditText)convertView.findViewById(R.id.BdisplaySubRegionsTv2);
                subRegionsTv.setText(subRegionsText);
            } else {
                EditText subRegionsTv = (EditText)convertView.findViewById(R.id.BdisplaySubRegionsTv2);
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
