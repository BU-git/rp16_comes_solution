package com.bionic.model;


import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "workschedules")
public class WorkSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workScheduleId")
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

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
    public String toString() {
        return "WorkSchedule{" +
                "id=" + id +
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
}
