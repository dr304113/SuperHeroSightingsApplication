package com.sg.superherosightings.dao;

import com.sg.superherosightings.*;
import com.sg.superherosightings.entities.*;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = TestApplicationConfiguration.class)
public class OrganizationDaoDBTest {
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
    public void testAddAndGetOrganization() {
        Organization org = new Organization();
        org.setName("Test Org Name");
        org.setDescription("Test Org Description");
        org.setAddress("Test Org Address");
        org.setState("Test Org State");
        org.setCity("Test Org City");
        org.setZip("12345");
        org.setPhone("6144445555");
        org = organizationDao.addOrganization(org);

        Organization fromDao = organizationDao.getOrganizationById(org.getId());
        Assertions.assertEquals(org, fromDao);
    }

    @Test
    public void testGetAllOrganizations() {
        Organization org = new Organization();
        org.setName("Test Org Name");
        org.setDescription("Test Org Description");
        org.setAddress("Test Org Address");
        org.setState("Test Org State");
        org.setCity("Test Org City");
        org.setZip("12345");
        org.setPhone("1111111111");
        org = organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setName("Test Org Name 2");
        org2.setDescription("Test Org Description 2");
        org2.setAddress("Test Org Address 2");
        org2.setState("Test Org State 2");
        org2.setCity("Test Org City 2");
        org2.setZip("98765");
        org2.setPhone("2222222222");
        org2 = organizationDao.addOrganization(org2);

        List<Organization> orgs = organizationDao.getAllOrganizations();

        Assertions.assertEquals(2, orgs.size());
        Assertions.assertTrue(orgs.contains(org));
        Assertions.assertTrue(orgs.contains(org2));
    }

    @Test
    public void testUpdateOrganization() {
        Organization org = new Organization();
        org.setName("Test Org Name");
        org.setDescription("Test Org Description");
        org.setAddress("Test Org Address");
        org.setState("Test Org State");
        org.setCity("Test Org City");
        org.setZip("12345");
        org.setPhone("1111111111");
        org = organizationDao.addOrganization(org);

        Organization fromDao = organizationDao.getOrganizationById(org.getId());
        Assertions.assertEquals(org, fromDao);

        org.setName("New Test Org Name");
        organizationDao.updateOrganization(org);

        Assertions.assertNotEquals(org, fromDao);

        fromDao = organizationDao.getOrganizationById(org.getId());

        Assert.assertEquals(org, fromDao);
    }

    @Test
    public void testDeleteOrganizationById() {
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

        Organization fromDao = organizationDao.getOrganizationById(org.getId());
        Assertions.assertEquals(org, fromDao);

        organizationDao.deleteOrganizationById(org.getId());

        fromDao = organizationDao.getOrganizationById(org.getId());
        Assertions.assertNull(fromDao);
    }
}