package com.bionic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "workschedules")
public class WorkSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workScheduleId")
    private Integer id;

    @JsonIgnore
    private Integer userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date deactivationTime;

    private Integer sunday = 0;
    private Integer monday = 0;
    private Integer tuesday = 0;
    private Integer wednesday = 0;
    private Integer thursday = 0;
    private Integer friday = 0;
    private Integer saturday = 0;

    public WorkSchedule() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() { return userId; }

    public void setUserId(Integer userId) { this.userId = userId; }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Integer getSunday() {
        return sunday;
    }

    public void setSunday(Integer sunday) {
        this.sunday = sunday;
    }

    public Integer getMonday() {
        return monday;
    }

    public void setMonday(Integer monday) {
        this.monday = monday;
    }

    public Integer getTuesday() {
        return tuesday;
    }

    public void setTuesday(Integer tuesday) {
        this.tuesday = tuesday;
    }

    public Integer getWednesday() {
        return wednesday;
    }

    public void setWednesday(Integer wednesday) {
        this.wednesday = wednesday;
    }

    public Integer getThursday() {
        return thursday;
    }

    public void setThursday(Integer thurthday) {
        this.thursday = thurthday;
    }

    public Integer getFriday() {
        return friday;
    }

    public void setFriday(Integer friday) {
        this.friday = friday;
    }

    public Integer getSaturday() {
        return saturday;
    }

    public void setSaturday(Integer saturday) {
        this.saturday = saturday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkSchedule that = (WorkSchedule) o;

        if (sunday != null ? !sunday.equals(that.sunday) : that.sunday != null) return false;
        if (monday != null ? !monday.equals(that.monday) : that.monday != null) return false;
        if (tuesday != null ? !tuesday.equals(that.tuesday) : that.tuesday != null) return false;
        if (wednesday != null ? !wednesday.equals(that.wednesday) : that.wednesday != null) return false;
        if (thursday != null ? !thursday.equals(that.thursday) : that.thursday != null) return false;
        if (friday != null ? !friday.equals(that.friday) : that.friday != null) return false;
        if (saturday != null ? !saturday.equals(that.saturday) : that.saturday != null) return false;

        return true;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (sunday != null ? sunday.hashCode() : 0);
        result = 31 * result + (monday != null ? monday.hashCode() : 0);
        result = 31 * result + (tuesday != null ? tuesday.hashCode() : 0);
        result = 31 * result + (wednesday != null ? wednesday.hashCode() : 0);
        result = 31 * result + (thursday != null ? thursday.hashCode() : 0);
        result = 31 * result + (friday != null ? friday.hashCode() : 0);
        result = 31 * result + (saturday != null ? saturday.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WorkSchedule{" +
                "id=" + id +
                ", userId=" + userId +
                ", creationTime=" + creationTime +
                ", sunday='" + sunday + '\'' +
                ", monday='" + monday + '\'' +
                ", tuesday='" + tuesday + '\'' +
                ", wednesday='" + wednesday + '\'' +
                ", thursday='" + thursday + '\'' +
                ", friday='" + friday + '\'' +
                ", saturday='" + saturday + '\'' +
                '}';
    }

    public Date getDeactivationTime() {
        return deactivationTime;
    }

    public void setDeactivationTime(Date deactivationTime) {
        this.deactivationTime = deactivationTime;
    }
}
