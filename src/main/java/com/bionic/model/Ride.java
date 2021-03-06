package com.bionic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * author Dima Budko
 * v.0.1
 */
@Entity
@Table(name = "rides")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rideId")
    private Integer id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "shiftId")
    private Shift shift;

    public Ride() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public boolean equals(Object obj){
        if (this == obj) return true;
        Ride ride = (Ride) obj;
        if (this.getId().equals(ride.getId())) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", shift=" + shift +
                '}';
    }
}
