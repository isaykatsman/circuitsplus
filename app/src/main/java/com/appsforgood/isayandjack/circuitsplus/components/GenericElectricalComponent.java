package com.appsforgood.isayandjack.circuitsplus.components;

/**
 * Model class for a generic electrical component
 */
public abstract class GenericElectricalComponent {
    private String name;
    private String component="EMPTY"; //official component NAME ("R", "C", "BAT", etc.)
    private String description;
    private float offsetX=0.0f; //used for drag events
    private float offsetY=0.0f; //used for drag events
    private float X=0.0f;
    private float Y=0.0f;
    private boolean draggable = true; //used for user interaction
    private int alpha = 255; //fully visible by default
    private float brightness;

    //accessors for name
    public String getName() {
        return name;
    }
    public void setName(String n) {
        name = n;
    }

    //accessors for description
    public String getDesc() {
        return description;
    }
    public void setDesc(String n) {
        description = n;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }
}
