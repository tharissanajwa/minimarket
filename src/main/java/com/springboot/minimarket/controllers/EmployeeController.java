package com.springboot.minimarket.controllers;

import com.springboot.minimarket.dto.requests.EmployeeRequest;
import com.springboot.minimarket.dto.responses.EmployeeResponse;
import com.springboot.minimarket.models.ApiResponse;
import com.springboot.minimarket.models.Employee;
import com.springboot.minimarket.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Kelas ini bertindak sebagai controller untuk mengatur permintaan terkait pegawai
@RestController
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
        model.addAttribute("employee", new Employee());
        return "add-edit-employee";
    }

    @PostMapping("/save-employee")
    public String saveEmployee(Employee employee) {
        employeeService.insertEmployee(employee);
        return "redirect:/";
    }

    // Metode untuk memperbarui informasi pegawai dari fungsi yg telah dibuat di service
    @GetMapping("/employee-update/{id}")
    public String getEmployee(Model model, @PathVariable("id") Long id) {
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
