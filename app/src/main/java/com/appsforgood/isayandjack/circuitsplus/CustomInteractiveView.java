package com.appsforgood.isayandjack.circuitsplus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.appsforgood.isayandjack.circuitsplus.components.Battery;
import com.appsforgood.isayandjack.circuitsplus.components.BlankComponent;
import com.appsforgood.isayandjack.circuitsplus.components.Capacitor;
import com.appsforgood.isayandjack.circuitsplus.components.CircuitSwitch;
import com.appsforgood.isayandjack.circuitsplus.components.Empty;
import com.appsforgood.isayandjack.circuitsplus.components.Fan;
import com.appsforgood.isayandjack.circuitsplus.components.GenericElectricalComponent;
import com.appsforgood.isayandjack.circuitsplus.components.Led;
import com.appsforgood.isayandjack.circuitsplus.components.Resistor;
import com.appsforgood.isayandjack.circuitsplus.components.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Main circuit view for interactive play
 */
public class CustomInteractiveView extends View implements View.OnTouchListener {
     /* PARSE:
      * S - Start (positive terminal)
      * R - Resistor
      * C - Capacitance
      * B - box empty (state machine)
      * EMPTY - skip draw of this component
      * E - End
      * FAN - fan
      * SOUND - sound component
      * LED - light
      * BAT - battery
      * SWITCHON - circuit switch
      * SWITCHOFF - circuit switch off
      * */
    //TODO: Main implementation
    /* Should be generic and will use string to generate both a circuit and a toolbox
     * Circuit ex. : S - R1|200 - C1|200 - B - E (note:
     * Toolbox ex. : R1|200 - C1|200 - FAN
     */

    ArrayList<String> validComponents = new ArrayList<>(); //used for validity checking
    GenericElectricalComponent[] currentComponents = new GenericElectricalComponent[12]; //current string circuit
    GenericElectricalComponent[] currentToolbox = new GenericElectricalComponent[8];
    Boolean okayToDraw = false; //used for validity checking of CircuitActivityInfo

    //scale numbers for bitmaps
    Map<String, Float> scale = new HashMap<String, Float>();

    //translation values for the 14 components
    float[] translationX;
    float[] translationY;

    public float viewWidth=0; //everything based on size of view
    public float viewHeight=0; //everything based on size of view

    //bitmaps
    Bitmap battery, fan, icon, led, wire, capacitor, switchon, switchoff, circuitsplustitle, toolboxtitle, refresh, speaker, blankcomponent, empty, resistor;
    Bitmap back1, back2;

    //tutorials string array
    String[] tutorials;
    int tut=0; //keeps track of current tutorial string, starts at 0

    //hashmap from string to bitmap
    Map<String, Bitmap> component = new HashMap<String, Bitmap>();

    //paint for drawing
    Paint paint = new Paint();

    //variables used for touch identification
    int t=-1;
    int c=-1;
    int killT = -1;

    //voltage and current across tapped components of circuit
    float currV=0;
    float currI=0;

    //circuit propogation updated with this string
    String circuitLiveString;
    String goalString;
    boolean canMoveOn=false;
    //add-on
    float factor = 0.01f;
    boolean firstTimeSound = true;
    //for sounds
    MediaPlayer mp;

    //for saving data
    FileIO files;

