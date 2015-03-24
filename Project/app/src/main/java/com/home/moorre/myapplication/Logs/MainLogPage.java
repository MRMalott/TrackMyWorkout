package com.home.moorre.myapplication.Logs;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.home.moorre.myapplication.R;
import com.home.moorre.myapplication.Workouts.AddWorkoutPage;
import com.home.moorre.myapplication.Workouts.ViewWorkouts;


public class MainLogPage extends ActionBarActivity {
    Button viewLogsBt;
    LinearLayout lastLogLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log_page);

        viewLogsBt = (Button)findViewById(R.id.viewLogsBt);
        lastLogLayout = (LinearLayout)findViewById(R.id.lastLog);
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

            switch (buttonId) {
                case (R.id.btViewLogs):
                    Intent intent = new Intent(MainLogPage.this, MainLogPage.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
