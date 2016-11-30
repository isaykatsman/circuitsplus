package com.appsforgood.isayandjack.circuitsplus.components;

/**
 * Model class for a capacitor
 */
public class Capacitor extends GenericElectricalComponent {
    private double capacitance;
    static int count;

    public Capacitor(double capacitance) {
        setName("Capacitor");
        setDesc("Capacitor w/ C = " + capacitance + " | ID: " + (count++));
        setComponent("C");
    }

    public double getCapacitance() {
        return capacitance;
    }

    public void setCapacitance(double c) {
        capacitance = c;
    }
}
