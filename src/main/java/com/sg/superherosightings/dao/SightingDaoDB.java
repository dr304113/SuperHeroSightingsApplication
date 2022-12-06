package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String sql = "SELECT * FROM sighting WHERE sightingId = ?";
            Sighting sighting = jdbc.queryForObject(sql, new SightingMapper(), id);
            sighting.setLocation(getLocationForSighting(id));
            sighting.setHero(getHeroForSighting(id));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getTenRecentSightings() {
        final String sql = "SELECT * FROM sighting " +
                "ORDER BY date DESC LIMIT 10";
        List<Sighting> sightings = jdbc.query(sql, new SightingDaoDB.SightingMapper());
        associateHeroAndLocation(sightings);
        return sightings;
    }

    private Location getLocationForSighting(int id) {
        final String sql = "SELECT l.* FROM location l " +
                "JOIN sighting s ON s.locationId = l.locationId " +
                "WHERE s.sightingId = ?";
        return jdbc.queryForObject(sql, new LocationDaoDB.LocationMapper(), id);
    }

    private Hero getHeroForSighting(int id) {
        final String sql = "SELECT h.* FROM hero h " +
                "JOIN sighting s ON s.heroId = h.heroId " +
                "WHERE s.sightingId = ?";
        Hero hero = jdbc.queryForObject(sql, new HeroDaoDB.HeroMapper(), id);
        hero.setSuperpowers(getSuperpowersForHero(hero));
        hero.setOrganizations(getOrganizationsForHero(hero));
        return hero;

    }

    private List<Superpower> getSuperpowersForHero(Hero hero) {
        //Getting all Superpowers for passed in hero object
        final String sql = "SELECT s.* FROM superpower s " +
                "JOIN heroSuperpower hsp ON hsp.superpowerId = s.superpowerId " +
                "WHERE hsp.heroId = ?";
        return jdbc.query(sql, new SuperpowerDaoDB.SuperpowerMapper(), hero.getId());
    }

    private List<Organization> getOrganizationsForHero(Hero hero) {
        //Getting all organizations for passed in hero object
        final String sql = "SELECT o.* FROM organization o" +
                " JOIN heroOrganization ho ON ho.organizationId = o.organizationId" +
                " WHERE ho.heroId = ?";
        return jdbc.query(sql, new OrganizationDaoDB.OrganizationMapper(), hero.getId());
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String sql = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(sql, new SightingMapper());
        associateHeroAndLocation(sightings);
        return sightings;
    }


    private void associateHeroAndLocation(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getId()));
            sighting.setHero(getHeroForSighting(sighting.getId()));
        }
    }

    @Override
    public List<Sighting> getSightingsByDate(Date date) {
        final String sql = "SELECT * FROM sighting WHERE date = ?";
        List<Sighting> sightings = jdbc.query(sql, new SightingMapper(), date);
        associateHeroAndLocation(sightings);
        return sightings;
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String sql = "INSERT INTO sighting(date, heroId, locationId) " +
                "VALUES(?,?,?)";
        jdbc.update(sql, sighting.getDate(), sighting.getHero().getId(), sighting.getLocation().getId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        sighting.setHero(getHeroForSighting(sighting.getId()));
        sighting.setLocation(getLocationForSighting(sighting.getId()));
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String sql = "UPDATE sighting SET date = ?, heroId = ?, " +
                "locationId = ? WHERE sightingId = ?";
        jdbc.update(sql,
                sighting.getDate(),
                sighting.getHero().getId(),
                sighting.getLocation().getId(),
                sighting.getId());
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) {
        //Getting location to delete
        Sighting sighting = getSightingById(id);
//        int locationToDelete = sighting.getLocation().getId();
//
//        final String delLocation = "DELETE FROM location l " +
//                "WHERE locationId = ?";
//        jdbc.update(delLocation, locationToDelete);

        //First, delete sighting from sighting
        final String delFromSighting = "DELETE FROM sighting WHERE sightingId = ?";
        jdbc.update(delFromSighting, id);

    }


    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("sightingId"));
            sighting.setDate(rs.getDate("date"));
            return sighting;
        }

    }

}
