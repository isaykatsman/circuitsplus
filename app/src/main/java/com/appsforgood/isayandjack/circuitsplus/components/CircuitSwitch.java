package com.appsforgood.isayandjack.circuitsplus.components;

/**
 * Model class for a switch that allows users to turn circuits on and off
 */
public class CircuitSwitch extends GenericElectricalComponent {
    private boolean closed; //whether switch is closed or not

    public CircuitSwitch() {
        closed = false;
        setComponent("SWITCHOFF");
    }

    public CircuitSwitch(boolean closed) {
        this.closed = closed;
        if(closed) {
            setComponent("SWITCHON");
        } else {
            setComponent("SWITCHOFF");
        }
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
        if(closed) {
            setComponent("SWITCHON");
        } else {
            setComponent("SWITCHOFF");
        }
    }
}
