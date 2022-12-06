package com.sg.superherosightings.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class Hero {

    private int id;
    @NotBlank(message = "Name must not be empty")
    @Size(max = 50, message = "Name must not be longer than 50 characters")
    private String name;
    @NotBlank(message = "Description must not be empty")
    @Size(max = 200, message = "Description must not be longer than 200 characters")
    private String description;
    @NotEmpty(message = "Must Include at least one superpower")
    private List<Superpower> superpowers;
    @NotEmpty(message = "Must Include at least one organization")
    private List<Organization> organizations;


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

    public List<Superpower> getSuperpowers() {
        return superpowers;
    }

    public void setSuperpowers(List<Superpower> superpowers) {
        this.superpowers = superpowers;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", superpowers=" + superpowers +
                ", organizations=" + organizations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hero)) return false;
        Hero hero = (Hero) o;
        return getId() == hero.getId() && Objects.equals(getName(), hero.getName()) && Objects.equals(getDescription(), hero.getDescription()) && Objects.equals(getSuperpowers(), hero.getSuperpowers()) && Objects.equals(getOrganizations(), hero.getOrganizations());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getSuperpowers(), getOrganizations());
    }
}
