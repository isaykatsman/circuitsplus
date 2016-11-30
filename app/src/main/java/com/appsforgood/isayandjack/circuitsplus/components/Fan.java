package com.appsforgood.isayandjack.circuitsplus.components;

/**
 * Model class for a Fan
 */
public class Fan extends GenericElectricalComponent {
    private float rateSpin; //spin rate in rotations per second
    private boolean isSpinning; //boolean flag to control component

    public Fan() {
        rateSpin = 0;
        isSpinning = false;
        setComponent("FAN");
    }

    public Fan(float rS, boolean spin) {
        rateSpin = rS;
        isSpinning = spin;
        setComponent("FAN");
    }

    public float getRateSpin() {
        return rateSpin;
    }

    public void setRateSpin(float rateSpin) {
        this.rateSpin = rateSpin;
    }

    public boolean isSpinning() {
        return isSpinning;
    }

    public void setSpinning(boolean isSpinning) {
        this.isSpinning = isSpinning;
    }
}
