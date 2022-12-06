package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.SightingDao;
import com.sg.superherosightings.entities.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    SightingDao sightingDao;

    @GetMapping("index")
    public String displayIndex(Model model) {
        List<Sighting> sightings = sightingDao.getTenRecentSightings();
        model.addAttribute("sightings", sightings);
        OrganizationController.violations.clear();
        LocationController.violations.clear();
        HeroController.violations.clear();
        SightingController.violations.clear();
        SuperpowerController.violations.clear();
        return "index";
    }
}