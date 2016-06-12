package com.bionic.dto;

/**
 * @author Pavel Boiko
 */
public class TvtPaidDTO {

    private String periodName = "Period";
    private int fromWeek = 0;
    private int untilWeek = 0;
    private double aboveVoluntary = 0.0;
    private double aboveMandatory = 0.0;
    private double paid130 = 0.0;
    private double paid150 = 0.0;

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

    public double getPaid130() {
        return paid130;
    }

    public void setPaid130(double paid130) {
        this.paid130 = paid130;
    }

    public double getPaid150() {
        return paid150;
    }

    public void setPaid150(double paid150) {
        this.paid150 = paid150;
    }
}
