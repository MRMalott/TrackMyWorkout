package com.home.moorre.myapplication.Logs;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.moorre.myapplication.Classes.Set;
import com.home.moorre.myapplication.Classes.WorkoutLog;
import com.home.moorre.myapplication.DB.MyDBHandler;
import com.home.moorre.myapplication.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ViewLogPage extends ActionBarActivity {
    DatePicker findLogDayPicker;
    ListView foundLogsLv;
    Button findLogBt;
    List<WorkoutLog> foundLogs;
    ArrayAdapter<WorkoutLog> foundLogsAdapter;
    LinearLayout tempSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_log_page);

        foundLogs = new ArrayList<WorkoutLog>();
        foundLogsAdapter = new ArrayAdapter<WorkoutLog>(this,
                R.layout.log_view,
                foundLogs);

        findLogDayPicker = (DatePicker)findViewById(R.id.dateLogDay);
        findLogBt = (Button)findViewById(R.id.findLogsBt);
        findLogBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fillLogs();
                } catch (Exception e) {
                    Toast failToast = Toast.makeText(ViewLogPage.this, "Failed to lookup logs", Toast.LENGTH_SHORT);
                    failToast.show();
                }
            }
        });
        foundLogsLv = (ListView)findViewById(R.id.foundLogs);
        foundLogsLv.setAdapter(new foundLogsAdapter());
        tempSpace = (LinearLayout) findViewById(R.id.tempSpace);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_log_page, menu);
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



    public void fillLogs() throws Exception {
        Long lookupDate = getDateFromDatePicker(findLogDayPicker);
        MyDBHandler db = new MyDBHandler(this, null, null, 1);

        try {
            foundLogs = db.findFullLogsByDate(lookupDate);
            System.out.println("found logs");
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }

        // Display found logs
        for (WorkoutLog log : foundLogs) {
            foundLogs.add(log);
            // Set new view's fields
            TextView tempWorkoutDate = new TextView(this);
            tempWorkoutDate.setText(new Date(log.getWorkoutDate()).toString());
            tempSpace.addView(tempWorkoutDate);
            TextView tempWorkoutName = new TextView(this);
            tempWorkoutName.setText(log.getLoggedWorkout().getName());
            tempSpace.addView(tempWorkoutName);
            TextView tempWorkoutNotes = new TextView(this);
            tempWorkoutNotes.setText(log.getNotes());
            tempSpace.addView(tempWorkoutNotes);

            // Set inputs
            //LayoutInflater inflater = (LayoutInflater) ViewLogPage.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = inflater.inflate(R.layout.log_view, parent, false);
            LinearLayout inputArea = new LinearLayout(this); // Inputs Area
            TextView simpleInputs = new TextView(this);
            if(log.isAerobic()) {
                simpleInputs.setText(log.getAerobic().getFeet() + " " + log.getAerobic().getSeconds());
            } else {
                StringBuilder inputText = new StringBuilder();
                for(Set set : log.getAnaerobic().getSets()) {
                    inputText.append(set.getReps() + " " + set.getWeight() + "\n");
                }
                simpleInputs.setText(inputText.toString());
            }
            inputArea.addView(simpleInputs);
            tempSpace.addView(inputArea);
        }
        System.out.println("ready to notify");
        //foundLogsLv.getAdapter().deferNotifyDataSetChanged();
        System.out.println("notified");

        db.close();
    }

    /**
     * Get the date from the date picker widget
     * @param datePicker
     * @return a java.util.Date
     */
    public static long getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);

        return calendar.getTimeInMillis() - calendar.getTimeInMillis()%1000;
    }

    class foundLogsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return foundLogs.size();
        }

        @Override
        public Object getItem(int position) {
            return foundLogs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            WorkoutLog log = foundLogs.get(position);

            // If null then create new view
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) ViewLogPage.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.log_view, parent, false);
            }

            // Set new view's fields
            ((TextView)convertView.findViewById(R.id.displayLogDateTv)).setText(new Date(log.getWorkoutDate()).toString());
            ((TextView)convertView.findViewById(R.id.displayLogWorkoutNameTv)).setText(log.getLoggedWorkout().getName());
            ((TextView)convertView.findViewById(R.id.displayLogWorkoutNotesTv)).setText(log.getNotes());

            // Set inputs
            LinearLayout inputArea = (LinearLayout)convertView.findViewById(R.id.inputsArea); // Inputs Area
            TextView simpleInputs = new TextView(convertView.getContext());
            if(log.isAerobic()) {
                simpleInputs.setText(log.getAerobic().getFeet() + " " + log.getAerobic().getSeconds());
            } else {
                StringBuilder inputText = new StringBuilder();
                for(Set set : log.getAnaerobic().getSets()) {
                    inputText.append(set.getReps() + " " + set.getWeight() + "\n");
                }
                simpleInputs.setText(inputText.toString());
            }
            inputArea.addView(simpleInputs);

            return convertView;
        }
    }

}
