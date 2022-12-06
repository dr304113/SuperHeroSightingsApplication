package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.Sighting;

import java.sql.Date;
import java.util.List;

public interface SightingDao {

    Sighting addSighting(Sighting sighting);
    List<Sighting> getAllSightings();
    Sighting getSightingById(int id);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);

    List<Sighting> getSightingsByDate(Date date);
    List<Sighting> getTenRecentSightings();

}
