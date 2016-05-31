package com.bionic.model;

import com.bionic.model.dict.DayTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Forsent on 28.05.2016.
 */
@Entity
@Table(name = "day_types")
public class DayType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dayTypeId")
    private Integer id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "dayTypeName")
    @Enumerated(EnumType.STRING)
    private DayTypeEnum dayTypeName;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DayTypeEnum getDayTypeName() {
        return dayTypeName;
    }

    public void setDayTypeName(DayTypeEnum dayTypeName) {
        this.dayTypeName = dayTypeName;
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


}
