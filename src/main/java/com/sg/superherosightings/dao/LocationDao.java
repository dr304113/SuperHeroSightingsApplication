package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.Hero;
import com.sg.superherosightings.entities.Location;

import java.util.List;

public interface LocationDao {

    Location addLocation(Location location);
    List<Location> getAllLocations();
    Location getLocationById(int id);
    void updateLocation(Location location);
    void deleteLocationById(int id);

    List<Location> getLocationsForHero(Hero hero);

}
