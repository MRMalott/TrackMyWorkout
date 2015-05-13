package com.home.moorre.myapplication.Logs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.home.moorre.myapplication.MainActivity;
import com.home.moorre.myapplication.R;
import com.home.moorre.myapplication.Workouts.FilterWorkouts;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ViewLogPage extends ActionBarActivity {
    DatePicker findLogDayPicker;
    ListView foundLogsLv;
    Button findLogBt, delLog;
    List<WorkoutLog> foundLogs;
    foundLogsAdapter foundLogsAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_log_page);

        foundLogs = new ArrayList<WorkoutLog>();
        foundLogsAdapter1 = new foundLogsAdapter();

        findLogDayPicker = (DatePicker)findViewById(R.id.dateLogDay);
        findLogBt = (Button)findViewById(R.id.findLogsBt);
        findLogBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fillLogs();
                    //Toast.makeText(ViewLogPage.this, "Found logs", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ViewLogPage.this, "Failed to lookup logs", Toast.LENGTH_SHORT).show();
                }
            }
        });
        foundLogsLv = (ListView)findViewById(R.id.foundLogs);
        foundLogsLv.setAdapter(foundLogsAdapter1);

        delLog = (Button) findViewById(R.id.delLog);
        delLog.setOnClickListener(new removeLogListener());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_log_page, menu);
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

    private class removeLogListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            if(foundLogs.isEmpty()) {
                Toast.makeText(ViewLogPage.this, "No logs available", Toast.LENGTH_SHORT).show();
                return;
            }
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            MyDBHandler db = new MyDBHandler(getApplicationContext(), null, null, 1);

                            WorkoutLog log = foundLogs.get(foundLogs.size()-1);
                            foundLogs.remove(foundLogs.size()-1);
                            int id = log.getId();
                            db.deleteLog(id);
                            db.close();

                            Toast.makeText(ViewLogPage.this, "Log removed", Toast.LENGTH_SHORT).show();
                            foundLogsAdapter1.notifyDataSetChanged();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder ab = new AlertDialog.Builder(ViewLogPage.this);
            ab.setMessage("Remove last log?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    public void fillLogs() throws Exception {
        Long lookupDate = getDateFromDatePicker(findLogDayPicker);
        MyDBHandler db = new MyDBHandler(this, null, null, 1);

        try {
            foundLogs = new ArrayList<WorkoutLog>(db.findFullLogsByDate(lookupDate));
            if(foundLogs.size() == 0){
                Toast.makeText(ViewLogPage.this, "No logs found", Toast.LENGTH_SHORT).show();
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }

        foundLogsAdapter1.notifyDataSetChanged();

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
            inputArea.removeAllViewsInLayout();
            TextView simpleInputs = new TextView(convertView.getContext());
            if(!log.usesSets()) {
                simpleInputs.setText("\nDistance: " + log.getAerobic().getFeet()/5280 + " miles  Time: " + log.getAerobic().getSeconds()/60 + " minutes");
            } else {
                StringBuilder inputText = new StringBuilder("");
                int setNumber = 1;
                for(Set set : log.getAnaerobic().getSets()) {
                    inputText.append("\nSet " + (setNumber++) + " - Reps: " + set.getReps() + "  Weight: " + set.getWeight() + " pounds");
                }
                simpleInputs.setText(inputText.toString());
            }

            inputArea.addView(simpleInputs);
            System.out.println("adding a log to view " + log.getNotes());

            return convertView;
        }
    }

}
