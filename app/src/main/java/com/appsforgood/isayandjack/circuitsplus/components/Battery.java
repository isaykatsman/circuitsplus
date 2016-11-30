package com.appsforgood.isayandjack.circuitsplus.components;

/**
 * Model class for a battery
 */
public class Battery extends GenericElectricalComponent {
    private int voltage;

    public Battery(int voltage) {
        this.voltage = voltage;
        setComponent("BAT");
    }
    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }
}
