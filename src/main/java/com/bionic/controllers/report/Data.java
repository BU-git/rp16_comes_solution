package com.bionic.controllers.report;

/**
 * author Dima Budko
 */
public class Data {
    private String rides;
    private Integer totalDays;
    private Integer totalTimes;
    private Double allowences;

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

    public Double getAllowences() {
        return allowences;
    }

    public void setAllowences(Double allowences) {
        this.allowences = allowences;
    }
}