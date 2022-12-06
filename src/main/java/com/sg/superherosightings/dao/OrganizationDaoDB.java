package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.Hero;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String sql = "SELECT * FROM organization WHERE organizationId = ?";
            return jdbc.queryForObject(sql, new OrganizationMapper(), id);
            //Exception thrown if object doesn't exist in DB
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String sql = "SELECT * FROM organization";
        return jdbc.query(sql, new OrganizationMapper());
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization org) {
        final String sql = "INSERT INTO organization (name, description, " +
                "address, city, state, zip, phone) VALUES(?,?,?,?,?,?,?)";
        jdbc.update(sql, org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getCity(),
                org.getState(),
                org.getZip(),
                org.getPhone());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setId(newId);
        return org;
    }

    @Override
    public void updateOrganization(Organization org) {
        //Updating Organization object in superpower table
        final String updateOrg = "UPDATE organization SET name = ?, description = ?, address = ?," +
                "city = ?, state = ?, zip = ?, phone = ? WHERE organizationId = ?";
        jdbc.update(updateOrg, org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getCity(),
                org.getState(),
                org.getZip(),
                org.getPhone(),
                org.getId());
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        //Deleting from bridge table
        final String delFromBridge = "DELETE FROM heroOrganization WHERE organizationId = ?";
        jdbc.update(delFromBridge, id);

        //Deleting from organization table
        final String delFromOrg = "DELETE FROM organization WHERE organizationId = ?";
        jdbc.update(delFromOrg, id);
    }


//
//    private List<Hero> getHeroesForOrganization(int id) {
//        //Joining hero & heroOrganization tables to get heroes associated with passed in Id
//        final String sql = "SELECT h.* FROM hero h " +
//                "JOIN heroOrganization ho ON ho.heroId = h.heroId " +
//                "WHERE ho.organizationId = ?";
//        List<Hero> heroes = jdbc.query(sql, new HeroDaoDB.HeroMapper(), id);
//        associatePowersAndOrganizations(heroes);
//        return heroes;
//    }
//
//    private void associatePowersAndOrganizations(List<Hero> heroes) {
//        for (Hero hero : heroes) {
//            hero.setOrganizations(getOrganizationsForHero(hero));
//            hero.setSuperpowers(getSuperpowersForHero(hero));
//        }
//    }
//
//    private List<Superpower> getSuperpowersForHero(Hero hero) {
//        final String sql = "SELECT sp.* FROM superpower sp " +
//                "JOIN heroSuperpower hsp ON hsp.superpowerId = sp.superpowerId " +
//                "WHERE hsp.heroId = ?";
//        return jdbc.query(sql, new SuperpowerDaoDB.SuperpowerMapper(), hero.getId());
//    }

//
//    private void associateHeroes(List<Organization> orgs) {
//        //Assigning heroes to each organization
//        for (Organization org : orgs) {
//            org.setMembers(getHeroesForOrganization(org.getId()));
//        }
//    }

//    private void insertHeroOrganization(Organization org) {
//        //Inserting Organization and hero into heroOrganization bridge table
//        final String sql = "INSERT INTO heroOrganization(heroId, organizationId) " +
//                "VALUES(?,?)";
//        for (Hero hero : org.getMembers()) {
//            jdbc.update(sql, hero.getId(), org.getId());
//        }
//    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int Index) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("organizationId"));
            org.setName(rs.getString("name"));
            org.setDescription(rs.getString("description"));
            org.setAddress(rs.getString("address"));
            org.setCity(rs.getString("city"));
            org.setState(rs.getString("state"));
            org.setZip(rs.getString("zip"));
            org.setPhone(rs.getString("phone"));
            return org;
        }
    }
}
