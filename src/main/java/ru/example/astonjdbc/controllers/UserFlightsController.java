package ru.example.astonjdbc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.example.astonjdbc.dao.UserFlightsDAO;

@Controller
@RequestMapping("/userflights")
public class UserFlightsController {

    private UserFlightsDAO userFlightsDAO;

    @Autowired
    public UserFlightsController(UserFlightsDAO userFlightsDAO) {
        this.userFlightsDAO = userFlightsDAO;
    }

    @GetMapping()
    public String getAllUserFlights(Model model) {
        model.addAttribute("userflights", userFlightsDAO.getAll());
        return "userflights/all";
    }

    @GetMapping("/{id}")
    public String findUserFlights(@PathVariable("id") int id, Model model) {
        model.addAttribute("userFlight", userFlightsDAO.findUserFlights(id));
        return "userflights/find";
    }
}