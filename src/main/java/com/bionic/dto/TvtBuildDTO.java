package com.bionic.dto;

/**
 * @author Pavel Boiko
 */
public class TvtBuildDTO {

    private String periodName = "Period";
    private int fromWeek = 0;
    private int untilWeek = 0;
    private double aboveVoluntary = 0.0;
    private double aboveMandatory = 0.0;
    private double build = 0.0;
    private double used = 0.0;
    private double left = 0.0;

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public int getFromWeek() {
        return fromWeek;
    }

    public void setFromWeek(int fromWeek) {
        this.fromWeek = fromWeek;
    }

    public int getUntilWeek() {
        return untilWeek;
    }

    public void setUntilWeek(int untilWeek) {
        this.untilWeek = untilWeek;
    }

    public double getAboveVoluntary() {
        return aboveVoluntary;
    }

    public void setAboveVoluntary(double aboveVoluntary) {
        this.aboveVoluntary = aboveVoluntary;
    }

    public double getAboveMandatory() {
        return aboveMandatory;
    }

    public void setAboveMandatory(double aboveMandatory) {
        this.aboveMandatory = aboveMandatory;
    }

    public double getBuild() {
        return build;
    }

    public void setBuild(double build) {
        this.build = build;
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }
}
