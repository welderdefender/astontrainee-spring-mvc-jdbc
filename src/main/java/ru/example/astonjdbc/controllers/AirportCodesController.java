package ru.example.astonjdbc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.example.astonjdbc.dao.AirportCodesDAO;
import ru.example.astonjdbc.model.AirportCodes;

@Controller
@RequestMapping("/codes")
public class AirportCodesController {

    private AirportCodesDAO airportCodesDAO;

    @Autowired
    public AirportCodesController(AirportCodesDAO airportCodesDAO) {
        this.airportCodesDAO = airportCodesDAO;
    }

    @GetMapping()
    public String getAllCodes(Model model) {
        model.addAttribute("codes", airportCodesDAO.getAllCodes());
        return "codes/all";
    }

    @GetMapping("/{id}")
    public String findCode(@PathVariable("id") int id, Model model) {
        model.addAttribute("airportCodes", airportCodesDAO.findAirportCode(id));
        return "codes/find";
    }

    @GetMapping("/new")
    public String newCode(Model model) {
        model.addAttribute("airportCodes", new AirportCodes());
        return "codes/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("airportCodes") AirportCodes airportCodes) {
        airportCodesDAO.save(airportCodes);
        return "redirect:/codes";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("airportCodes", airportCodesDAO.findAirportCode(id));
        return "codes/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("airportCodes") AirportCodes airportCodes, @PathVariable("id") int id) {
        airportCodesDAO.update(id, airportCodes);
        return "redirect:/codes";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        airportCodesDAO.delete(id);
        return "redirect:/codes";
    }
}