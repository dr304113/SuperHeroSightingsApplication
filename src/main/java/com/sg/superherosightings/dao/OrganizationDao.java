package com.sg.superherosightings.dao;

import com.sg.superherosightings.entities.Hero;
import com.sg.superherosightings.entities.Organization;

import java.util.List;

public interface OrganizationDao {

    Organization addOrganization(Organization org);
    List<Organization> getAllOrganizations();
    Organization getOrganizationById(int id);
    void updateOrganization(Organization org);
    void deleteOrganizationById(int id);

}
