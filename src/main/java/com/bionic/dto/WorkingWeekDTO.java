package com.bionic.dto;

import com.bionic.model.Shift;

import java.util.Set;

/**
 * @author Pavel Boiko
 */
public class WorkingWeekDTO {

    private int weekNumber;

    private int workedTime;

    private int overTime;

    private Set<Shift> shiftList;

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getWorkedTime() {
        return workedTime;
    }

    public void setWorkedTime(int workedTime) {
        this.workedTime = workedTime;
    }

    public int getOverTime() {
        return overTime;
    }

    public void setOverTime(int overTime) {
        this.overTime = overTime;
    }

    public Set<Shift> getShiftList() { return shiftList; }

    public void setShiftList(Set<Shift> shiftList) { this.shiftList = shiftList; }

    public String toString() {
        return "weekNumber = " + weekNumber + ", workedTime = " +
                workedTime + ", overTime = " + overTime + ", shiftList = " + shiftList;
    }
}
