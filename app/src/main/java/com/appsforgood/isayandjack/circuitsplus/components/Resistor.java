package com.appsforgood.isayandjack.circuitsplus.components;

/**
 * Model class for a resistor
 */
public class Resistor extends GenericElectricalComponent {
    private double resistance;
    static int count;

    public Resistor(double resistance) {
        setName("Resistor");
        setDesc("Resistor w/ R = " + resistance + " | ID: " + (count++));
        this.resistance = resistance;
        setComponent("R");
    }

    public double getResistance() {
        return resistance;
    }

    public void setResistance(double r) {
        resistance = r;
    }
}
