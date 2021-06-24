package com.hackerrank.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Weather {

    @Id
    private Long id;

    @JsonProperty("date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    @ElementCollection(targetClass = Float.class)
    private List<Float> temperature;

    public Weather() {
    }

    public Weather(Long id, Date date, Location location, List<Float> temperature) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date dateRecorded) {
        this.date = dateRecorded;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Float> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<Float> temperature) {
        this.temperature = temperature;
    }
}
