package ru.example.astonjdbc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.example.astonjdbc.dao.FlightDAO;
import ru.example.astonjdbc.model.Flight;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private FlightDAO flightDAO;

    @Autowired
    public FlightController(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }

    @GetMapping()
    public String getAllFlights(Model model) {
        model.addAttribute("flights", flightDAO.getAllFlights());
        return "flights/all";
    }

    @GetMapping("/{id}")
    public String findFlight(@PathVariable("id") int id, Model model) {
        model.addAttribute("flight", flightDAO.findFlight(id));
        return "flights/find";
    }

    @GetMapping("/new")
    public String newFlight(Model model) {
        model.addAttribute("flight", new Flight());
        return "flights/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("flight") Flight flight) {
        flightDAO.save(flight);
        return "redirect:/flights";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("flight", flightDAO.findFlight(id));
        return "flights/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("flight") Flight flight, @PathVariable("id") int id) {
        flightDAO.update(id, flight);
        return "redirect:/flights";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        flightDAO.delete(id);
        return "redirect:/flights";
    }
}