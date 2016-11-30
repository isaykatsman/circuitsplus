package com.appsforgood.isayandjack.circuitsplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * ChapterActivity allows users to select the chapter they want to try out/play
 */
public class ChaptersActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //request full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.chapters);

        //check if returning from completed chapter
        int id=0;
        Bundle b = getIntent().getExtras();
        if(b!=null) {
            id = b.getInt("id");
            Toast.makeText(this, "Congratulations! You have completed Chapter " + (id-10), Toast.LENGTH_LONG).show();
        }

        //initialize variables
        initVars();
    }


    private void initVars() {
        //back
        final Button backButton = (Button) findViewById(R.id.bBackChapters);
        backButton.setOnClickListener(this);
        //list view
        final ListView chaptersListView = (ListView) findViewById(R.id.lVCHPT);
        List<String> chapterTitles = new ArrayList<String>();
        chapterTitles.add("Chapter 1. Intro to Circuits");
        chapterTitles.add("Chapter 2. Lighting LEDs");
        chapterTitles.add("Chapter 3. Intro to Resistors");
        chapterTitles.add("Chapter 4. LED to a Resistor");
        chapterTitles.add("Chapter 5. Multiple Resistors");
        chapterTitles.add("Chapter 6. Intro to Capacitors");
        chapterTitles.add("Chapter 7. Sounds Away!");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.customlistlayout,android.R.id.text1,chapterTitles);
        chaptersListView.setAdapter(adapter);
        chaptersListView.setOnItemClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_chapters, menu);
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
            case R.id.bBackChapters:
                final Intent in2 = new Intent(this, MainActivity.class);
                startActivity(in2);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);
                finish();//also KILL current activity
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("chapters","Log is: " + position);
        switch(position) {
            case 0:
                //chapter 1
                final Intent in2 = new Intent(this, Chapter1S1Intro.class);
                in2.putExtra("id",1);
                startActivity(in2);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);
                break;
            case 1:
                //chapter 2
                final Intent in3 = new Intent(this, Chapter1S1Intro.class);
                in3.putExtra("id",2);
                startActivity(in3);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);
                break;
            case 2:
                //chapter 3
                final Intent in4 = new Intent(this, Chapter1S1Intro.class);
                in4.putExtra("id",3);
                startActivity(in4);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);
                break;
            case 3:
                //chapter 4
                final Intent in5 = new Intent(this, Chapter1S1Intro.class);
                in5.putExtra("id",4);
                startActivity(in5);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);
                break;
            case 4:
                //chapter 5
                final Intent in6 = new Intent(this, Chapter1S1Intro.class);
                in6.putExtra("id",5);
                startActivity(in6);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);
                break;
            case 5:
                //chapter 6
                final Intent in7 = new Intent(this, Chapter1S1Intro.class);
                in7.putExtra("id",6);
                startActivity(in7);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);
                break;
            case 6:
                //chapter 7
                final Intent in8 = new Intent(this, Chapter1S1Intro.class);
                in8.putExtra("id",7);
                startActivity(in8);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);
                break;
            default:
                Log.d("chaptersactivity","Unrecognized position");
        }
    }
}
