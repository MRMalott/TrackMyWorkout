package com.home.moorre.myapplication.Logs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.moorre.myapplication.Classes.Set;
import com.home.moorre.myapplication.Classes.WorkoutLog;
import com.home.moorre.myapplication.DB.MyDBHandler;
import com.home.moorre.myapplication.R;
import com.home.moorre.myapplication.Workouts.AddWorkoutPage;
import com.home.moorre.myapplication.Workouts.ViewWorkouts;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class MainLogPage extends ActionBarActivity {
    Button viewLogsBt;
    Button addLogsBt;
    ListView lastLogLayout;
    List<WorkoutLog> lastDaysLogs;
    foundLogsAdapter logsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log_page);

        viewLogsBt = (Button)findViewById(R.id.viewLogsBt);
        viewLogsBt.setOnClickListener(new ButtonClickListener());
        addLogsBt = (Button)findViewById(R.id.goToAddLogPageBt);
        addLogsBt.setOnClickListener(new ButtonClickListener());
        lastDaysLogs = new ArrayList<WorkoutLog>();
        lastLogLayout = (ListView) findViewById(R.id.lastLogs);
        logsAdapter = new foundLogsAdapter();
        lastLogLayout.setAdapter(logsAdapter);

        fillRecentActivity();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_log_page, menu);
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
    Methods
     */

    public void fillRecentActivity() {
        MyDBHandler db = new MyDBHandler(this, null, null, 1);

        try {
            lastDaysLogs.clear();
            lastDaysLogs.addAll(db.findRecentWorkouts());
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load logs", Toast.LENGTH_SHORT).show();
        }

        logsAdapter.notifyDataSetChanged();
        db.close();
    }

    /*
    Adapters and Listeners
     */
    private class ButtonClickListener implements View.OnClickListener {
        public void onClick(View v) {
            Button b = (Button) (v);
            int buttonId = b.getId();

            switch (buttonId) {
                case (R.id.viewLogsBt):
                    Intent intent = new Intent(MainLogPage.this, ViewLogPage.class);
                    startActivity(intent);
                    break;
                case (R.id.goToAddLogPageBt):
                    intent = new Intent(MainLogPage.this, AddLogPage.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    class foundLogsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return lastDaysLogs.size();
        }

        @Override
        public Object getItem(int position) {
            return lastDaysLogs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            WorkoutLog log = lastDaysLogs.get(position);

            // If null then create new view
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) MainLogPage.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.log_view, parent, false);
            }

            // Set new view's fields
            ((TextView)convertView.findViewById(R.id.displayLogDateTv)).setText(new Date(log.getWorkoutDate()).toString());
            ((TextView)convertView.findViewById(R.id.displayLogWorkoutNameTv)).setText(log.getLoggedWorkout().getName());
            ((TextView)convertView.findViewById(R.id.displayLogWorkoutNotesTv)).setText(log.getNotes());

            // Set inputs
            LinearLayout inputArea = (LinearLayout)convertView.findViewById(R.id.inputsArea); // Inputs Area
            inputArea.removeAllViewsInLayout();
            TextView simpleInputs = new TextView(convertView.getContext());
            if(!log.usesSets()) {
                simpleInputs.setText(log.getAerobic().getFeet() + " " + log.getAerobic().getSeconds());
            } else {
                StringBuilder inputText = new StringBuilder();
                int setNumber = 1;
                for(Set set : log.getAnaerobic().getSets()) {
                    inputText.append("Set " + (setNumber++) + " " + set.getReps() + " " + set.getWeight() + "\n");
                }
                simpleInputs.setText(inputText.toString());
            }
            inputArea.addView(simpleInputs);

            return convertView;
        }
    }
}
