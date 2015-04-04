package com.home.moorre.myapplication.Workouts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.home.moorre.myapplication.Classes.Workout;
import com.home.moorre.myapplication.DB.MyDBHandler;
import com.home.moorre.myapplication.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class AddWorkoutPage extends ActionBarActivity {
    TextView idView;
    EditText workoutBox;
    EditText descriptionBox;
    // workout type
    Spinner workoutTypeDrop;
    String workoutTypeDropValue = "anaerobic";
    // muscle group
    Spinner muscleGroupDrop;
    String muscleGroupDropValue = "trapezius";
    // pictures
    LinearLayout imageLayout;
    ImageView mainImageView;
    Button addImageBt;
    ArrayList<Bitmap> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_page);

        idView = (TextView) findViewById(R.id.tvId);
        workoutBox = (EditText) findViewById(R.id.tvName);
        descriptionBox = (EditText) findViewById(R.id.tvDesc);

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
                System.out.println("Selected " + workoutTypeDropValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                workoutTypeDropValue = "anaerobic";
            }
        });

        muscleGroupDrop = (Spinner)findViewById(R.id.ddMuscleGroup);
        final ArrayAdapter<String> muscleGroupAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MyDBHandler.GROUPING_IDS.keySet().toArray(new String[MyDBHandler.GROUPING_IDS.size()]));
        workoutDropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        muscleGroupDrop.setAdapter(muscleGroupAdapter);
        muscleGroupDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItem, int pos, long id)
            {
                Object item = parent.getItemAtPosition(pos);
                muscleGroupDropValue = item.toString();
                System.out.println("Selected " + muscleGroupDropValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                muscleGroupDropValue = "trapezius";
            }
        });

        imageLayout = (LinearLayout) findViewById(R.id.imageField);
        addImageBt = (Button) findViewById(R.id.addImgBt);
        addImageBt.setOnClickListener(new addImageListener());
        images = new ArrayList<Bitmap>();

        int[] mainCheckboxes = {R.id.mainChk1, R.id.mainChk2, R.id.mainChk3, R.id.mainChk4, R.id.mainChk5, R.id.mainChk6, R.id.mainChk7,
                R.id.mainChk8, R.id.mainChk9, R.id.mainChk10, R.id.mainChk11, R.id.mainChk12, R.id.mainChk13, R.id.mainChk14};
        int[] subCheckboxes = {R.id.subChk1, R.id.subChk2, R.id.subChk3, R.id.subChk4, R.id.subChk5, R.id.subChk6, R.id.subChk7,
                R.id.subChk8, R.id.subChk9, R.id.subChk10, R.id.subChk11, R.id.subChk12, R.id.subChk13, R.id.subChk14};
        int checkboxIndex = 0;
        for (String region : MyDBHandler.REGION_IDS.keySet().toArray(new String[MyDBHandler.REGION_IDS.size()])) {
            // Get checkbox views
            CheckBox mainChk = (CheckBox) findViewById(mainCheckboxes[checkboxIndex]);
            CheckBox subChk = (CheckBox) findViewById(subCheckboxes[checkboxIndex]);

            // set text
            mainChk.setText(region);
            subChk.setText(region);

            checkboxIndex++;
        }
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
     Click listeners
      */

    private class addImageListener implements View.OnClickListener {
        public final static int addImgCode = 101;
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT); // will get something
            startActivityForResult(Intent.createChooser(intent, "Select workout image"), addImgCode);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent returnedData) {
        super.onActivityResult(requestCode, resultCode, returnedData);
        if (resultCode == RESULT_OK) {
            if (requestCode == addImageListener.addImgCode) {
                // Get bitmap
                Uri orgUri = returnedData.getData();
                Bitmap bm = null;
                try {
                    bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), orgUri);
                } catch (FileNotFoundException e) {
                    Log.d("imgs", e.toString());
                    e.printStackTrace();
                    Toast err = Toast.makeText(this, "Uploading the image failed", Toast.LENGTH_SHORT);
                    err.show();
                    return;
                } catch (IOException e) {
                    Log.d("imgs", e.toString());
                    e.printStackTrace();
                    Toast err = Toast.makeText(this, "Uploading the image failed", Toast.LENGTH_SHORT);
                    err.show();
                    return;
                }

                // Create image view
                LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(100, 100);// width, height
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(imgParams);
                imageView.setImageBitmap(bm);
                imageLayout.addView(imageView, imgParams);

                // Add image to listing
                images.add(bm);

                // Tell user image was added
                Toast success = Toast.makeText(this, "Uploaded the image", Toast.LENGTH_SHORT);
                success.show();
            }
        }
    }

    /*
    DBHandlers
     */

    public void newWorkout (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        // Assure name not taken already
        boolean nameExists = true;
        try {
        } catch(Exception e) {
            nameExists = false;
        }

        if(nameExists) {
            Toast err = Toast.makeText(this, "Name already exists", Toast.LENGTH_SHORT);
            err.show();
            return;
        }

        String desc = descriptionBox.getText().toString().trim();
        String name = cleanName(workoutBox.getText().toString());
        int workoutTypeId = MyDBHandler.WORKOUT_TYPE_IDS.get(workoutTypeDropValue); //workout type
        int muscleGroupId = MyDBHandler.GROUPING_IDS.get(muscleGroupDropValue); // muscle group
        // images
        ArrayList<String> selectedMainRegions = getRegionCheckboxValues(true);
        ArrayList<String> selectedSubRegions = getRegionCheckboxValues(false);

        Workout workout = new Workout();
        workout.setName(name);
        workout.setDescription(desc);
        workout.setWorkoutTypeId(workoutTypeId);
        workout.setMuscleGroupId(muscleGroupId);
        workout.setPictures(images);
        if (images.isEmpty()) {
            workout.setCheckPictures(false);
        } else {
            workout.setCheckPictures(true);
        }
        workout.setMainRegions(selectedMainRegions);
        if (selectedMainRegions.isEmpty()) {
            workout.setCheckMainRegions(false);
        } else {
            workout.setCheckMainRegions(true);
        }
        workout.setSubRegions(selectedSubRegions);
        if (selectedSubRegions.isEmpty()) {
            workout.setCheckSubRegions(false);
        } else {
            workout.setCheckSubRegions(true);
        }

        // Try to add the workout
        try {
            dbHandler.addWorkout(workout);
            Toast good = Toast.makeText(this, "Added workout", Toast.LENGTH_SHORT);
            good.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast err = Toast.makeText(this, "Couldn't add workout", Toast.LENGTH_SHORT);
            err.show();
<<<<<<< HEAD
        } finally {
            dbHandler.close();
=======
>>>>>>> dev
        }
    }

    /**
     * Get what checkboxes where selected
     * @param main checks main boxes if true, sub boxes if not
     * @return ArrayList<String> of selected regions or empty ArrayList
     */
    private ArrayList<String> getRegionCheckboxValues(boolean main) {
        ArrayList<String> selectedRegions = new ArrayList<String>(14);
        if (main) {
            int[] mainCheckboxes = {R.id.mainChk1, R.id.mainChk2, R.id.mainChk3, R.id.mainChk4, R.id.mainChk5, R.id.mainChk6, R.id.mainChk7,
                    R.id.mainChk8, R.id.mainChk9, R.id.mainChk10, R.id.mainChk11, R.id.mainChk12, R.id.mainChk13, R.id.mainChk14};
            for (int checkboxId = 0; checkboxId < mainCheckboxes.length; checkboxId++) {
                CheckBox chk = (CheckBox) findViewById(mainCheckboxes[checkboxId]);
                // If selected add the region
                if (chk.isChecked()) {
                    selectedRegions.add(chk.getText().toString());
                }
            }
        } else {
            int[] subCheckboxes = {R.id.subChk1, R.id.subChk2, R.id.subChk3, R.id.subChk4, R.id.subChk5, R.id.subChk6, R.id.subChk7,
                    R.id.subChk8, R.id.subChk9, R.id.subChk10, R.id.subChk11, R.id.subChk12, R.id.subChk13, R.id.subChk14};
            for (int checkboxId = 0; checkboxId < subCheckboxes.length; checkboxId++) {
                CheckBox chk = (CheckBox) findViewById(subCheckboxes[checkboxId]);
                // If selected add the region
                if (chk.isChecked()) {
                    selectedRegions.add(chk.getText().toString());
                }
            }
        }
        return selectedRegions;
    }

    /*
    Helpers
     */

    public String cleanName(String name) {
        return name.trim().toLowerCase();
    }

}