    public CustomInteractiveView(Context context, CircuitActivityInfo ci) {
        super(context);

        //load all bitmaps
        battery = BitmapFactory.decodeResource(getResources(), R.drawable.battery);
        fan = BitmapFactory.decodeResource(getResources(), R.drawable.fan);
        icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        led = BitmapFactory.decodeResource(getResources(), R.drawable.led);
        wire = BitmapFactory.decodeResource(getResources(), R.drawable.wire);
        capacitor = BitmapFactory.decodeResource(getResources(), R.drawable.capacitor);
        switchon = BitmapFactory.decodeResource(getResources(), R.drawable.switchclosedtxt);
        switchoff = BitmapFactory.decodeResource(getResources(), R.drawable.switchopentxt);
        blankcomponent = BitmapFactory.decodeResource(getResources(), R.drawable.blankcomponent);
        empty = BitmapFactory.decodeResource(getResources(), R.drawable.empty);
        resistor = BitmapFactory.decodeResource(getResources(), R.drawable.resistor);
        refresh = BitmapFactory.decodeResource(getResources(), R.drawable.refresh);
        speaker = BitmapFactory.decodeResource(getResources(), R.drawable.speaker);

        //load display text
        circuitsplustitle = BitmapFactory.decodeResource(getResources(), R.drawable.circuitsplustitle);
        toolboxtitle = BitmapFactory.decodeResource(getResources(), R.drawable.toolboxtitle);

        //load back buttons
        back1 = BitmapFactory.decodeResource(getResources(), R.drawable.backsettled);
        back2 = BitmapFactory.decodeResource(getResources(), R.drawable.backpressed);

        //add expected components
        //Deprecated since Rev. 1.1: validComponents.add("S");
        //Deprecated since Rev. 1.1: validComponents.add("E");
        validComponents.add("R");
        component.put("R", resistor);
        validComponents.add("C");
        component.put("C", capacitor);
        validComponents.add("B");
        component.put("B", blankcomponent);
        validComponents.add("FAN");
        component.put("FAN", fan);
        validComponents.add("SOUND");
        component.put("SOUND", speaker);
        validComponents.add("LED");
        component.put("LED",led);
        validComponents.add("BAT");
        component.put("BAT", battery);
        validComponents.add("EMPTY");
        component.put("EMPTY",empty);
        validComponents.add("SWITCHON");
        component.put("SWITCHON",switchon);
        validComponents.add("SWITCHOFF");
        component.put("SWITCHOFF",switchoff);

        //set scale numbers for components
        //TODO: scale based on screen size, current assumption is 1000 x 1824
        scale.put("BAT", 0.2f);
        scale.put("R", 0.8f);
        scale.put("C", 0.2f);
        scale.put("B", 1.0f);
        scale.put("FAN", 0.2f);
        scale.put("SOUND", 0.2f);
        scale.put("LED", 2.0f);
        scale.put("EMPTY", 0.2f);
        scale.put("SWITCHON", 0.8f);
        scale.put("SWITCHOFF", 0.8f);
        scale.put("REFRESH",0.18f);

        try {
            if (checkValidity(ci)) {
                okayToDraw = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("INVALID STRING FORMAT");
        }

        //initialize tutorials array from circuitactivityinfo
        tutorials = ci.getTutorial();
        //initialize live circuit
        circuitLiveString = ci.getCircuitString();
        goalString = ci.getDesiredCircuitString();

        //for file IO
        files = new FileIO(getContext().getAssets());

        setOnTouchListener(this);
    }

    private boolean checkValidity(CircuitActivityInfo ci) throws Exception {
        String[] circuitTokens = ci.getCircuitString().split("-");
        String[] toolBoxTokens = ci.getToolboxString().split("-");

        //setup full circuit
        for(int i = 0; i < 12; i ++) {
            switch(circuitTokens[i]){
                case "BAT":
                    //default battery voltage is 8 V
                    currentComponents[i] = new Battery(8);
                    break;
                case "B":
                    currentComponents[i] = new BlankComponent();
                    break;
                case "R":
                    //default resistor ohmage is 20 ohms
                    currentComponents[i] = new Resistor(20);
                    break;
                case "C":
                    //default capacitance is 20 farads
                    currentComponents[i] = new Capacitor(20);
                    break;
                case "FAN":
                    currentComponents[i] = new Fan();
                    break;
                case "SOUND":
                    currentComponents[i] = new Sound();
                    break;
                case "LED":
                    currentComponents[i] = new Led();
                    break;
                case "EMPTY":
                    currentComponents[i] = new Empty();
                    break;
                case "SWITCHON":
                    currentComponents[i] = new CircuitSwitch(true);
                    break;
                case "SWITCHOFF":
                    currentComponents[i] = new CircuitSwitch(false);
                    break;
            }
        }

        //setup full toolbox
        for(int i = 0; i < 8; i ++) {
            switch(toolBoxTokens[i]){
                case "BAT":
                    //default battery voltage is 8 V
                    currentToolbox[i] = new Battery(8);
                    break;
                case "B":
                    currentToolbox[i] = new BlankComponent();
                    break;
                case "R":
                    //default resistor ohmage is 20 ohms
                    currentToolbox[i] = new Resistor(20);
                    break;
                case "C":
                    //default capacitance is 20 farads
                    currentToolbox[i] = new Capacitor(20);
                    break;
                case "FAN":
                    currentToolbox[i] = new Fan();
                    break;
                case "SOUND":
                    currentToolbox[i] = new Sound();
                    break;
                case "LED":
                    currentToolbox[i] = new Led();
                    break;
                case "EMPTY":
                    currentToolbox[i] = new Empty();
                    break;
                case "SWITCHON":
                    currentToolbox[i] = new CircuitSwitch(true);
                    break;
                case "SWITCHOFF":
                    currentToolbox[i] = new CircuitSwitch(false);
                    break;
            }
        }

        boolean valid = false;

        for(int i = 0; i < circuitTokens.length; i ++) {
            for(String s: validComponents) {
                if(s.equals(circuitTokens[i])) {
                    valid = true;
                    break;
                }
            }

            if(valid==false) {
                return false;
            }

            if(valid && i < circuitTokens.length-1) {
                valid = false;
            }
        }

        for(int i = 0; i < toolBoxTokens.length; i ++) {
            for(String s: validComponents) {
                if(s.equals(toolBoxTokens[i])) {
                    valid = true;
                    break;
                }
            }

            if(valid==false) {
                return false;
            }

            if(valid && i < toolBoxTokens.length-1) {
                valid = false;
            }
        }

        return valid;
    }

    protected void onDraw(Canvas canvas) {
        viewWidth = getWidth();
        viewHeight = getHeight();
        if(okayToDraw) {
            float centerx = viewWidth / 2;
            float centery = viewHeight / 2;

            //setup matrix for drawing
            Matrix m = new Matrix();

            //draw display texts
            m.setScale(2,2);
            m.postTranslate(viewWidth*0.5f-circuitsplustitle.getWidth()*2*0.5f, viewHeight*0.03125f-circuitsplustitle.getHeight()*2*0.5f);
            canvas.drawBitmap(circuitsplustitle, m, paint);

            m.setScale(0.6f,0.6f);
            m.postTranslate(viewWidth*0.0025f, viewHeight*0.7508f);
            canvas.drawBitmap(toolboxtitle, m, paint);

            //draw back button and tutorial text
            if(Flags.backFromCircuit) {
                m.setScale(0.1f,0.1f);
                m.postTranslate(0.0f,0.0f);
                canvas.drawBitmap(back2,m,paint);
            } else {
                m.setScale(0.1f,0.1f);
                m.postTranslate(0.0f,0.0f);
                canvas.drawBitmap(back1,m,paint);
            }

            //tutorial + tutorial border
            m.setScale(7.0f, 1.0f);
            m.postTranslate(viewWidth * 0.00525f, viewHeight * 0.0625f + circuitsplustitle.getHeight() * 0.8f);
            if(tut<tutorials.length-1) {
                paint.setAlpha(255);
            } else {
                paint.setAlpha(100);
            }
            canvas.drawBitmap(blankcomponent,m,paint);
            paint.setAlpha(255); //set alpha back to normal

            paint.setTextSize(40);
            canvas.drawText(tutorials[tut], viewWidth*0.04125f, viewHeight*0.0625f + circuitsplustitle.getHeight() * 2.0f, paint);

            //draw refresh button
            m.setScale(scale.get("REFRESH"),scale.get("REFRESH"));
            m.postTranslate(0.00525f * viewWidth + 7.0f * blankcomponent.getWidth(), viewHeight * 0.0625f + circuitsplustitle.getHeight() * 0.8f);
            canvas.drawBitmap(refresh,m,paint);

            //draw finish button
            if(canMoveOn) {
                paint.setAlpha(255);
            } else {
                paint.setAlpha(110);
            }
            m.setScale(-0.1f,0.1f);
            m.postTranslate(viewWidth-back1.getWidth()*0.00f,0.0f);
            canvas.drawBitmap(back1,m,paint);
            paint.setAlpha(255);
            //draw curr V and curr I -> not needed at this point
            //canvas.drawText("V: " + currV, viewWidth-0.125f*viewWidth, viewHeight*0.0625f + circuitsplustitle.getHeight() * 2.0f, paint);
            //canvas.drawText("I: " + currI, viewWidth-0.125f*viewWidth, viewHeight*0.0625f + circuitsplustitle.getHeight() * 2.0f + 0.0625f*viewHeight, paint);

            //draw toolbox separation + circuit
            canvas.drawLine((float)0, (float)3/4 * viewHeight, (float)viewWidth, (float)3/4 * viewHeight, paint);

            canvas.drawLine((float)1/8 * viewWidth, (float)1/4 * viewHeight, (float)7/8 * viewWidth, (float)1/4 * viewHeight, paint);
            canvas.drawLine((float)7/8 * viewWidth, (float)1/4 * viewHeight, (float)7/8 * viewWidth, (float)(0.75 - (float)1/16) * viewHeight, paint);
            canvas.drawLine((float)7/8 * viewWidth, (float)(0.75-(float)1/16)*viewHeight, (float)1/8 * viewWidth, (float)(0.75-(float)1/16) * viewHeight, paint);
            canvas.drawLine((float)1/8 * viewWidth, (float)(0.75-(float)1/16)*viewHeight, (float)1/8 * viewWidth, (float)1/4 * viewHeight, paint);

            //draw main 12 components
            //also set Xs and set Ys for each component
            for(int i = 0; i < 12; i ++) {
                if(currentComponents[i].getComponent().equals("B")) {
                    paint.setAlpha(255);
                } else {
                    paint.setAlpha(currentComponents[i].getAlpha()); //fetch individual transparency
                }
                if(i == 0 || i == 1 || i == 11) {
                    m.setScale(-scale.get(currentComponents[i].getComponent()), scale.get(currentComponents[i].getComponent()));
                    m.postTranslate(currentComponents[i].getOffsetX() + viewWidth * (0.5f - 0.25f * ((i != 11) ? (float) i : -1.0f)) + component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 0.5f, currentComponents[i].getOffsetY() +viewHeight * (0.5f + 0.25f - 0.0625f) - component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent()) * 0.5f);
                    currentComponents[i].setX(viewWidth * (0.5f - 0.25f * ((i != 11) ? (float) i : -1.0f)) + component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 0.5f);
                    currentComponents[i].setY(viewHeight * (0.5f + 0.25f - 0.0625f) - component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent()) * 0.5f);
                    canvas.drawBitmap(component.get(currentComponents[i].getComponent()), m, paint);
                    //m.reset();
                } else if(i >= 2 && i <= 4) {
                    m.setScale(-scale.get(currentComponents[i].getComponent()), scale.get(currentComponents[i].getComponent()));
                    m.postTranslate(currentComponents[i].getOffsetX() + viewWidth * 0.125f + component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent())* 0.5f, currentComponents[i].getOffsetY() +viewHeight * (0.5f + 0.25f - 0.0625f*0.5f - 0.145f * (float)(i-2)) - component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 0.5f);
                    m.postRotate(90,currentComponents[i].getOffsetX() + viewWidth * 0.125f + component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent())* 0.5f, currentComponents[i].getOffsetY() +viewHeight * (0.5f + 0.25f - 0.0625f*0.5f - 0.145f * (float)(i-2)) - component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 0.5f);
                    currentComponents[i].setX(viewWidth * 0.125f + component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent())* 0.5f);
                    currentComponents[i].setY(viewHeight * (0.5f + 0.25f - 0.0625f*0.5f - 0.145f * (float)(i-2)) - component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 0.5f);
                    canvas.drawBitmap(component.get(currentComponents[i].getComponent()), m, paint);
                } else if(i >= 5 && i <= 7) {
                    m.setScale( ((i==6) ? 1.0f : -1.0f) * scale.get(currentComponents[i].getComponent()), scale.get(currentComponents[i].getComponent()));
                    m.postTranslate(currentComponents[i].getOffsetX() + viewWidth * 0.25f * (float)(i-4) + component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * ((i==6) ? -0.5f : 0.5f), currentComponents[i].getOffsetY() +viewHeight * (0.25f) - component.get(currentComponents[i].getComponent()).getHeight() *scale.get(currentComponents[i].getComponent()) * 0.5f);
                    currentComponents[i].setX(viewWidth * 0.25f * (float)(i-4) + component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * ((i==6) ? -0.5f : 0.5f));
                    currentComponents[i].setY(viewHeight * (0.25f) - component.get(currentComponents[i].getComponent()).getHeight() *scale.get(currentComponents[i].getComponent()) * 0.5f);
                    canvas.drawBitmap(component.get(currentComponents[i].getComponent()), m, paint);
                } else if(i >= 8 && i <= 10) {
                    m.setScale(-scale.get(currentComponents[i].getComponent()), scale.get(currentComponents[i].getComponent()));
                    m.postTranslate(currentComponents[i].getOffsetX() + viewWidth-(viewWidth * 0.125f - component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent())* 0.5f), currentComponents[i].getOffsetY() +viewHeight * (0.5f + 0.25f - 0.0625f*0.5f - 0.145f*(float)(10-i)) - component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 0.5f);
                    m.postRotate(90, currentComponents[i].getOffsetX() + viewWidth-(viewWidth * 0.125f - component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent())* 0.5f), currentComponents[i].getOffsetY() +viewHeight * (0.5f + 0.25f - 0.0625f*0.5f - 0.145f*(float)(10-i)) - component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 0.5f);
                    currentComponents[i].setX(viewWidth-(viewWidth * 0.125f - component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent())* 0.5f));
                    currentComponents[i].setY(viewHeight * (0.5f + 0.25f - 0.0625f*0.5f - 0.145f*(float)(10-i)) - component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 0.5f);
                    canvas.drawBitmap(component.get(currentComponents[i].getComponent()), m, paint);
                }
            }

            //draw toolbox
            for(int i = 0; i < 8; i ++) {
                if(currentToolbox[i].getComponent().equals("B")) {
                    paint.setAlpha(255);
                } else {
                    paint.setAlpha(currentToolbox[i].getAlpha());
                }
                m.setScale(scale.get(currentToolbox[i].getComponent()), scale.get(currentToolbox[i].getComponent()));
                m.postTranslate(currentToolbox[i].getOffsetX() + 0.0625f * viewWidth + 0.25f * viewWidth * (float) (i % 4), currentToolbox[i].getOffsetY() +0.800f * viewHeight + 0.1f * viewHeight * (float) (i / 4));
                currentToolbox[i].setX(0.0625f * viewWidth + 0.25f * viewWidth * (float) (i % 4));
                currentToolbox[i].setY(0.800f * viewHeight + 0.1f * viewHeight * (float) (i / 4));
                canvas.drawBitmap(component.get(currentToolbox[i].getComponent()), m, paint); //current toolbox so far doesn't take R|200
            }

            //animate released component
            /*if(killT != -1) {
                float curr = currentToolbox[killT].getOffsetX();
                if(curr > 0) currentToolbox[killT].setOffsetX(curr--);
                curr = currentToolbox[killT].getOffsetY();
                if(curr > 0) currentToolbox[killT].setOffsetY(curr--);

                if(currentToolbox[killT].getOffsetX() <=0 && currentToolbox[killT].getOffsetY() <= 0) {
                    killT = -1;
                }
            }*/

            //update model
            updateLiveString();

            invalidate();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /*if (clickOnBitmap(dog, event)) {
                    Toast.makeText(getContext(), "dog", Toast.LENGTH_SHORT).show();
                } else if (clickOnBitmap(mouse,event)) {
                    Toast.makeText(getContext(), "mouse", Toast.LENGTH_SHORT).show();
                } else if ()*/
                t = getSelectedBitmapTool(event.getX(), event.getY());
                c = getBitmapCircuitComponent(event.getX(), event.getY());
                Log.d("ciruit", "Toolbox #: " + t + " | Circuit #: " + c);

                //handle tapped switches
                if(c!=-1) {
                    if(currentComponents[c].getComponent().equals("SWITCHON")) {
                        mp = MediaPlayer.create(getContext(),R.raw.onswitch);
                        mp.start();
                        firstTimeSound = true;
                        ((CircuitSwitch)currentComponents[c]).setClosed(false);
                        placeComponentIntoString(6, "SWITCHOFF");
                    } else if(currentComponents[c].getComponent().equals("SWITCHOFF")) {
                        mp = MediaPlayer.create(getContext(),R.raw.onswitch);
                        mp.start();
                        firstTimeSound = true;
                        ((CircuitSwitch)currentComponents[c]).setClosed(true);
                        placeComponentIntoString(6, "SWITCHON");
                    }
                }

                //go back to ch1s1intro if back pressed
                if(backTapped(event.getX(), event.getY())) {
                    Activity host = (Activity) getContext();
                    final Intent in = new Intent(host, Chapter1S1Intro.class);
                    in.putExtra("id", host.getIntent().getExtras().getInt("id"));
                    host.startActivity(in);
                    host.overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);
                    host.finish();
                    Log.d("interactive", "Back");
                }

                if(nextTapped(event.getX(), event.getY()) && canMoveOn) {
                    Activity host = (Activity) getContext();
                    final Intent in = new Intent(host, ChaptersActivity.class);
                    in.putExtra("id", host.getIntent().getExtras().getInt("id")+10);
                    host.startActivity(in);
                    host.overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_top);
                    host.finish();
                }

                if(nextTutorialText(event.getX(), event.getY())) {
                    if(tut < tutorials.length-1) tut++;
                    Log.d("interactive", "Tutorial");
                }

                if(refreshTapped(event.getX(), event.getY())) {
                    tut=0;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(t!=-1 && currentToolbox[t].isDraggable()) {
                    currentToolbox[t].setOffsetX(event.getX()- currentToolbox[t].getX() - component.get(currentToolbox[t].getComponent()).getWidth() * scale.get(currentToolbox[t].getComponent())/2);
                    currentToolbox[t].setOffsetY(event.getY()- currentToolbox[t].getY() - component.get(currentToolbox[t].getComponent()).getHeight() * scale.get(currentToolbox[t].getComponent())/2);
                    //Log.d("interactive", "X: " + (currentToolbox[t].getX()) + " Y: " + ( currentToolbox[t].getY()));
                }

                //no draggable circuit components
                /*if(c!=-1 && currentComponents[c].isDraggable()) {
                    currentComponents[c].setOffsetX(event.getX()- currentComponents[c].getX() - component.get(currentComponents[c].getComponent()).getWidth() * scale.get(currentComponents[c].getComponent())/2);
                    currentComponents[c].setOffsetY(event.getY()- currentComponents[c].getY() - component.get(currentComponents[c].getComponent()).getHeight() * scale.get(currentComponents[c].getComponent())/2);
                    //Log.d("interactive", "X: " + (currentToolbox[t].getX()) + " Y: " + ( currentToolbox[t].getY()));
                }*/
                return true;
            case MotionEvent.ACTION_UP:
                Log.d("interactive","ACTION_UP");

                //check if dragged to blank component in circuit or in toolbox
                c = getBitmapCircuitComponent(event.getX(), event.getY());
                if(c!=-1 && t!=-1) {
                    if(currentComponents[c].getComponent().equals("B")) {
                        currentComponents[c].setComponent(currentToolbox[t].getComponent());
                        currentComponents[c].setBrightness(currentToolbox[t].getBrightness());
                        currentComponents[c].setAlpha(currentToolbox[t].getAlpha());
                        //update model
                        placeComponentIntoString(c, currentToolbox[t].getComponent());
                        //then set current toolbox component dragged to blank
                        currentToolbox[t].setComponent("B");
                    } else if(currentToolbox[t].getComponent().equals("B")) {
                        currentToolbox[t].setComponent(currentComponents[c].getComponent());
                        //then set current component to blank
                        currentComponents[c].setComponent("B");
                    }
                }
                //back to original position if release
                if(t!=-1) {
                    currentToolbox[t].setOffsetX(0);
                    currentToolbox[t].setOffsetY(0);
                    //killT = t;
                }
                //back to original position if release
                if(c!=-1) {
                    currentComponents[c].setOffsetX(0);
                    currentComponents[c].setOffsetY(0);
                    //killT = t;
                }

                return true;
            case MotionEvent.ACTION_CANCEL:
                return true;
        }
        return false;
    }

    /**
     * Updates model
     */
    private void updateLiveString() {
        if(goalString.equals(circuitLiveString) && canMoveOn==false) {
            canMoveOn=true;
            mp = MediaPlayer.create(getContext(),R.raw.completedchapter);
            //special case volume chapter
            if(((Activity)getContext()).getIntent().getExtras().getInt("id")==7) {
                mp.setVolume(0.01f,0.01f);
            }
            mp.start();

            //set data
            switch(((Activity)getContext()).getIntent().getExtras().getInt("id")) {
                case 1:
                    DataController.ch1Complete=true;
                    break;
                case 2:
                    DataController.ch2Complete=true;
                    break;
                case 3:
                    DataController.ch3Complete=true;
                    break;
                case 4:
                    DataController.ch4Complete=true;
                    break;
                case 5:
                    DataController.ch5Complete=true;
                    break;
                case 6:
                    DataController.ch6Complete=true;
                    break;
                case 7:
                    DataController.ch7Complete=true;
                    break;
            }
            DataController.save(files);
        }

        float equivI = 0;
        float tV = 0;

        //check if circuit on -> TODO: DELEGATE TO OTHER METHOD
        if(currentComponents[6].getComponent().equals("SWITCHON")) {
            if(numRealComponents(circuitLiveString)!=0) {
                equivI = 8.0f/(20.0f*numRealComponents(circuitLiveString)); //all have same resistance of 20 ohms
            }

            for(int i = 0; i < currentComponents.length; i++) {
                if(currentComponents[i].getComponent().equals("LED")) {
                    tV = equivI * 20.0f - factor;
                    if(tV>0) currentComponents[i].setBrightness(tV/8.0f); //once again, assumed that connected to one 8V battery
                    currentComponents[i].setAlpha((int) (25.0f+230.0f*(currentComponents[i]).getBrightness()));
                }

                if(currentComponents[i].getComponent().equals("C")) {
                    factor+=0.01f;
                    Log.d("interactive","Factor: " +factor);
                }

                if(currentComponents[i].getComponent().equals("SOUND") && firstTimeSound) {
                    mp = MediaPlayer.create(getContext(),R.raw.soundcomponentplay2);
                    float log1 = 0.33f * numRealComponents(circuitLiveString);
                    mp.setVolume(1-log1, 1-log1);
                    mp.start();
                    firstTimeSound = false;
                }
            }
        } else if(currentComponents[6].getComponent().equals("SWITCHOFF")) {
            for(int i = 0; i < currentComponents.length; i++) {
                if(currentComponents[i].getComponent().equals("LED")) {
                    currentComponents[i].setAlpha(25);
                }
            }
        }
    }

    private int numRealComponents(String circuit) {
        String[] components = circuit.split("-");
        int count = 0;
        for(int i = 0; i < components.length; i ++) {
            if(!components[i].equals("B") && !components[i].equals("EMPTY") && !components[i].equals("SWITCHON") && !components[i].equals("SWITCHOFF") && !components[i].equals("BAT") && !components[i].equals("C")) {
                count++;
            }
        }
        return count;
    }

    public void placeComponentIntoString(int index, String component) {
        //split
        String[] components = circuitLiveString.split("-");
        circuitLiveString=""; //"nullify"

        //combine
        for(int i = 0; i < components.length; i++) {
            if(i==index) {
                circuitLiveString += component + "-";
            } else {
                circuitLiveString += components[i] + "-";
            }
        }

        //truncate last dash
        int len = circuitLiveString.length();
        circuitLiveString = circuitLiveString.substring(0,len-1);
        Log.d("interactive","String: " + circuitLiveString);
    }

    /**
     * Get if user tapped back
     * @param x x-pos of click
     * @param y y-pos of click
     * @return index of bitmap in currentToolbox
     */
    private boolean refreshTapped(Float x, Float y) {
        if(x >= 0.00525f * viewWidth + 7.0f * blankcomponent.getWidth()
                && x <= 0.00525f * viewWidth + 7.0f * blankcomponent.getWidth() + refresh.getWidth()*scale.get("REFRESH")
                && y >= viewHeight * 0.0625f + circuitsplustitle.getHeight() * 0.8f
                && y <= viewHeight * 0.0625f + circuitsplustitle.getHeight() * 0.8f + refresh.getHeight()*scale.get("REFRESH")) {
            return true;
        }
        return false;
    }

    /**
     * Get if user tapped back
     * @param x x-pos of click
     * @param y y-pos of click
     * @return index of bitmap in currentToolbox
     */
    private boolean nextTapped(Float x, Float y) {
        if(x >= viewWidth-0.125f*viewWidth
                && x <= viewWidth
                && y >= 0
                && y <= 0.03125f * viewHeight + viewHeight * 0.0625f) {
            return true;
        }
        return false;
    }

    /**
     * Get if user tapped back
     * @param x x-pos of click
     * @param y y-pos of click
     * @return index of bitmap in currentToolbox
     */
    private boolean backTapped(Float x, Float y) {
        if(x >= 0
                && x <= 0.125f * viewWidth
                && y >= 0
                && y <= 0.03125f * viewHeight + viewHeight * 0.0625f) {
            return true;
        }
        return false;
    }
    /**
     * Get if user tapped tutorial text
     * @param x x-pos of click
     * @param y y-pos of click
     * @return index of bitmap in currentToolbox
     */
    private boolean nextTutorialText(Float x, Float y) {
        if(x >= 0
                && x <= 0.75f * viewWidth
                && y >= 0.03125f * viewHeight + circuitsplustitle.getHeight()*2
                && y <= 0.03125f * viewHeight + circuitsplustitle.getHeight()*2 + viewHeight * 0.125f) {
            return true;
        }
        return false;
    }

    //to detect what bitmap was selected in toolbox
    /**
     * Get selected bitmap in toolbox
     * @param x x-pos of click
     * @param y y-pos of click
     * @return index of bitmap in currentToolbox
     */
    private int getSelectedBitmapTool(Float x, Float y) {
        for(int i = 0; i < 8; i ++) {
            if(x >= 0.0625f *viewWidth + 0.25f * viewWidth * (float)(i%4)
               && x <= 0.0625f *viewWidth + 0.25f * viewWidth * (float)(i%4) + component.get(currentToolbox[i].getComponent()).getWidth() * scale.get(currentToolbox[i].getComponent())
               && y >= 0.800f * viewHeight + 0.1f *viewHeight* (float)(i/4)
               && y <= 0.800f * viewHeight + 0.1f *viewHeight* (float)(i/4) + component.get(currentToolbox[i].getComponent()).getHeight() * scale.get(currentToolbox[i].getComponent())) {
               return i;
            }
        }
        //no bitmap click
        return -1;
    }

    /**
     * Get selected bitmap in circuit
     * @param x x-pos of click
     * @param y y-pos of click
     * @return index of bitmap in currentComponents
     */
    private int getBitmapCircuitComponent(Float x, Float y) {
        for(int i = 0; i < 12; i ++) {
            if(i == 0 || i == 1 || i == 11) {
                if(x >= viewWidth * (0.5f-0.25f*( (i!=11) ? (float)i : -1.0f) ) - component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 0.5f
                        && x <= viewWidth * (0.5f-0.25f*( (i!=11) ? (float)i : -1.0f) ) + component.get(currentComponents[i].getComponent()).getWidth()* scale.get(currentComponents[i].getComponent()) * 0.5f + component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 0.5f
                        && y >= viewHeight * (0.5f + 0.25f - 0.0625f) - component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent()) * 0.5f
                        && y <= viewHeight * (0.5f + 0.25f - 0.0625f) - component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent()) * 0.5f + component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent())) {
                    return i;
                }
            } else if(i >= 2 && i <= 4) {
                if(x >= viewWidth * 0.125f - component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent())* 0.5f
                        && x <= viewWidth * 0.125f - component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent())* 0.5f + component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent())
                        && y >= viewHeight * (0.5f + 0.25f - 0.0625f*0.5f - 0.145f * (float)(i-2)) - component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 1.5f
                        && y <= viewHeight * (0.5f + 0.25f - 0.0625f*0.5f - 0.145f * (float)(i-2)) - component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 1.5f + component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent())) {
                    return i;
                }
            } else if(i >= 5 && i <= 7) {
                if(x >= viewWidth * 0.25f * (float)(i-4) - component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 0.5f
                        && x <= viewWidth * 0.25f * (float)(i-4) + component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 0.5f + component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 0.5f
                        && y >= viewHeight * (0.25f) - component.get(currentComponents[i].getComponent()).getHeight() *scale.get(currentComponents[i].getComponent()) * 0.5f
                        && y <= viewHeight * (0.25f) - component.get(currentComponents[i].getComponent()).getHeight() *scale.get(currentComponents[i].getComponent()) * 0.5f + component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent())) {
                    return i;
                }
            } else if(i >= 8 && i <= 10) {
                if(x >= viewWidth-(viewWidth * 0.125f + component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent())* 0.5f)
                        && x <= viewWidth-(viewWidth * 0.125f + component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent())* 0.5f) + component.get(currentComponents[i].getComponent()).getHeight() * scale.get(currentComponents[i].getComponent())
                        && y >= viewHeight * (0.5f + 0.25f - 0.0625f*0.5f - 0.145f*(float)(10-i)) - component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 1.5f
                        && y <= viewHeight * (0.5f + 0.25f - 0.0625f*0.5f - 0.145f*(float)(10-i)) - component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent()) * 1.5f + component.get(currentComponents[i].getComponent()).getWidth() * scale.get(currentComponents[i].getComponent())) {
                    return i;
                }
            }
        }
        //no bitmap click
        return -1;
    }
}
