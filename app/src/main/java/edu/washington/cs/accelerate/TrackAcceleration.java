package edu.washington.cs.accelerate;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class TrackAcceleration extends ActionBarActivity {
    private static final String TAG = "TrackAcceleration";

    private void startService() {
        Log.i(TAG, "Starting service");
//        this.stopService(new Intent(this, AccelerateService.class));
        ComponentName component = this.startService(new Intent(this, AccelerateService.class));
        Log.i(TAG, "Service: " + component.flattenToString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_acceleration);

        Log.i(TAG, "Creating Activity...");
        startService();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.track_acceleration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        this.startService();
    }
}
