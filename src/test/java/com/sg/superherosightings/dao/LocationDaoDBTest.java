package com.sg.superherosightings.dao;

import com.sg.superherosightings.*;
import com.sg.superherosightings.dao.*;
import com.sg.superherosightings.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = TestApplicationConfiguration.class)
public class LocationDaoDBTest {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperpowerDao superpowerDao;

    @Autowired
    HeroDao heroDao;

    @BeforeEach
    public void setUp() {
        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }
        List<Organization> orgs = organizationDao.getAllOrganizations();
        for (Organization org : orgs) {
            organizationDao.deleteOrganizationById(org.getId());
        }
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }
        List<Superpower> powers = superpowerDao.getAllSuperpowers();
        for (Superpower power : powers) {
            superpowerDao.deleteSuperpowerById(power.getId());
        }
        List<Hero> heroes = heroDao.getAllHeroes();
        for (Hero hero : heroes) {
            heroDao.deleteHeroById(hero.getId());
        }
    }

    @Test
    public void addAndGetLocation() {
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setState("Test Location State");
        location.setCity("Test Location City");
        location.setZip("12345");
        location.setLongitude("12.123456");
        location.setLatitude("12.123456");
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());

        Assertions.assertEquals(location, fromDao);
    }

    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setState("Test Location State");
        location.setCity("Test Location City");
        location.setZip("12345");
        location.setLongitude("12.123456");
        location.setLatitude("12.123456");
        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setName("Test Location Name 2");
        location2.setDescription("Test Location Description 2");
        location2.setAddress("Test Location Address 2");
        location2.setState("Test Location State 2");
        location2.setCity("Test Location City 2");
        location2.setZip("65432");
        location2.setLongitude("22.123456");
        location2.setLatitude("22.123456");
        location2 = locationDao.addLocation(location2);

        List<Location> locations = locationDao.getAllLocations();

        Assertions.assertEquals(2, locations.size());
        Assertions.assertTrue(locations.contains(location));
        Assertions.assertTrue(locations.contains(location2));
    }

    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setState("Test Location State");
        location.setCity("Test Location City");
        location.setZip("12345");
        location.setLongitude("12.123456");
        location.setLatitude("12.123456");
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());
        Assertions.assertEquals(location, fromDao);

        location.setName("New Test Location Name");
        locationDao.updateLocation(location);

        Assertions.assertNotEquals(location, fromDao);

        fromDao = locationDao.getLocationById(location.getId());

        Assertions.assertEquals(location, fromDao);
    }

    @Test
    public void testDeleteLocationById() {
        //Test Location object
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setState("Test Location State");
        location.setCity("Test Location City");
        location.setZip("12345");
        location.setLongitude("12.123456");
        location.setLatitude("12.123456");
        location = locationDao.addLocation(location);

        //Test Organization object
        Organization org = new Organization();
        org.setName("Test Org Name");
        org.setDescription("Test Org Description");
        org.setAddress("Test Org Address");
        org.setState("Test Org State");
        org.setCity("Test Org City");
        org.setZip("12345");
        org.setPhone("6144445555");
        org = organizationDao.addOrganization(org);

        //Test Power Object
        Superpower power = new Superpower();
        power.setName("Test Power Name");
        power.setDescription("Test Power Description");
        power = superpowerDao.addSuperpower(power);

        //Putting Superpowers & Organizations into Lists for Hero
        List<Superpower> powers = new ArrayList<>();
        powers.add(power);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);

        //Test Hero Object
        Hero hero = new Hero();
        hero.setName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setSuperpowers(powers);
        hero.setOrganizations(orgs);
        hero = heroDao.addHero(hero);

        //Test Sighting object
        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setHero(hero);
        //Converting String into sql date
        Date d = Date.valueOf("2022-10-15");
        sighting.setDate(d);
        sighting = sightingDao.addSighting(sighting);

        Location fromDao = locationDao.getLocationById(location.getId());
        Assertions.assertEquals(location, fromDao);

        locationDao.deleteLocationById(location.getId());

        fromDao = locationDao.getLocationById(location.getId());
        Assertions.assertNull(fromDao);
    }

    @Test
    public void testGetLocationsForHero() {
        //Test Location object
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setState("Test Location State");
        location.setCity("Test Location City");
        location.setZip("12345");
        location.setLongitude("12.123456");
        location.setLatitude("12.123456");
        location = locationDao.addLocation(location);

        //Test Location 2 object
        Location location2 = new Location();
        location2.setName("Test Location Name 2");
        location2.setDescription("Test Location Description 2");
        location2.setAddress("Test Location Address 2");
        location2.setState("Test Location State 2");
        location2.setCity("Test Location City 2");
        location2.setZip("12345");
        location2.setLongitude("12.123456");
        location2.setLatitude("12.123456");
        location2 = locationDao.addLocation(location2);

        //Test Organization object
        Organization org = new Organization();
        org.setName("Test Org Name");
        org.setDescription("Test Org Description");
        org.setAddress("Test Org Address");
        org.setState("Test Org State");
        org.setCity("Test Org City");
        org.setZip("12345");
        org.setPhone("6144445555");
        org = organizationDao.addOrganization(org);

        //Test Power Object
        Superpower power = new Superpower();
        power.setName("Test Power Name");
        power.setDescription("Test Power Description");
        power = superpowerDao.addSuperpower(power);

        //Putting Superpowers & Organizations into Lists for Hero
        List<Superpower> powers = new ArrayList<>();
        powers.add(power);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);


        //Test Hero Object
        Hero hero = new Hero();
        hero.setName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setSuperpowers(powers);
        hero.setOrganizations(orgs);
        heroDao.addHero(hero);

        //Test Sighting object
        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setHero(hero);
        //Converting String into sql date
        Date d = Date.valueOf("2022-10-15");
        sighting.setDate(d);
        sightingDao.addSighting(sighting);

        //Test Sighting 2 object
        Sighting sighting2 = new Sighting();
        sighting2.setLocation(location2);
        sighting2.setHero(hero);
        //Converting String into sql date
        Date d2 = Date.valueOf("2022-10-14");
        sighting2.setDate(d2);
        sightingDao.addSighting(sighting2);

        List<Location> fromDao = locationDao.getLocationsForHero(hero);

        Assertions.assertEquals(2, fromDao.size());
    }
}