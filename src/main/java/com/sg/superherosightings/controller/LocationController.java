package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.LocationDao;
import com.sg.superherosightings.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class LocationController {

    @Autowired
    LocationDao locationDao;

    static Set<ConstraintViolation<Location>> violations = new HashSet<>();


    @GetMapping("locations")
    public String displayLocations(Integer id, Model model) {
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        Location location = new Location();
        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setCity(request.getParameter("city"));
        location.setState(request.getParameter("state"));
        location.setAddress(request.getParameter("address"));
        location.setZip(request.getParameter("zip"));
        location.setLongitude(request.getParameter("longitude"));
        location.setLatitude(request.getParameter("latitude"));
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        violations = validate.validate(location);
        if (violations.isEmpty()) {
            locationDao.addLocation(location);
        }
        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "deleteLocation";
    }

    @PostMapping("deleteLocation")
    public String performDeleteLocation(Integer id) {
        locationDao.deleteLocationById(id);
        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result) {
        if (!result.hasErrors()) {
            locationDao.updateLocation(location);
            violations.clear();
            return "redirect:/locations";
        }
        return "editLocation";
    }

    @GetMapping("locationDetail")
    public String locationDetail(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        violations.clear();
        return "locationDetail";
    }

}
