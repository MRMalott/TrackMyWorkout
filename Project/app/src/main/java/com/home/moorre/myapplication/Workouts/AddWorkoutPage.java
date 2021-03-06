package com.home.moorre.myapplication.Workouts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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
import com.home.moorre.myapplication.Logs.MainLogPage;
import com.home.moorre.myapplication.MainActivity;
import com.home.moorre.myapplication.R;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class AddWorkoutPage extends ActionBarActivity {
    EditText workoutBox;
    EditText descriptionBox;
    // workout type
    Spinner workoutTypeDrop;
    String workoutTypeDropValue = "strength";
    // muscle group
    Spinner muscleGroupDrop;
    String muscleGroupDropValue = "legs";
    // pictures
    LinearLayout imageLayout;
    ImageView mainImageView;
    Button addImageBt, removePicture;
    ArrayList<Bitmap> images;
    static final boolean MAIN_REGIONS = true;
    static final boolean SUB_REGIONS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_page);

        //Custom font
        String fontPath = "fonts/SFEspressoShack.otf";
        TextView customTV = (TextView) findViewById(R.id.addWorkoutTV);
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        customTV.setTypeface(tf);

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
                workoutTypeDropValue = "strength";
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
                muscleGroupDropValue = "legs";
            }
        });

        imageLayout = (LinearLayout) findViewById(R.id.imageField);
        addImageBt = (Button) findViewById(R.id.addImgBt);
        addImageBt.setOnClickListener(new addImageListener());
        removePicture = (Button) findViewById(R.id.removePicture);
        removePicture.setOnClickListener(new removeImageListener());
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent homeIntent = new Intent(this, MainActivity.class);
            startActivity(homeIntent);
            return true;
        }
        if (id == R.id.action_workouts) {
            Intent workoutIntent = new Intent(this, FilterWorkouts.class);
            startActivity(workoutIntent);
            return true;
        }
        if (id == R.id.action_logs) {
            Intent logIntent = new Intent(this, MainLogPage.class);
            startActivity(logIntent);
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

    private class removeImageListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(images.isEmpty()) {
                Toast.makeText(AddWorkoutPage.this, "No images selected", Toast.LENGTH_SHORT).show();
                return;
            }
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            images.remove(images.size() - 1);
                            imageLayout.removeViewAt(imageLayout.getChildCount() - 1);
                            Toast.makeText(AddWorkoutPage.this, "Image removed", Toast.LENGTH_SHORT).show();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder ab = new AlertDialog.Builder(AddWorkoutPage.this);
            ab.setMessage("Remove last image from workout?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
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

        // Assure name not taken already and not blank
        String name = cleanName(workoutBox.getText().toString());
        if (name.isEmpty()) {
            Toast.makeText(AddWorkoutPage.this, "Name cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean nameExists = true;
        try {
            dbHandler.findWorkoutIdByName(name);
        } catch(Exception e) {
            // Will throw an error if the name doesn't exist
            nameExists = false;
        }

        if(nameExists) {
            Toast err = Toast.makeText(this, "Name already exists", Toast.LENGTH_SHORT);
            err.show();
            return;
        }

        int workoutTypeId = MyDBHandler.WORKOUT_TYPE_IDS.get(workoutTypeDropValue); //workout type
        int muscleGroupId = MyDBHandler.GROUPING_IDS.get(muscleGroupDropValue); // muscle group
        ArrayList<String> selectedMainRegions = getRegionCheckboxValues(MAIN_REGIONS);
        ArrayList<String> selectedSubRegions = getRegionCheckboxValues(SUB_REGIONS);

        Workout workout = new Workout();

        workout.setName(name);
        workout.setDescription(descriptionBox.getText().toString().trim());

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
            Toast.makeText(this, "Added workout", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Couldn't add workout", Toast.LENGTH_SHORT).show();
        } finally {
            dbHandler.close();
        }
    }

    /**
     * Get what checkboxes where selected
     * @param isMain checks main boxes if true, sub boxes if not
     * @return ArrayList<String> of selected regions or empty ArrayList
     */
    private ArrayList<String> getRegionCheckboxValues(boolean isMain) {
        ArrayList<String> selectedRegions = new ArrayList<String>(14);

        if (isMain) {
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
