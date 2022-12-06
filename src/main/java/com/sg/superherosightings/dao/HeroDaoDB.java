package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.Hero;
import com.sg.superherosightings.entities.Location;
import com.sg.superherosightings.entities.Organization;
import com.sg.superherosightings.entities.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HeroDaoDB implements HeroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Hero getHeroById(int id) {
        try {
            final String sql = "SELECT * FROM hero WHERE heroId = ?";
            Hero hero = jdbc.queryForObject(sql, new HeroMapper(), id);
            hero.setSuperpowers(getSuperpowersForHero(hero));
            hero.setOrganizations(getOrganizationsForHero(hero));
            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Superpower> getSuperpowersForHero(Hero hero) {
        final String sql = "SELECT sp.* FROM superpower sp " +
                "JOIN heroSuperpower hsp ON hsp.superpowerId = sp.superpowerId " +
                "WHERE hsp.heroId = ?";
        return jdbc.query(sql, new SuperpowerDaoDB.SuperpowerMapper(), hero.getId());
    }

    @Override
    public List<Organization> getOrganizationsForHero(Hero hero) {
        final String sql = "SELECT o.* FROM organization o " +
                "JOIN heroOrganization ho ON ho.organizationId = o.organizationId " +
                "WHERE ho.heroId = ?";
        return jdbc.query(sql, new OrganizationDaoDB.OrganizationMapper(), hero.getId());
    }

    @Override
    public List<Hero> getAllHeroes() {
        final String sql = "SELECT * FROM hero";
        List<Hero> heroes = jdbc.query(sql, new HeroMapper());
        associatePowersAndOrganizations(heroes);
        return heroes;
    }

    private void associatePowersAndOrganizations(List<Hero> heroes) {
        for (Hero hero : heroes) {
            hero.setOrganizations(getOrganizationsForHero(hero));
            hero.setSuperpowers(getSuperpowersForHero(hero));
        }
    }

    @Override
    public List<Hero> getHeroesForSuperpower(Superpower superpower) {
        final String sql = "SELECT h.* FROM hero h " +
                "JOIN heroSuperpower hsp ON hsp.heroId = h.heroId " +
                "Where hsp.superpowerId = ?";
        List<Hero> heroes = jdbc.query(sql, new HeroMapper(), superpower.getId());
        associatePowersAndOrganizations(heroes);
        return heroes;
    }

    @Override
    public List<Hero> getHeroesForOrganization(Organization org) {
        final String sql = "SELECT h.* FROM hero h " +
                "JOIN heroOrganization ho ON ho.heroId = h.heroId " +
                "WHERE ho.organizationId = ?";
        List<Hero> heroes = jdbc.query(sql, new HeroMapper(), org.getId());
        associatePowersAndOrganizations(heroes);
        return heroes;
    }

    @Override
    public List<Hero> getHeroesByLocation(Location location) {
        final String sql = "SELECT h.* FROM hero h " +
                "JOIN sighting s ON s.heroId = h.heroId " +
                "WHERE s.locationId = ?";
        List<Hero> heroes = jdbc.query(sql, new HeroMapper(), location.getId());
        associatePowersAndOrganizations(heroes);
        return heroes;
    }


    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String sql = "INSERT INTO hero(name, description) VALUES(?,?)";
        jdbc.update(sql, hero.getName(), hero.getDescription());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);
        insertHeroOrganization(hero);
        insertHeroSuperpower(hero);
        return hero;
    }

    private void insertHeroOrganization(Hero hero) {
        final String sql = "INSERT INTO " +
                "heroOrganization(heroId, organizationId) VALUES(?,?)";
        for (Organization org : hero.getOrganizations()) {
            jdbc.update(sql, hero.getId(), org.getId());
        }
    }

    private void insertHeroSuperpower(Hero hero) {
        final String sql = "INSERT INTO " +
                "heroSuperpower(heroId, superpowerId) VALUES(?,?)";
        for (Superpower power : hero.getSuperpowers()) {
            jdbc.update(sql, hero.getId(), power.getId());
        }
    }

    @Override
    @Transactional
    public void updateHero(Hero hero) {
        final String updateHero = "UPDATE hero SET name = ?, description = ? " +
                "WHERE heroId = ?";
        jdbc.update(updateHero, hero.getName(), hero.getDescription(), hero.getId());

        final String delFromBridgeOne = "DELETE FROM heroOrganization WHERE heroId = ?";
        jdbc.update(delFromBridgeOne, hero.getId());

        final String delFromBridgeTwo = "DELETE FROM heroSuperpower WHERE heroId = ?";
        jdbc.update(delFromBridgeTwo, hero.getId());

        insertHeroOrganization(hero);
        insertHeroSuperpower(hero);
    }

    @Override
    @Transactional
    public void deleteHeroById(int id) {
        //Deleting from heroOrganization Bridge table
        final String delFromBridgeOne = "DELETE FROM heroOrganization WHERE heroId = ?";
        jdbc.update(delFromBridgeOne, id);

        //Deleting from heroSuperpower Bridge Table
        final String delFromBridgeTwo = "DELETE FROM heroSuperpower WHERE heroId = ?";
        jdbc.update(delFromBridgeTwo, id);

        //Deleting from sighting table
        final String delFromSighting = "DELETE FROM sighting WHERE heroId = ?";
        jdbc.update(delFromSighting, id);

        //Finally, deleting from hero table
        final String delFromHero = "DELETE FROM hero WHERE heroId = ?";
        jdbc.update(delFromHero, id);

    }

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setId(rs.getInt("heroId"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            return hero;
        }
    }
}
