package com.sg.superherosightings.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class Superpower {

    private int id;
    @NotBlank(message = "Name must not be empty")
    @Size(max = 50, message = "Name must not be longer than 50 characters")
    private String name;
    @NotBlank(message = "Description must not be empty")
    @Size(max = 200, message = "Description must not be longer than 200 characters")
    private String description;

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

    @Override
    public String toString() {
        return "Superpower{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Superpower)) return false;
        Superpower that = (Superpower) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
