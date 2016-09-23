/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrum.table.Main;

/**
 *
 * @author leandro.lima
 */
public class TestModel {

    private double elementCurrent;

    private double elementVoltage;

    private Elements elements;

    private double powerFactor;

    private double phaseFromA;

    private boolean isReactive;

    private boolean isInductive;

    private boolean isReverse;

    public TestModel() {
        this.elementCurrent = 0;
        this.elementVoltage = 0;
        this.elements = Elements.ABC;
        this.powerFactor = 1;
        this.phaseFromA = 0;
        this.isReactive = false;
        this.isInductive = false;
        this.isReverse = false;
    }

    public TestModel(double elementCurrent, double elementVoltage, Elements elements,
            double powerFactor, double phaseFromA, boolean isReactive,
            boolean isInductive, boolean isReverse) {
        this.elementCurrent = elementCurrent;
        this.elementVoltage = elementVoltage;
        this.elements = elements;
        this.powerFactor = powerFactor;
        this.phaseFromA = phaseFromA;
        this.isReactive = isReactive;
        this.isInductive = isInductive;
        this.isReverse = isReverse;
    }

    public double getElementCurrent() {
        return elementCurrent;
    }

    public void setElementCurrent(double elementCurrent) {
        this.elementCurrent = elementCurrent;
    }

    public double getElementVoltage() {
        return elementVoltage;
    }

    public void setElementVoltage(double elementVoltage) {
        this.elementVoltage = elementVoltage;
    }

    public Elements getElements() {
        return elements;
    }

    public void setElements(Elements elements) {
        this.elements = elements;
    }

    public double getPowerFactor() {
        return powerFactor;
    }

    public void setPowerFactor(double powerFactor) {
        this.powerFactor = powerFactor;
    }

    public double getPhaseFromA() {
        return phaseFromA;
    }

    public void setPhaseFromA(double phaseFromA) {
        this.phaseFromA = phaseFromA;
    }

    public boolean isIsReactive() {
        return isReactive;
    }

    public void setIsReactive(boolean isReactive) {
        this.isReactive = isReactive;
    }

    public boolean isIsInductive() {
        return isInductive;
    }

    public void setIsInductive(boolean isInductive) {
        this.isInductive = isInductive;
    }

    public boolean isIsReverse() {
        return isReverse;
    }

    public void setIsReverse(boolean isReverse) {
        this.isReverse = isReverse;
    }

}
