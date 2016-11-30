package com.appsforgood.isayandjack.circuitsplus.components;

/**
 * Deprecated as of Rev. 1.1 | component naming system now removes the need of any component machine
 */
//blank component will change according to the state of the component machine
public enum ComponentMachine {
    capacitor, resistor, inductor, fan, battery, led, sound;

    /* Units:
    * For resistors -> ohms
    * For capacitors -> farads
    * For inductors -> henrys
    * For batteries -> volts
    * */
    double val = 0; //default component value, meaning no value

    ComponentMachine() {
        val = 0;
    }

    ComponentMachine(double value) {
        val = value;
    }
}
