package com.home.moorre.myapplication.Workouts;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Typeface;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.home.moorre.myapplication.Classes.Workout;
        import com.home.moorre.myapplication.DB.MyDBHandler;
        import com.home.moorre.myapplication.Logs.AddLogPage;
        import com.home.moorre.myapplication.R;
        import java.util.ArrayList;
        import java.util.List;

        import static android.graphics.Typeface.*;

public class ViewWorkouts extends ActionBarActivity implements View.OnClickListener{
    EditText lookupTv;
    Button lookupBt;
    List<Workout> workouts;
    View lastSelectedView = null;
    boolean fromLogPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workouts);

        // set log page parameters
        Bundle params = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        fromLogPage = params.getBoolean("logLookup", false);

        //Custom font
        String fontPath = "fonts/SFEspressoShack.otf";
        TextView customTV = (TextView) findViewById(R.id.viewWorkoutsTV);
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        customTV.setTypeface(tf);

        //Hide soft keyboard on touch
        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard(v);
                return false;
            }
        });

        //Receive the filter to select workout by type
        Intent intent = getIntent();
        String filterId = intent.getStringExtra("filterkey");

        //Search field and button
        lookupTv = (EditText) findViewById(R.id.lookupTv);
        lookupBt = (Button) findViewById(R.id.lookupBt);
        lookupBt.setOnClickListener(this);

        //Select workouts by filter received and put in workouts list
        MyDBHandler dbHandler = getDb();
        switch (filterId) {
            case "all":
                workouts = dbHandler.getAllWorkouts(); //Get all workouts
                break;
            case ("aerobic"):
                workouts = dbHandler.getAllWorkoutsByType(0); //Get all aerobic workouts
                break;
            case ("strength"):
                workouts = dbHandler.getAllWorkoutsByType(1); //Get all strength workouts
                break;
            case ("yoga"):
                workouts = dbHandler.getAllWorkoutsByType(2); //Get all yoga workouts
        }


        ListView allWorkoutsLv = (ListView) findViewById(R.id.allWorkoutsLv);
        ArrayAdapter<Workout> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, workouts);

        allWorkoutsLv.setAdapter(adapter);

        allWorkoutsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Check whether here for log lookup
                if (fromLogPage) {
                    Intent intent = new Intent(ViewWorkouts.this, AddLogPage.class);
                    intent.putExtra("logLookup", true);
                    intent.putExtra("selectedWorkout", workouts.get(position).getName());
                    startActivity(intent);
                } else {
                    //Highlight last clicked view
                    if (lastSelectedView != null) {
                        lastSelectedView.setBackgroundResource(R.color.white);
                    }
                    lastSelectedView = view;
                    view.setBackgroundResource(R.drawable.roundbgwhite);

                    //Open workout details
                    Workout workout = workouts.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("key1", workout.getName());
                    intent.setClass(ViewWorkouts.this, ViewSingleWorkout.class);
                    startActivity(intent);
                }
            }
        });
    }

    public static void hideSoftKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.lookupBt:
                MyDBHandler db = getDb();
                // Ignore if there is no input
                if (lookupTv.getText().length() == 0) {
                    return;
                }
                try {
                    Workout foundWorkout = db.findFullWorkoutByName(cleanName(lookupTv.getText().toString()));
                    // Ignore if there are no workouts found
                    if (foundWorkout == null || foundWorkout.getName() == null) {
                        Toast.makeText(ViewWorkouts.this, "Workout '"
                                + lookupTv.getText().toString()
                                + "' not found", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent2 = new Intent();
                        intent2.putExtra("key1", foundWorkout.getName());
                        intent2.setClass(ViewWorkouts.this, ViewSingleWorkout.class);
                        startActivity(intent2);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
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