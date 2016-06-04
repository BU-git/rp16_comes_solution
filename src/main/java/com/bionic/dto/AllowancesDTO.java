package com.bionic.dto;

/**
 * author Dima Budko
 */
public class AllowancesDTO {
    private String rides;
    private Integer totalDays;
    private Integer totalTimes;
    private Double allowances;

    public String getRides() {
        return rides;
    }

    public void setRides(String rides) {
        this.rides = rides;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Integer getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(Integer totalTimes) {
        this.totalTimes = totalTimes;
    }

    public Double getAllowances() {
        return allowances;
    }

    public void setAllowances(Double allowances) {
        this.allowances = allowances;
    }
}