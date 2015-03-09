package com.home.moorre.myapplication.Workouts;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.home.moorre.myapplication.Classes.Workout;
import com.home.moorre.myapplication.MyDBHandler;
import com.home.moorre.myapplication.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.Attributes;


public class AddWorkoutPage extends ActionBarActivity {
    TextView idView;
    EditText workoutBox;
    EditText descriptionBox;
    // workout type
    Spinner workoutTypeDrop;
    String workoutTypeDropValue;
    // muscle group
    Spinner muscleGroupDrop;
    String muscleGroupDropValue;
    // pictures
    ImageView mainImageView;
    Button addImageBt;
    ArrayList<Bitmap> images;
    // main regions
    LinearLayout mainRegionsLayoutRight;
    LinearLayout mainRegionsLayoutLeft;
    // sub regions
    LinearLayout subRegionsLayoutRight;
    LinearLayout subRegionsLayoutLeft;
    String subRegionDropValue;

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        muscleGroupDrop = (Spinner)findViewById(R.id.ddMuscleGroup);
        ArrayAdapter<String> muscleGroupAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MyDBHandler.GROUPING_IDS.keySet().toArray(new String[MyDBHandler.GROUPING_IDS.size()]));
        workoutDropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        muscleGroupDrop.setAdapter(muscleGroupAdapter);
        muscleGroupDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItem, int pos, long id)
            {
                Object item = parent.getItemAtPosition(pos);
                workoutTypeDropValue = item.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //MyDBHandler.REGION_IDS.keySet().toArray(new String[MyDBHandler.REGION_IDS.size()])
        mainImageView = (ImageView) findViewById(R.id.mainImageView);
        addImageBt = (Button) findViewById(R.id.addImgBt);
        addImageBt.setOnClickListener(new addImageListener());
        images = new ArrayList<Bitmap>();

        /*mainRegionsLayoutLeft = (LinearLayout) findViewById(R.id.mainRegionsLeft);
        mainRegionsLayoutRight = (LinearLayout) findViewById(R.id.mainRegionsRight);

        for (String region : MyDBHandler.REGION_IDS.keySet().toArray(new String[MyDBHandler.REGION_IDS.size()])) {
            CheckBox chk = new CheckBox(this);
            chk.setText(region);

        }*/

        /*subRegionsLayoutLeft = (LinearLayout) findViewById(R.id.subRegionsLeft);
        subRegionsLayoutRight = (LinearLayout) findViewById(R.id.subRegionsRight);

        MyDBHandler.REGION_IDS.keySet().toArray(new String[MyDBHandler.REGION_IDS.size()])*/

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

    // Click listeners
    /* https://www.youtube.com/watch?v=S8E5GdF0RBA */
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
                } catch (IOException e) {
                    Log.d("imgs", e.toString());
                    e.printStackTrace();
                }

                // Set image as the main image
                if (mainImageView.getVisibility() == ImageView.INVISIBLE) {
                    mainImageView.setImageBitmap(bm);
                    mainImageView.setVisibility(ImageView.VISIBLE); // make visible
                }

                // Add image to listing
                images.add(bm);
            }
        }
    }

    /*
    DBHandler
     */

    public void newWorkout (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String desc = descriptionBox.getText().toString();
        String name = workoutBox.getText().toString();
        String workoutType = workoutTypeDrop.get


        dbHandler.addWorkout(new Workout(name, desc));

        workoutBox.setText("");
        descriptionBox.setText("");
    }
}
