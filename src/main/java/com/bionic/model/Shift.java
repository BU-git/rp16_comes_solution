package com.bionic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * author Dima Budko
 * v.0.1
 */
@Entity
public class Shift {
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

    @JsonIgnore
//    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "shift", fetch = FetchType.EAGER)
    private List<Ride> rides;

    public Shift() {
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

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
