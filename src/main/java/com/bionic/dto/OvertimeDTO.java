package com.bionic.dto;

import java.util.Date;

public class OvertimeDTO {
    private int number;
    private Date fromDate;
    private Date toDate;
    private int total;
    private int paid100;
    private int paid130;
    private int paid150;
    private int paid200;
    private int waitingdayHours;
    private int sickdayHours;
    private int holidayHours;
    private int atvHours;
    private int paidLeaveHours;
    private int unpaidLeaveHours;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPaid100() {
        return paid100;
    }

    public void setPaid100(int paid100) {
        this.paid100 = paid100;
    }

    public int getPaid130() {
        return paid130;
    }

    public void setPaid130(int paid130) {
        this.paid130 = paid130;
    }

    public int getPaid150() {
        return paid150;
    }

    public void setPaid150(int paid150) {
        this.paid150 = paid150;
    }

    public int getPaid200() {
        return paid200;
    }

    public void setPaid200(int paid200) {
        this.paid200 = paid200;
    }

    public int getWaitingdayHours() {
        return waitingdayHours;
    }

    public void setWaitingdayHours(int waitingdayHours) {
        this.waitingdayHours = waitingdayHours;
    }

    public int getSickdayHours() {
        return sickdayHours;
    }

    public void setSickdayHours(int sickdayHours) {
        this.sickdayHours = sickdayHours;
    }

    public int getHolidayHours() {
        return holidayHours;
    }

    public void setHolidayHours(int holidayHours) {
        this.holidayHours = holidayHours;
    }

    public int getAtvHours() {
        return atvHours;
    }

    public void setAtvHours(int atvHours) {
        this.atvHours = atvHours;
    }

    public int getPaidLeaveHours() {
        return paidLeaveHours;
    }

    public void setPaidLeaveHours(int paidLeaveHours) {
        this.paidLeaveHours = paidLeaveHours;
    }

    public int getUnpaidLeaveHours() {
        return unpaidLeaveHours;
    }

    public void setUnpaidLeaveHours(int unpaidLeaveHours) {
        this.unpaidLeaveHours = unpaidLeaveHours;
    }


}
