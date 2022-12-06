package com.sg.superherosightings.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.sql.Date;
import java.util.Objects;

public class Sighting {

    private int id;
    @NotNull(message = "Date Must not be empty")
    @PastOrPresent(message = "Date must not be in the future")
    private Date date;
    private Location location;
    private Hero hero;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sighting)) return false;
        Sighting sighting = (Sighting) o;
        return getId() == sighting.getId() && Objects.equals(getDate(), sighting.getDate()) && Objects.equals(getLocation(), sighting.getLocation()) && Objects.equals(getHero(), sighting.getHero());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate(), getLocation(), getHero());
    }
}
