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
public class SightingDaoDBTest {
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
    public void testAddAndGetSighting() {
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

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        Assertions.assertEquals(sighting, fromDao);
    }

    @Test
    public void testGetAllSightings() {
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

        //Test Sighting 2 object
        Sighting sighting2 = new Sighting();
        sighting2.setLocation(location);
        sighting2.setHero(hero);
        //Converting String into sql date
        Date d2 = Date.valueOf("2022-10-15");
        sighting2.setDate(d2);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightings();
        Assertions.assertEquals(2, sightings.size());
        Assertions.assertTrue(sightings.contains(sighting));
        Assertions.assertTrue(sightings.contains(sighting2));
    }

    @Test
    public void testUpdateSighting() {
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

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        Assertions.assertEquals(sighting, fromDao);

        Date updatedDate = Date.valueOf("2021-10-15");
        sighting.setDate(updatedDate);

        sightingDao.updateSighting(sighting);

        Assertions.assertNotEquals(sighting, fromDao);

        fromDao = sightingDao.getSightingById(sighting.getId());
        Assertions.assertEquals(sighting, fromDao);
    }

    @Test
    public void testDeleteSightingById() {
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

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        Assertions.assertEquals(sighting, fromDao);

        sightingDao.deleteSightingById(sighting.getId());

        fromDao = sightingDao.getSightingById(sighting.getId());
        Assertions.assertNull(fromDao);
    }

    @Test
    public void testGetSightingsByDate() {
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

        //Test Sighting 2 object
        Sighting sighting2 = new Sighting();
        sighting2.setLocation(location);
        sighting2.setHero(hero);
        //Converting String into sql date
        sighting2.setDate(d);
        sighting2 = sightingDao.addSighting(sighting2);

        //Test Sighting 3 object
        Sighting sighting3 = new Sighting();
        sighting3.setLocation(location);
        sighting3.setHero(hero);
        //Converting String into sql date
        Date d3 = Date.valueOf("2021-09-11");
        sighting3.setDate(d3);
        sighting3 = sightingDao.addSighting(sighting3);

        List<Sighting> sightings = new ArrayList<>();
        sightings.add(sighting);
        sightings.add(sighting2);
        sightings.add(sighting3);

        List<Sighting> fromDao = sightingDao.getSightingsByDate(d);

        Assertions.assertNotEquals(sightings, fromDao);
        Assertions.assertEquals(2, fromDao.size());
        Assertions.assertTrue(fromDao.contains(sighting));
        Assertions.assertTrue(fromDao.contains(sighting2));
        Assertions.assertFalse(fromDao.contains(sighting3));

    }


}