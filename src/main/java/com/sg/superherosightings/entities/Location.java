package com.sg.superherosightings.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Location {

    private int id;
    @NotBlank(message = "Name must not be empty")
    @Size(max = 50, message = "Name must not be longer than 50 characters")
    private String name;
    @NotBlank(message = "Description must not be empty")
    @Size(max = 200, message = "Description must not be longer than 200 characters")
    private String description;
    @NotBlank(message = "Address must not be empty")
    @Size(max = 50, message = "Address must not be longer than 50 characters")
    private String address;
    @NotBlank(message = "City must not be empty")
    @Size(max = 30, message = "City must not be longer than 30 characters")
    private String city;
    @NotBlank(message = "State must not be empty")
    @Size(max = 30, message = "State must not be longer than 300 characters")
    private String state;
    @NotBlank(message = "Zip Code must not be empty")
    @Size(min = 5, max = 5, message = "Zip must be 5 characters")
    private String zip;
    @NotBlank(message = "Longitude must not be empty")
    @Size(max = 30, message = "Longitude must not be longer than 30 characters")
    private String longitude;
    @NotBlank(message = "Latitude must not be empty")
    @Size(max = 30, message = "Latitude must not be longer than 30 characters")
    private String latitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return getId() == location.getId() && Objects.equals(getName(), location.getName()) && Objects.equals(getDescription(), location.getDescription()) && Objects.equals(getAddress(), location.getAddress()) && Objects.equals(getCity(), location.getCity()) && Objects.equals(getState(), location.getState()) && Objects.equals(getZip(), location.getZip()) && Objects.equals(getLongitude(), location.getLongitude()) && Objects.equals(getLatitude(), location.getLatitude());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getAddress(), getCity(), getState(), getZip(), getLongitude(), getLatitude());
    }
}
