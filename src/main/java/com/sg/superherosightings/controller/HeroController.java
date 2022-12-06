package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.HeroDao;
import com.sg.superherosightings.dao.OrganizationDao;
import com.sg.superherosightings.dao.SuperpowerDao;
import com.sg.superherosightings.entities.Hero;
import com.sg.superherosightings.entities.Organization;
import com.sg.superherosightings.entities.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HeroController {

    @Autowired
    SuperpowerDao superpowerDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    OrganizationDao organizationDao;

    static Set<ConstraintViolation<Hero>> violations = new HashSet<>();

    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        //Retrieve lists from Dao
        List<Hero> heroes = heroDao.getAllHeroes();
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        List<Organization> organizations = organizationDao.getAllOrganizations();
        //Then add to model
        model.addAttribute("heroes", heroes);
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("organizations", organizations);
        model.addAttribute("errors", violations);
        //Push to hero.html
        return "heroes";
    }

    @PostMapping("addHero")
    public String addHero(Hero hero, HttpServletRequest request) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        String[] superpowerIds = request.getParameterValues("superpowerId");
        String[] organizationIds = request.getParameterValues("organizationId");

        List<Superpower> superpowers = new ArrayList<>();
        if (superpowerIds != null) {
            for (String superpowerId : superpowerIds) {
                superpowers.add(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));
            }
        }
        hero.setSuperpowers(superpowers);

        List<Organization> organizations = new ArrayList<>();
        if (organizationIds != null) {
            for (String organizationId : organizationIds) {
                organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
            }
        }
        hero.setOrganizations(organizations);

        violations = validate.validate(hero);
        if (violations.isEmpty()) {
            heroDao.addHero(hero);
        }
        return "redirect:/heroes";
    }

    @GetMapping("heroDetail")
    public String heroDetail(Integer id, Model model) {
        Hero hero = heroDao.getHeroById(id);
        model.addAttribute("hero", hero);
        violations.clear();
        return "heroDetail";
    }

    @GetMapping("deleteHero")
    public String deleteHero(Integer id, Model model) {
        Hero hero = heroDao.getHeroById(id);
        model.addAttribute("hero", hero);
        violations.clear();
        return "deleteHero";
    }

    @PostMapping("deleteHero")
    public String performDeleteHero(Integer id) {
        heroDao.deleteHeroById(id);
        return "redirect:/heroes";
    }

    @GetMapping("editHero")
    public String editHero(Integer id, Model model) {
        Hero hero = heroDao.getHeroById(id);
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("hero", hero);
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("organizations", organizations);
        model.addAttribute("errors", violations);
        return "editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(Hero hero, HttpServletRequest request) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        //getParameterValues gets data from the "name" in html form
        String[] superpowerIds = request.getParameterValues("superpowerId");
        String[] organizationIds = request.getParameterValues("organizationId");

        //Make new list, sort through the data String we get from the list above, get them from Dao, add them to the new list
        List<Superpower> superpowers = new ArrayList<>();
        for (String superpowerId : superpowerIds) {
            superpowers.add(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));
        }

        List<Organization> organizations = new ArrayList<>();
        for (String organizationId : organizationIds) {
            organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
        }


        //Give hero superpowers and organizations from the new lists made above
        hero.setSuperpowers(superpowers);
        hero.setOrganizations(organizations);

        //Send updated info to Dao
        violations = validate.validate(hero);
        if (violations.isEmpty()) {
            return "editHero";
        } else {
            heroDao.updateHero(hero);
        }

        //redirect back to regular hero html page
        return "redirect:/heroes";
    }


}
