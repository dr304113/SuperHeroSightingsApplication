package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.Hero;
import com.sg.superherosightings.entities.Superpower;

import java.util.List;

public interface SuperpowerDao {

    Superpower addSuperpower(Superpower superpower);
    List<Superpower> getAllSuperpowers();
    Superpower getSuperpowerById(int id);
    void updateSuperpower(Superpower superpower);
    void deleteSuperpowerById(int id);

}
