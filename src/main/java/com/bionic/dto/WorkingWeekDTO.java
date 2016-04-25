package com.bionic.dto;

/**
 * @author Pavel Boiko
 */
public class WorkingWeekDTO {

    private int weekNumber;

    private int contractTime;

    private int overTime;

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

    public String toString() {
        return "weekNumber = " + weekNumber + ", contractTime = " +
                contractTime + ", overTime = " + overTime;
    }
}
