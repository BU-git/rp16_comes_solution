package com.bionic.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * author Dima Budko
 * v.0.1
 */
@Entity
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

    @ManyToOne(cascade = CascadeType.PERSIST)
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
}
