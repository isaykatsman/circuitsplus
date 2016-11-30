package com.appsforgood.isayandjack.circuitsplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 * MainActivity is the menu screen for the application and is where the application "starts"
 */
public class MainActivity extends Activity implements View.OnClickListener {


    //all components
    ImageView mainCircuitsPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //request full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.main);

        //do all linking with local variables
        initVars();

    }

    private void initVars() {
        final Button goToChapters = (Button) findViewById(R.id.bCHPT);
        final Button goToAbout = (Button) findViewById(R.id.bABT);
        final Button goToSettings = (Button) findViewById(R.id.bSTNG);

        goToChapters.setOnClickListener(this);
        goToAbout.setOnClickListener(this);
        goToSettings.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bCHPT:
                final Intent in2 = new Intent(this, ChaptersActivity.class);
                startActivity(in2);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);
                finish();//also KILL current activity
                break;
            case R.id.bABT:
                final Intent in3 = new Intent(this, AboutActivity.class);
                startActivity(in3);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);
                finish();//also KILL current activity
                break;
            case R.id.bSTNG:
                final Intent in4 = new Intent(this, SettingsActivity.class);
                startActivity(in4);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);
                finish();//also KILL current activity
                break;
        }
    }
}
