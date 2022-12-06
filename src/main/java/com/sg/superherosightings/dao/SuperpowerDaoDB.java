package com.sg.superherosightings.dao;

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
public class SuperpowerDaoDB implements SuperpowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superpower getSuperpowerById(int id) {
        try {
            final String sql = "SELECT * FROM superpower WHERE superpowerId = ?";
            return jdbc.queryForObject(sql, new SuperpowerMapper(), id);
            //Exception thrown if object doesn't exist in DB
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        final String sql = "SELECT * FROM superpower";
        return jdbc.query(sql, new SuperpowerMapper());
    }

    @Override
    @Transactional
    public Superpower addSuperpower(Superpower superpower) {
        final String sql = "INSERT INTO superpower(name, description) VALUES(?,?)";
        jdbc.update(sql, superpower.getName(), superpower.getDescription());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superpower.setId(newId);
        return superpower;
    }

    @Override
    public void updateSuperpower(Superpower superpower) {
        final String updateSuperpower = "UPDATE superpower SET name = ?, description = ? WHERE superpowerId = ?";
        jdbc.update(updateSuperpower, superpower.getName(), superpower.getDescription(), superpower.getId());

    }

    @Override
    @Transactional
    public void deleteSuperpowerById(int id) {
        //Deleting from bridge table
        final String sqlDeleteFromBridge = "DELETE FROM heroSuperpower WHERE superpowerId = ?";
        jdbc.update(sqlDeleteFromBridge, id);

        //Then deleting from superpower table
        final String sqlDeleteFromSuperpower = "DELETE FROM superpower WHERE superpowerId = ?";
        jdbc.update(sqlDeleteFromSuperpower, id);
    }

    public static final class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower superpower = new Superpower();
            superpower.setId(rs.getInt("superpowerId"));
            superpower.setName(rs.getString("name"));
            superpower.setDescription(rs.getString("description"));
            return superpower;
        }

    }
}
