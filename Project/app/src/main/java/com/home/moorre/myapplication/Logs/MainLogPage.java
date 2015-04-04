package com.home.moorre.myapplication.Logs;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
<<<<<<< HEAD
import android.widget.ListView;
=======
>>>>>>> dev

import com.home.moorre.myapplication.R;
import com.home.moorre.myapplication.Workouts.AddWorkoutPage;
import com.home.moorre.myapplication.Workouts.ViewWorkouts;


public class MainLogPage extends ActionBarActivity {
    Button viewLogsBt;
<<<<<<< HEAD
    Button addLogsBt;
    ListView lastLogLayout;
=======
    LinearLayout lastLogLayout;
>>>>>>> dev


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log_page);

        viewLogsBt = (Button)findViewById(R.id.viewLogsBt);
<<<<<<< HEAD
        viewLogsBt.setOnClickListener(new ButtonClickListener());
        addLogsBt = (Button)findViewById(R.id.goToAddLogPageBt);
        addLogsBt.setOnClickListener(new ButtonClickListener());
        lastLogLayout = (ListView) findViewById(R.id.lastLog);
=======
        lastLogLayout = (LinearLayout)findViewById(R.id.lastLog);
>>>>>>> dev
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

    // Listener for buttons
    private class ButtonClickListener implements View.OnClickListener {
        public void onClick(View v) {
            Button b = (Button) (v);
            int buttonId = b.getId();

<<<<<<< HEAD
            System.out.println(buttonId + " : " + R.id.viewLogsBt);

            switch (buttonId) {
                case (R.id.viewLogsBt):
                    Intent intent = new Intent(MainLogPage.this, ViewLogPage.class);
                    startActivity(intent);
                    break;
                case (R.id.goToAddLogPageBt):
                    intent = new Intent(MainLogPage.this, AddLogPage.class);
=======
            switch (buttonId) {
                case (R.id.btViewLogs):
                    Intent intent = new Intent(MainLogPage.this, MainLogPage.class);
>>>>>>> dev
                    startActivity(intent);
                    break;
            }
        }
    }
}
