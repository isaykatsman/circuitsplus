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
import android.widget.TextView;

/**
 * This class serves as a generic intro class for the circuit interactive activity
 */
public class Chapter1S1Intro extends Activity implements View.OnClickListener {

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //request full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.infoc1s1);

        //get id of caller
        Bundle b = getIntent().getExtras();
        if(b!=null)
            id = b.getInt("id");

        initVars();
    }

    private void initVars() {
        final Button toInteractive = (Button) findViewById(R.id.bGoInteractive);
        final Button bBack = (Button) findViewById(R.id.bBackCh1Info);

        //set text based on id
        final TextView mainText = (TextView) findViewById(R.id.tVINF1);
        CircuitActivityInfo currentInfo = ChaptersInfo.getCircuitInfo(id);
        mainText.setText(currentInfo.getInitialDescription());

        toInteractive.setOnClickListener(this);
        bBack.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chapter1_s1_intro, menu);
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
            case R.id.bGoInteractive:
                final Intent in2 = new Intent(this, CircuitInteractiveActivity.class);
                in2.putExtra("id",id);
                startActivity(in2);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);
                finish();//also KILL current activity
                break;
            case R.id.bBackCh1Info:
                final Intent in3 = new Intent(this, ChaptersActivity.class);
                startActivity(in3);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);
                finish();//also KILL current activity
                break;
        }
    }
}
