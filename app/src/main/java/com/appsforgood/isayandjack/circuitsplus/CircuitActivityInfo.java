package com.appsforgood.isayandjack.circuitsplus;

/**
 * Set of information needed to instantiate a new circuit chapter
 */
public class CircuitActivityInfo {
    private String initialDescription;
    private String[] tutorial;
    private String circuitString;
    private String toolboxString;
    private String desiredCircuitString;

    public CircuitActivityInfo() {
    }

    public CircuitActivityInfo(String initDesc, String[] tutorial, String circuit, String toolbox, String desiredCircuitString) {
        initialDescription = initDesc;
        this.tutorial = tutorial;
        circuitString = circuit;
        toolboxString = toolbox;
        this.desiredCircuitString = desiredCircuitString;
    }

    public String getCircuitString() {
        return circuitString;
    }

    public void setCircuitString(String circuitString) {
        this.circuitString = circuitString;
    }

    public String getToolboxString() {
        return toolboxString;
    }

    public void setToolboxString(String toolboxString) {
        this.toolboxString = toolboxString;
    }

    public String getInitialDescription() {
        return initialDescription;
    }

    public void setInitialDescription(String initialDescription) {
        this.initialDescription = initialDescription;
    }

    public String[] getTutorial() {
        return tutorial;
    }

    public void setTutorial(String[] tutorial) {
        this.tutorial = tutorial;
    }

    public String getDesiredCircuitString() {
        return desiredCircuitString;
    }

    public void setDesiredCircuitString(String desiredCircuitString) {
        this.desiredCircuitString = desiredCircuitString;
    }
}
