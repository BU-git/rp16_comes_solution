package com.bionic.dto;

import com.bionic.model.Shift;

import java.util.Set;

/**
 * @author Pavel Boiko
 */
public class WorkingWeekDTO {

    private int weekNumber;

    private int contractTime;

    private int overTime;

    private Set<Shift> shiftList;

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getContractTime() {
        return contractTime;
    }

    public void setContractTime(int contractTime) {
        this.contractTime = contractTime;
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
        return "weekNumber = " + weekNumber + ", contractTime = " +
                contractTime + ", overTime = " + overTime + ", shiftList = " + shiftList;
    }
}
