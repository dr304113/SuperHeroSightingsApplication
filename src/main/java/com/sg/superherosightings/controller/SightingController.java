package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.HeroDao;
import com.sg.superherosightings.dao.LocationDao;
import com.sg.superherosightings.dao.SightingDao;
import com.sg.superherosightings.entities.Hero;
import com.sg.superherosightings.entities.Location;
import com.sg.superherosightings.entities.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SightingController {

    @Autowired
    SightingDao sightingDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    static Set<ConstraintViolation<Sighting>> violations = new HashSet<>();

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Hero> heroes = heroDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocations();

        model.addAttribute("sightings", sightings);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(Sighting sighting, HttpServletRequest request, String dateString) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");

        sighting.setHero(heroDao.getHeroById(Integer.parseInt(heroId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        try {
            sighting.setDate(Date.valueOf(dateString));
        } catch (IllegalArgumentException ex) {
        }

        violations = validate.validate(sighting);
        if (violations.isEmpty()) {
            sightingDao.addSighting(sighting);
        }

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        List<Hero> heroes = heroDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocations();

        model.addAttribute("sighting", sighting);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(Sighting sighting, HttpServletRequest request, String dateString, Model model) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");
        try {
            sighting.setHero(heroDao.getHeroById(Integer.parseInt(heroId)));
            sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        } catch (NumberFormatException ex) {
        }
        try {
            sighting.setDate(Date.valueOf(dateString));
        } catch (IllegalArgumentException ex) {
        }
        violations = validate.validate(sighting);
        if (violations.isEmpty()) {
            sightingDao.updateSighting(sighting);
            violations.clear();
            return "redirect:/sightings";
        } else {
            List<Location> locations = locationDao.getAllLocations();
            List<Hero> heroes = heroDao.getAllHeroes();
            model.addAttribute("sighting", sighting);
            model.addAttribute("locations", locations);
            model.addAttribute("heroes", heroes);
            model.addAttribute("errors", violations);
        }

        return "editSighting";
    }

    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        violations.clear();
        return "sightingDetail";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        violations.clear();
        return "deleteSighting";
    }

    @PostMapping("deleteSighting")
    public String performDeleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }
}
