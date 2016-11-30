package com.appsforgood.isayandjack.circuitsplus.components;

/**
 * This class defines the blank component, which allows users to drag items into the circuit
 */
public class BlankComponent extends GenericElectricalComponent {
    private float brightness; //brightness, NOT in lux; measure used is simply scale from 0 to 1

    public BlankComponent() {
        brightness = 1;
        setComponent("B");
        setDraggable(false);
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }
}
