package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.SuperpowerDao;
import com.sg.superherosightings.entities.Superpower;
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
public class SuperpowerController {


    @Autowired
    SuperpowerDao superpowerDao;

    static Set<ConstraintViolation<Superpower>> violations = new HashSet<>();

    @GetMapping("superpowers")
    public String displaySuperpowers(Model model) {
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("errors", violations);
        return "superpowers";
    }

    @PostMapping("addSuperpower")
    public String addSuperpower(String name, String description) {
        Superpower superpower = new Superpower();
        superpower.setName(name);
        superpower.setDescription(description);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        violations = validate.validate(superpower);
        if (violations.isEmpty()) {
            superpowerDao.addSuperpower(superpower);
        }

        return "redirect:/superpowers";
    }

    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(Integer id, Model model) {
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        violations.clear();
        return "deleteSuperpower";
    }

    @PostMapping("deleteSuperpower")
    public String performDeleteSuperpower(Integer id) {
        superpowerDao.deleteSuperpowerById(id);
        return "redirect:/superpowers";
    }

    @GetMapping("editSuperpower")
    public String editSuperpower(Integer id, Model model) {
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        return "editSuperpower";

    }

    @PostMapping("editSuperpower")
    public String performEditSuperpower(@Valid Superpower superpower, BindingResult result) {
        if (!result.hasErrors()) {
            superpowerDao.updateSuperpower(superpower);
            violations.clear();
            return "redirect:/superpowers";
        }
        return "editSuperpower";
    }

    @GetMapping("superpowerDetail")
    public String superpowerDetail(Integer id, Model model) {
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        violations.clear();
        return "superpowerDetail";
    }
}
