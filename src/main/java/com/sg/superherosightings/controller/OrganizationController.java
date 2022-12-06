package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.OrganizationDao;
import com.sg.superherosightings.entities.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class OrganizationController {
    @Autowired
    OrganizationDao organizationDao;

    static Set<ConstraintViolation<Organization>> violations = new HashSet<>();

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        model.addAttribute("errors", violations);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(String name, String description,
        String address, String state, String city, String zip, String phone) {
        Organization organization = new Organization();
        organization.setName(name);
        organization.setDescription(description);
        organization.setAddress(address);
        organization.setState(state);
        organization.setCity(city);
        organization.setZip(zip);
        organization.setPhone(phone);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        violations = validate.validate(organization);
        if (violations.isEmpty()) {
            organizationDao.addOrganization(organization);
        }

        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization organization, BindingResult result) {
        if (!result.hasErrors()) {
            organizationDao.updateOrganization(organization);
            violations.clear();
            return "redirect:/organizations";
        }
        return "editOrganization";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "deleteOrganization";
    }

    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        violations.clear();
        return "organizationDetail";
    }

    @PostMapping("deleteOrganization")
    public String performDeleteOrganization(Integer id) {
        organizationDao.deleteOrganizationById(id);
        return "redirect:/organizations";
    }

}