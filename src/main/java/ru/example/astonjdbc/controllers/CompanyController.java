package ru.example.astonjdbc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.example.astonjdbc.dao.CompanyDAO;
import ru.example.astonjdbc.model.Company;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private CompanyDAO companyDAO;

    @Autowired
    public CompanyController(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @GetMapping()
    public String getAllCompanies(Model model) {
        model.addAttribute("companies", companyDAO.getAllCompanies());
        return "companies/all";
    }

    @GetMapping("/{id}")
    public String findCompany(@PathVariable("id") int id, Model model) {
        model.addAttribute("company", companyDAO.findCompany(id));
        return "companies/find";
    }

    @GetMapping("/new")
    public String newCompany(Model model) {
        model.addAttribute("company", new Company());
        return "companies/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("company") Company company) {
        companyDAO.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("company", companyDAO.findCompany(id));
        return "companies/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("company") Company company, @PathVariable("id") int id) {
        companyDAO.update(id, company);
        return "redirect:/companies";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        companyDAO.delete(id);
        return "redirect:/companies";
    }
}