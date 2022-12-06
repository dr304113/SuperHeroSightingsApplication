package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.Hero;
import com.sg.superherosightings.entities.Location;
import com.sg.superherosightings.entities.Organization;
import com.sg.superherosightings.entities.Superpower;

import java.util.List;

public interface HeroDao {

    Hero addHero(Hero hero);

    List<Hero> getAllHeroes();

    Hero getHeroById(int id);

    void updateHero(Hero hero);

    void deleteHeroById(int id);

    List<Hero> getHeroesByLocation(Location location);

    List<Hero> getHeroesForOrganization(Organization org);

    List<Organization> getOrganizationsForHero(Hero hero);

    List<Hero> getHeroesForSuperpower(Superpower power);
}
