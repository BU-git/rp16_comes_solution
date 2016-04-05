package com.bionic.model;

import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author Dima Budko
 * v.0.1
 */
@Entity
public class InputShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shiftId")
    private Integer id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="inputShift",fetch = FetchType.EAGER)
    private List<InputRide> inputRides;

    public InputShift() {
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

    public List<InputRide> getInputRides() {
        return inputRides;
    }

    public void setInputRides(List<InputRide> inputRides) {
        this.inputRides = inputRides;
    }
}
