package com.sg.superherosightings.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Organization {

    private int id;
    @NotBlank(message = "Name must not be empty")
    @Size(max = 30, message = "Name must not be longer than 30 characters")
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
    @Size(max = 30, message = "State must not be longer than 30 characters")
    private String state;
    @NotBlank(message = "Zip Code must not be empty")
    @Size(min = 5, max = 5, message = "Zip Code must be 5 characters")
    private String zip;
    @NotBlank(message = "Phone must not be empty")
    @Size(min = 10, max = 10, message = "Phone must be 10 characters")
    private String phone;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(address, that.address) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(zip, that.zip) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, address, city, state, zip, phone);
    }
}
