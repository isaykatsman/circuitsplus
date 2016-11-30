package com.appsforgood.isayandjack.circuitsplus.components;

/**
 * Model class for a LED
 */
public class Led extends GenericElectricalComponent {
    private float brightness; //brightness, NOT in lux; measure used is simply scale from 0 to 1

    public Led() {
        brightness = 1;
        setComponent("LED");
        setAlpha(25);
    }

    public Led(float brightness) {
        this.brightness = brightness;
        setComponent("LED");
        setAlpha(25);
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }
}
