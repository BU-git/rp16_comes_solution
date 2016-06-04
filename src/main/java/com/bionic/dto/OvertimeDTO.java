package com.bionic.dto;

import java.util.Date;

public class OvertimeDTO {

    private int weekOfYear;
    private Date startTime;
    private Date endTime;
    private double totalHours = 0.0;
    private double paid100 = 0.0;
    private double paid130 = 0.0;
    private double paid150 = 0.0;
    private double paid200 = 0.0;
    private double waitingdayHours = 0.0;
    private double sickdayHours = 0.0;
    private double holidayHours = 0.0;
    private double atvHours = 0.0;
    private double paidLeaveHours = 0.0;
    private double unpaidLeaveHours = 0.0;

    public int getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public double getPaid100() {
        return paid100;
    }

    public void setPaid100(double paid100) {
        this.paid100 = paid100;
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

    public double getPaid200() {
        return paid200;
    }

    public void setPaid200(double paid200) {
        this.paid200 = paid200;
    }

    public double getWaitingdayHours() {
        return waitingdayHours;
    }

    public void setWaitingdayHours(double waitingdayHours) {
        this.waitingdayHours = waitingdayHours;
    }

    public double getSickdayHours() {
        return sickdayHours;
    }

    public void setSickdayHours(double sickdayHours) {
        this.sickdayHours = sickdayHours;
    }

    public double getHolidayHours() {
        return holidayHours;
    }

    public void setHolidayHours(double holidayHours) {
        this.holidayHours = holidayHours;
    }

    public double getAtvHours() {
        return atvHours;
    }

    public void setAtvHours(double atvHours) {
        this.atvHours = atvHours;
    }

    public double getPaidLeaveHours() {
        return paidLeaveHours;
    }

    public void setPaidLeaveHours(double paidLeaveHours) {
        this.paidLeaveHours = paidLeaveHours;
    }

    public double getUnpaidLeaveHours() {
        return unpaidLeaveHours;
    }

    public void setUnpaidLeaveHours(double unpaidLeaveHours) {
        this.unpaidLeaveHours = unpaidLeaveHours;
    }

    @Override
    public String toString() {
        return "OvertimeDTO{" +
                "weekOfYear=" + weekOfYear +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalHours=" + totalHours +
                ", paid100=" + paid100 +
                ", paid130=" + paid130 +
                ", paid150=" + paid150 +
                ", paid200=" + paid200 +
                ", waitingdayHours=" + waitingdayHours +
                ", sickdayHours=" + sickdayHours +
                ", holidayHours=" + holidayHours +
                ", atvHours=" + atvHours +
                ", paidLeaveHours=" + paidLeaveHours +
                ", unpaidLeaveHours=" + unpaidLeaveHours +
                '}';
    }
}

