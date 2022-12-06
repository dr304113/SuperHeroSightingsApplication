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
class HeroDaoDBTest {

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
    void testAddAndGetHero() {

        Organization org = new Organization();
        org.setName("Test Org Name");
        org.setDescription("Test Org Description");
        org.setAddress("123 Org Address");
        org.setState("Test Org State");
        org.setCity("Test Org City");
        org.setZip("12345");
        org.setPhone("7401234567");
        org = organizationDao.addOrganization(org);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);

        Superpower power = new Superpower();
        power.setDescription("Test Superpower Description");
        power.setName("Test Superpower Name");
        power = superpowerDao.addSuperpower(power);
        List<Superpower> powers = new ArrayList<>();
        powers.add(power);

        Hero hero = new Hero();
        hero.setName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setOrganizations(orgs);
        hero.setSuperpowers(powers);
        hero = heroDao.addHero(hero);


        Hero fromDao = heroDao.getHeroById(hero.getId());
        Assertions.assertEquals(hero, fromDao);


    }

    @Test
    void testGetAllHeroes() {
        Organization org = new Organization();
        org.setName("Test Org Name");
        org.setDescription("Test Org Description");
        org.setAddress("123 Org Address");
        org.setState("Test Org State");
        org.setCity("Test Org City");
        org.setZip("12345");
        org.setPhone("7401234567");
        org = organizationDao.addOrganization(org);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);

        Superpower power = new Superpower();
        power.setDescription("Test Superpower Description");
        power.setName("Test Superpower Name");
        power = superpowerDao.addSuperpower(power);
        List<Superpower> powers = new ArrayList<>();
        powers.add(power);

        Hero hero = new Hero();
        hero.setName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setOrganizations(orgs);
        hero.setSuperpowers(powers);
        hero = heroDao.addHero(hero);

        Hero hero2 = new Hero();
        hero2.setName("Test Hero Name 2");
        hero2.setDescription("Test Hero Description 2");
        hero2.setOrganizations(orgs);
        hero2.setSuperpowers(powers);
        hero2 = heroDao.addHero(hero2);

        List<Hero> heroes = heroDao.getAllHeroes();
        Assertions.assertEquals(2, heroes.size());
        Assertions.assertTrue(heroes.contains(hero));
        Assertions.assertTrue(heroes.contains(hero2));
    }

    @Test
    void testUpdateHero() {
        Organization org = new Organization();
        org.setName("Test Org Name");
        org.setDescription("Test Org Description");
        org.setAddress("123 Org Address");
        org.setState("Test Org State");
        org.setCity("Test Org City");
        org.setZip("12345");
        org.setPhone("7401234567");
        org = organizationDao.addOrganization(org);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);

        Superpower power = new Superpower();
        power.setDescription("Test Superpower Description");
        power.setName("Test Superpower Name");
        power = superpowerDao.addSuperpower(power);
        List<Superpower> powers = new ArrayList<>();
        powers.add(power);

        Hero hero = new Hero();
        hero.setName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setOrganizations(orgs);
        hero.setSuperpowers(powers);
        hero = heroDao.addHero(hero);

        Hero fromDao = heroDao.getHeroById(hero.getId());
        Assertions.assertEquals(hero, fromDao);

        hero.setName("New Test Hero Name");

        Organization org2 = new Organization();
        org2.setName("Test Org Name 2");
        org2.setDescription("Test org Description 2");
        org2.setAddress("Test org Address 2");
        org2.setState("Test org Address 2");
        org2.setCity("Test org City 2");
        org2.setZip("11223");
        org2.setPhone("6145555555");
        org2 = organizationDao.addOrganization(org2);
        orgs.add(org2);
        hero.setOrganizations(orgs);

        Superpower power2 = new Superpower();
        power2.setName("Test Superpower Name 2");
        power2.setDescription("Test Superpower Description 2");
        power2 = superpowerDao.addSuperpower(power2);
        powers.add(power2);
        hero.setSuperpowers(powers);

        heroDao.updateHero(hero);

        Assertions.assertNotEquals(hero, fromDao);

        fromDao = heroDao.getHeroById(hero.getId());
        Assertions.assertEquals(hero, fromDao);
    }

    @Test
    void deleteHeroById() {
        Organization org = new Organization();
        org.setName("Test Org Name");
        org.setDescription("Test Org Description");
        org.setAddress("123 Org Address");
        org.setState("Test Org State");
        org.setCity("Test Org City");
        org.setZip("12345");
        org.setPhone("7401234567");
        org = organizationDao.addOrganization(org);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);

        Superpower power = new Superpower();
        power.setDescription("Test Superpower Description");
        power.setName("Test Superpower Name");
        power = superpowerDao.addSuperpower(power);
        List<Superpower> powers = new ArrayList<>();
        powers.add(power);

        Hero hero = new Hero();
        hero.setName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setOrganizations(orgs);
        hero.setSuperpowers(powers);
        hero = heroDao.addHero(hero);

        Hero fromDao = heroDao.getHeroById(hero.getId());
        Assertions.assertEquals(hero, fromDao);

        heroDao.deleteHeroById(hero.getId());

        fromDao = heroDao.getHeroById(hero.getId());
        Assertions.assertNull(fromDao);
    }

    @Test
    void getHeroesByLocation() {
        Organization org = new Organization();
        org.setName("Test Org Name");
        org.setDescription("Test Org Description");
        org.setAddress("123 Org Address");
        org.setState("Test Org State");
        org.setCity("Test Org City");
        org.setZip("12345");
        org.setPhone("7401234567");
        org = organizationDao.addOrganization(org);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);

        Superpower power = new Superpower();
        power.setDescription("Test Superpower Description");
        power.setName("Test Superpower Name");
        power = superpowerDao.addSuperpower(power);

        List<Superpower> powers = new ArrayList<>();
        powers.add(power);

        Hero hero = new Hero();
        hero.setName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setOrganizations(orgs);
        hero.setSuperpowers(powers);
        hero = heroDao.addHero(hero);

        Hero hero2 = new Hero();
        hero2.setName("Test Hero Name 2");
        hero2.setDescription("Test Hero Description 2");
        hero2.setOrganizations(orgs);
        hero2.setSuperpowers(powers);
        hero2 = heroDao.addHero(hero2);

        Hero hero3 = new Hero();
        hero3.setName("Test Hero Name 3");
        hero3.setDescription("Test Hero Description 3");
        hero3.setOrganizations(orgs);
        hero3.setSuperpowers(powers);
        hero3 = heroDao.addHero(hero3);

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
        location2.setLongitude("11.111111");
        location2.setLatitude("11.111111");
        location2 = locationDao.addLocation(location2);

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
        sighting2.setHero(hero2);
        //Converting String into sql date
        sighting2.setDate(d);
        sighting2 = sightingDao.addSighting(sighting2);

        //Test Sighting 3 object
        Sighting sighting3 = new Sighting();
        sighting3.setLocation(location2);
        sighting3.setHero(hero3);
        //Converting String into sql date
        sighting3.setDate(d);
        sighting3 = sightingDao.addSighting(sighting3);

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        heroes.add(hero2);
        heroes.add(hero3);

        List<Hero> fromDao = heroDao.getHeroesByLocation(location);

        Assertions.assertEquals(2, fromDao.size());
        Assertions.assertTrue(fromDao.contains(hero));
        Assertions.assertTrue(fromDao.contains(hero2));
        Assertions.assertFalse(fromDao.contains(hero3));
    }

    @Test
    void getHeroesForSuperpowers() {
        Organization org = new Organization();
        org.setName("Test Org Name");
        org.setDescription("Test Org Description");
        org.setAddress("123 Org Address");
        org.setState("Test Org State");
        org.setCity("Test Org City");
        org.setZip("12345");
        org.setPhone("7401234567");
        org = organizationDao.addOrganization(org);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);

        Superpower power = new Superpower();
        power.setDescription("Test Superpower Description");
        power.setName("Test Superpower Name");
        power = superpowerDao.addSuperpower(power);

        Superpower power2 = new Superpower();
        power2.setDescription("Test Superpower Description 2");
        power2.setName("Test Superpower Name 2");
        power2 = superpowerDao.addSuperpower(power2);

        List<Superpower> powers = new ArrayList<>();
        powers.add(power);
        powers.add(power2);

        List<Superpower> powers2 = new ArrayList<>();
        powers2.add(power2);

        Hero hero = new Hero();
        hero.setName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setOrganizations(orgs);
        hero.setSuperpowers(powers);
        hero = heroDao.addHero(hero);

        Hero hero2 = new Hero();
        hero2.setName("Test Hero Name 2");
        hero2.setDescription("Test Hero Description 2");
        hero2.setOrganizations(orgs);
        hero2.setSuperpowers(powers2);
        hero2 = heroDao.addHero(hero2);

        Hero hero3 = new Hero();
        hero3.setName("Test Hero Name 3");
        hero3.setDescription("Test Hero Description 3");
        hero3.setOrganizations(orgs);
        hero3.setSuperpowers(powers);
        hero3 = heroDao.addHero(hero3);

        List<Hero> heroes = heroDao.getHeroesForSuperpower(power);
        Assertions.assertEquals(2, heroes.size());
        Assertions.assertTrue(heroes.contains(hero));
        Assertions.assertFalse(heroes.contains(hero2));
        Assertions.assertTrue(heroes.contains(hero3));
    }

    @Test
    void getHeroesForOrganization() {
        Superpower power = new Superpower();
        power.setDescription("Test Superpower Description");
        power.setName("Test Superpower Name");
        power = superpowerDao.addSuperpower(power);
        List<Superpower> powers = new ArrayList<>();
        powers.add(power);

        Organization org = new Organization();
        org.setName("Test Org Name");
        org.setDescription("Test Org Description");
        org.setAddress("123 Org Address");
        org.setState("Test Org State");
        org.setCity("Test Org City");
        org.setZip("12345");
        org.setPhone("7401234567");
        org = organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setName("Test Org Name 2");
        org2.setDescription("Test Org Description 2");
        org2.setAddress("123 Org Address 2");
        org2.setState("Test Org State 2");
        org2.setCity("Test Org City 2");
        org2.setZip("12345");
        org2.setPhone("7401234567");
        org2 = organizationDao.addOrganization(org2);

        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);
        orgs.add(org2);

        List<Organization> orgs2 = new ArrayList<>();
        orgs2.add(org2);

        Hero hero = new Hero();
        hero.setName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setOrganizations(orgs);
        hero.setSuperpowers(powers);
        hero = heroDao.addHero(hero);

        Hero hero2 = new Hero();
        hero2.setName("Test Hero Name 2");
        hero2.setDescription("Test Hero Description 2");
        hero2.setOrganizations(orgs2);
        hero2.setSuperpowers(powers);
        hero2 = heroDao.addHero(hero2);

        Hero hero3 = new Hero();
        hero3.setName("Test Hero Name 3");
        hero3.setDescription("Test Hero Description 3");
        hero3.setOrganizations(orgs);
        hero3.setSuperpowers(powers);
        hero3 = heroDao.addHero(hero3);

        List<Hero> heroes = heroDao.getHeroesForOrganization(org);
        Assertions.assertEquals(2, heroes.size());
        Assertions.assertTrue(heroes.contains(hero));
        Assertions.assertFalse(heroes.contains(hero2));
        Assertions.assertTrue(heroes.contains(hero3));


    }

    @Test
    public void testGetOrganizationsForHero() {
        Organization org = new Organization();
        org.setName("Test Org Name");
        org.setDescription("Test Org Description");
        org.setAddress("123 Org Address");
        org.setState("Test Org State");
        org.setCity("Test Org City");
        org.setZip("12345");
        org.setPhone("7401234567");
        org = organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setName("Test Org Name 2");
        org2.setDescription("Test Org Description 2");
        org2.setAddress("123 Org Address 2");
        org2.setState("Test Org State 2");
        org2.setCity("Test Org City 2");
        org2.setZip("12345");
        org2.setPhone("7401234567");
        org2 = organizationDao.addOrganization(org2);

        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);
        orgs.add(org2);

        Superpower power = new Superpower();
        power.setDescription("Test Superpower Description");
        power.setName("Test Superpower Name");
        power = superpowerDao.addSuperpower(power);
        List<Superpower> powers = new ArrayList<>();
        powers.add(power);

        Hero hero = new Hero();
        hero.setName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setOrganizations(orgs);
        hero.setSuperpowers(powers);
        hero = heroDao.addHero(hero);

        List<Organization> fromDao = heroDao.getOrganizationsForHero(hero);

        Assertions.assertEquals(2, fromDao.size());
        Assertions.assertTrue(fromDao.contains(org));
        Assertions.assertTrue(fromDao.contains(org2));
    }
}