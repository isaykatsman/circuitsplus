package com.appsforgood.isayandjack.circuitsplus;

/**
 * Provides all information needed for generic chapter instantiation
 */
public class ChaptersInfo {
    public static CircuitActivityInfo ci1 = new CircuitActivityInfo(
            "A circuit in its most basic form is a connection of multiple electric components. " +
                    "Common components that you may know of are things like batteries or light bulbs. " +
                    "In this activity, you will use a battery to turn on a light.",
            new String[] {"Tap this text to continue...",
                          "Welcome to Circuits+!",
                          "This is a tutorial",
                          "Drag a LED from the toolbox onto the circuit",
                          "Now tap the switch",
                          "Notice that the LED lights up",
                          "...because we just let current from the",
                          "battery pass through it",
                          "Tap the top right button to continue..."},
            "BAT-EMPTY-EMPTY-EMPTY-B-EMPTY-SWITCHOFF-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY",
            "LED-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY",
            "BAT-EMPTY-EMPTY-EMPTY-LED-EMPTY-SWITCHON-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY"
    );

    public static CircuitActivityInfo ci2 = new CircuitActivityInfo(
            "It is important to understand that current, unless stopped, will flow through the wire " +
                    "until it comes around into the opposite terminal of the battery and will " +
                    "power all components in the circuit while doing so. By convention, current flows from " +
                    "the positive to negative terminals on the battery.",
            new String[] {"Tap this text to continue...",
                          "Try to light up all the LEDs",
                          "Then tap the top right button"},
            "BAT-EMPTY-EMPTY-EMPTY-B-B-SWITCHOFF-B-B-EMPTY-EMPTY-EMPTY",
            "LED-LED-LED-LED-EMPTY-EMPTY-EMPTY-EMPTY",
            "BAT-EMPTY-EMPTY-EMPTY-LED-LED-SWITCHON-LED-LED-EMPTY-EMPTY-EMPTY"
    );

    public static CircuitActivityInfo ci3 = new CircuitActivityInfo(
            "Resistors are electrical components that resist current. Thus, the wire after a resistor " +
                    "experiences a drop in voltage. This will cause all components placed after the " +
                    "resistor to function with LESS power. Previously you have already seen how " +
                    "the internal resistance of components causes them to glow softer.",
            new String[] {"Tap this text to continue...",
                          "Set-up a circuit in which:",
                          "A LED glows with less brightness than it",
                          "normally would"},
            "BAT-EMPTY-EMPTY-B-EMPTY-B-SWITCHOF F-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY",
            "LED-R-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY",
            "BAT-EMPTY-EMPTY-R-EMPTY-LED-SWITCHON-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY"
    );

    public static CircuitActivityInfo ci4 = new CircuitActivityInfo(
            "Resistors affect the voltage of all components placed afterwards.",
            new String[] {"Tap this text to continue...",
                          "Set-up a circuit in which the two",
                          "LEDs glow with decreased brightness"},
            "BAT-B-EMPTY-EMPTY-EMPTY-EMPTY-SWITCHOFF-B-EMPTY-B-EMPTY-EMPTY",
            "LED-LED-R-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY",
            "BAT-R-EMPTY-EMPTY-EMPTY-EMPTY-SWITCHON-LED-EMPTY-LED-EMPTY-EMPTY"
    );

    public static CircuitActivityInfo ci5 = new CircuitActivityInfo(
            "When multiple resistors are placed one after another, the voltage in the " +
                    "circuit drops after each resistor",
            new String[] {"Tap this text to continue...",
                          "Make the LED glow with",
                          "minimal brightness..."},
            "BAT-B-B-B-EMPTY-EMPTY-SWITCHOFF-B-EMPTY-EMPTY-EMPTY-EMPTY",
            "R-LED-R-R-EMPTY-EMPTY-EMPTY-EMPTY",
            "BAT-R-R-R-EMPTY-EMPTY-SWITCHON-LED-EMPTY-EMPTY-EMPTY-EMPTY"
    );

    public static CircuitActivityInfo ci6 = new CircuitActivityInfo(
            "A capacitor is like a battery that can charge and discharge very quickly. When fully charged " +
                    "current no longer passes through the capacitor, and thus, current stops passing through the circuit (" +
                    "which is now broken as a result of the charged capacitor).",
            new String[] {"Tap this text to continue...",
                          "Set-up a circuit in which the",
                          "The LED glows at a rapidly decreasing",
                          "brightness as a result of the",
                          "capacitor"},
            "BAT-EMPTY-B-EMPTY-EMPTY-B-SWITCHOFF-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY",
            "C-LED-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY",
            "BAT-EMPTY-C-EMPTY-EMPTY-LED-SWITCHON-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY"
    );

    public static CircuitActivityInfo ci7 = new CircuitActivityInfo(
            "A sound component will play sound when current flows through it.",
            new String[] {"Tap this text to continue...",
                          "Make the sound component",
                          "Produce sound"},
            "BAT-EMPTY-EMPTY-B-EMPTY-EMPTY-SWITCHOFF-EMPTY-B-EMPTY-EMPTY-EMPTY",
            "R-SOUND-LED-EMPTY-EMPTY-EMPTY-EMPTY-EMPTY",
            "BAT-EMPTY-EMPTY-R-EMPTY-EMPTY-SWITCHON-EMPTY-SOUND-EMPTY-EMPTY-EMPTY"
    );

    public static CircuitActivityInfo getCircuitInfo(int id) {
        switch(id) {
            case 1:
                return ci1;
            case 2:
                return ci2;
            case 3:
                return ci3;
            case 4:
                return ci4;
            case 5:
                return ci5;
            case 6:
                return ci6;
            case 7:
                return ci7;
        }
        //no id matches
        return null;
    }
}

