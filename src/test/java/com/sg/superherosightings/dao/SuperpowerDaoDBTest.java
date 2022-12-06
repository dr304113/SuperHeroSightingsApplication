package com.sg.superherosightings.dao;

import com.sg.superherosightings.*;
import com.sg.superherosightings.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = TestApplicationConfiguration.class)
public class SuperpowerDaoDBTest {
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
    public void testAndGetSuperpower() {
        Superpower power = new Superpower();
        power.setName("Test Power Name");
        power.setDescription("Test Power Description");
        power = superpowerDao.addSuperpower(power);

        Superpower fromDao = superpowerDao.getSuperpowerById(power.getId());
        Assertions.assertEquals(power, fromDao);
    }

    @Test
    public void testGetAllSuperpowers() {
        Superpower power = new Superpower();
        power.setName("Test Power Name");
        power.setDescription("Test Power Description");
        power = superpowerDao.addSuperpower(power);

        Superpower power2 = new Superpower();
        power2.setName("Test Power Name 2");
        power2.setDescription("Test Power Description 2");
        power2 = superpowerDao.addSuperpower(power2);

        List<Superpower> powers = superpowerDao.getAllSuperpowers();

        Assertions.assertEquals(2, powers.size());
        Assertions.assertTrue(powers.contains(power));
        Assertions.assertTrue(powers.contains(power2));
    }

    @Test
    public void testUpdateSuperpower() {
        Superpower power = new Superpower();
        power.setName("Test Power Name");
        power.setDescription("Test Power Description");
        power = superpowerDao.addSuperpower(power);

        Superpower fromDao = superpowerDao.getSuperpowerById(power.getId());
        Assertions.assertEquals(power, fromDao);

        power.setName("New Test Power Name");
        superpowerDao.updateSuperpower(power);

        Assertions.assertNotEquals(power, fromDao);

        fromDao = superpowerDao.getSuperpowerById(power.getId());

        Assertions.assertEquals(power, fromDao);
    }

    @Test
    public void testDeleteSuperpowerById() {
        Superpower power = new Superpower();
        power.setName("Test Power Name");
        power.setDescription("Test Power Description");
        power = superpowerDao.addSuperpower(power);
        List<Superpower> powers = new ArrayList<>();
        powers.add(power);

        Organization org = new Organization();
        org.setName("Test Org Name");
        org.setDescription("Test Org Description");
        org.setAddress("Test Org Address");
        org.setState("Test Org State");
        org.setCity("Test Org City");
        org.setZip("12345");
        org.setPhone("6144445555");
        org = organizationDao.addOrganization(org);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);

        Hero hero = new Hero();
        hero.setName("Test Hero Name");
        hero.setDescription("Test Hero Description");
        hero.setOrganizations(orgs);
        hero.setSuperpowers(powers);
        hero = heroDao.addHero(hero);

        Superpower fromDao = superpowerDao.getSuperpowerById(power.getId());
        Assertions.assertEquals(power, fromDao);

        superpowerDao.deleteSuperpowerById(power.getId());

        fromDao = superpowerDao.getSuperpowerById(power.getId());
        Assertions.assertNull(fromDao);

    }
}