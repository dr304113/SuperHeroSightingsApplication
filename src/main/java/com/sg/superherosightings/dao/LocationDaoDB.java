package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.Hero;
import com.sg.superherosightings.entities.Location;
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
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;


    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String sql = "INSERT INTO location(name, description, address, city, state, zip, longitude, latitude) " +
                "VALUES(?,?,?,?,?,?,?,?)";
        jdbc.update(sql,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getLongitude(),
                location.getLatitude());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public List<Location> getAllLocations() {
        final String sql = "SELECT * FROM location";
        return jdbc.query(sql, new LocationMapper());
    }

    @Override
    public Location getLocationById(int id) {
        try {
            final String sql = "SELECT * FROM location WHERE locationId = ?";
            return jdbc.queryForObject(sql, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateLocation(Location location) {
        final String sql = "UPDATE location SET name = ?," +
                " description = ?," +
                " address = ?," +
                " city = ?," +
                " state = ?," +
                " zip = ?," +
                " longitude = ?," +
                " latitude = ?" +
                " WHERE locationId = ?";
        jdbc.update(sql,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getLongitude(),
                location.getLatitude(),
                location.getId());
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {

        //Deleting Sightings associated with this id from sighting table
        final String delFromSightings = "DELETE FROM sighting WHERE locationId = ?";
        jdbc.update(delFromSightings, id);

        //Finally, deleting location itself from location table
        final String delLocation = "DELETE FROM location WHERE locationId = ?";
        jdbc.update(delLocation, id);
    }

    @Override
    public List<Location> getLocationsForHero(Hero hero) {
        final String sql = "SELECT l.* FROM location l " +
                "JOIN sighting s ON s.locationId = l.locationId " +
                "JOIN hero h ON h.heroId = s.heroId WHERE h.heroId = ?";
        return jdbc.query(sql, new LocationMapper(), hero.getId());
    }


    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("locationId"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            location.setAddress(rs.getString("address"));
            location.setCity(rs.getString("city"));
            location.setState(rs.getString("state"));
            location.setZip(rs.getString("zip"));
            location.setLongitude(rs.getString("longitude"));
            location.setLatitude(rs.getString("latitude"));
            return location;
        }
    }

}
