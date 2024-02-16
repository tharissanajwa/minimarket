package com.springboot.minimarket.controllers;

import com.springboot.minimarket.models.Employee;
import com.springboot.minimarket.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// Kelas ini bertindak sebagai controller untuk mengatur permintaan terkait pegawai
@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/welcome")
    public String welcomepage() {
        return "Welcome to Tharissa";
    }

    // Metode untuk mengambil semua data pegawai dari fungsi yg telah dibuat di service
    @GetMapping("/")
    public String getAllEmployee(Model model) {
        List<Employee> employees = employeeService.getAllEmployee();
        if (employees.size() > 0) {
            model.addAttribute("employees", employees);
        }
        return "list-employees";
    }

    // Metode untuk membuat pegawai baru dari fungsi yg telah dibuat di service
    @GetMapping("/add-employee")
    public String addEmployee(Model model) {
        String messages = employeeService.getResponseMessage();
        model.addAttribute("messages", messages);
        model.addAttribute("employee", new Employee());
        return "add-edit-employee";
    }

    @PostMapping("/save-employee")
    public String saveEmployee(Employee employee, Model model) {
        employeeService.insertEmployee(employee);
        String messages = employeeService.getResponseMessage();
        model.addAttribute("messages", messages);
        System.out.println("pesan error : "+messages);
        if (messages.equals("")){
            return "redirect:/";
        } else {
            return "add-edit-employee";
        }

    }

    // Metode untuk memperbarui informasi pegawai dari fungsi yg telah dibuat di service
    @GetMapping("/employee-update/{id}")
    public String getEmployee(Model model, @PathVariable("id") Long id) {
        String messages = employeeService.getResponseMessage();
        model.addAttribute("messages", messages);
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "add-edit-employee";
    }

    // Metode untuk menghapus pegawai berdasarkan dari fungsi yg telah dibuat di service
    @GetMapping("/employee-delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }
}
