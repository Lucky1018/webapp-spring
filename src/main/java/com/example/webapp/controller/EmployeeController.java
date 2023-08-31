package com.example.webapp.controller;

import com.example.webapp.model.Employee;
import com.example.webapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Employee> lisEmployee = service.getEmployees();
        model.addAttribute("employees", lisEmployee);
        return "home";
    }

    @GetMapping("/createEmployee")
    public String createEmployee(Model model) {
        Employee e = new Employee();
        model.addAttribute("employee", e);
        return "formNewEmployee";

    }

    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable("id") final int id, Model model) {
        Employee e = service.getEmployee(id);
        model.addAttribute("employee", e);
        return "formUpdateEmployee";

    }

    @GetMapping("/deleteEmployee/{id}")
    public ModelAndView deleteEmployee(@PathVariable("id") final int id) {
        service.deleteEmployee(id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/saveEmployee")
    public ModelAndView saveEmployee(@ModelAttribute Employee employee) {
        if (employee.getId() != null) {
            Employee currentEmployee = service.getEmployee(employee.getId());
            employee.setPassword(currentEmployee.getPassword());
        }
        service.saveEmployee(employee);
        return new ModelAndView("redirect:/");
    }
}
