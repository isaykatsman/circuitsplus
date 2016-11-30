package com.appsforgood.isayandjack.circuitsplus.components;

/**
 * Model class for a sound component
 */
public class Sound extends GenericElectricalComponent {
    private float volume; //volume from 0 to 1

    public Sound() {
        volume = 0;
        setComponent("SOUND");
    }

    public Sound(int volume) {
        this.volume = volume;
        setComponent("SOUND");
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
