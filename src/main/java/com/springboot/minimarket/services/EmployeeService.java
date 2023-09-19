package com.springboot.minimarket.services;

import com.springboot.minimarket.dto.requests.EmployeeRequest;
import com.springboot.minimarket.dto.responses.EmployeeResponse;
import com.springboot.minimarket.models.Employee;
import com.springboot.minimarket.repositories.EmployeeRepository;
import com.springboot.minimarket.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    // Pesan status untuk memberi informasi kepada pengguna
    private String responseMessage;

    // Metode untuk mendapatkan pesan status
    public String getResponseMessage() {
        return responseMessage;
    }

    // Metode untuk mendapatkan semua daftar pegawai yang belum terhapus melalui repository
    public Page<EmployeeResponse> getAllEmployee(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Employee> result = employeeRepository.findAllByDeletedAtIsNullOrderByName(pageable);
        List<EmployeeResponse> responses = new ArrayList<>();
        if (result.isEmpty()) {
            responseMessage = Utility.message("data_doesnt_exists");
        } else {
            for (Employee employee : result.getContent()) {
                EmployeeResponse employeeResponse = new EmployeeResponse(employee);
                responses.add(employeeResponse);
            }
            responseMessage = Utility.message("data_displayed");
        }
        return new PageImpl<>(responses, PageRequest.of(page, limit), result.getTotalElements());
    }

    // Metode untuk mendapatkan data pegawai berdasarkan id melalui repository
    public Employee getEmployeeById(Long id) {
        Optional<Employee> result = employeeRepository.findByIdAndDeletedAtIsNull(id);
        if (!result.isPresent()) {
            responseMessage = Utility.message("employee_not_found");
            return null;
        }
        responseMessage = Utility.message("data_displayed");
        return result.get();
    }

    // Metode untuk mendapatkan data pegawai melalui response berdasarkan id melalui repository
    public EmployeeResponse getEmployeeByIdResponse(Long id) {
        EmployeeResponse response = null;
        Employee employee = getEmployeeById(id);
        if (employee != null) {
            response = new EmployeeResponse(employee);
        }
        return response;
    }

    // Metode untuk menambahkan pegawai ke dalam data melalui repository
    public EmployeeResponse insertEmployee(EmployeeRequest employeeRequest) {
        EmployeeResponse response = null;
        Employee result = new Employee();
        String name = Utility.inputTrim(employeeRequest.getName());
        String address = Utility.inputTrim(employeeRequest.getAddress());
        String phoneNumber = employeeRequest.getPhoneNumber();

        if (!inputValidation(name, phoneNumber).isEmpty()) {
            responseMessage = inputValidation(name, phoneNumber);
        } else {
            result.setName(name);
            result.setAddress(address);
            result.setPhoneNumber(Utility.phoneTrim(phoneNumber));
            employeeRepository.save(result);
            response = new EmployeeResponse(result);
            responseMessage = Utility.message("data_added");
        }
        return response;
    }

    // Metode untuk memperbarui informasi pegawai melalui repository
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest) {
        EmployeeResponse response = null;
        Employee result = getEmployeeById(id);
        String name = Utility.inputTrim(employeeRequest.getName());
        String address = Utility.inputTrim(employeeRequest.getAddress());
        String phoneNumber = employeeRequest.getPhoneNumber();

        if (!inputValidation(name, phoneNumber).isEmpty()) {
            responseMessage = inputValidation(name, phoneNumber);
        } else if (result != null){
            result.setName(name);
            result.setAddress(address);
            result.setPhoneNumber(Utility.phoneTrim(phoneNumber));
            employeeRepository.save(result);
            response = new EmployeeResponse(result);
            responseMessage = Utility.message("data_updated");
        }
        return response;
    }

    // Metode untuk menghapus data pegawai secara soft delete melalui repository
    public boolean deleteEmployee(Long id) {
        boolean result = false;
        Employee employee = getEmployeeById(id);
        if (employee != null) {
            employee.setDeletedAt(new Date());
            employeeRepository.save(employee);
            result = true;
            responseMessage = Utility.message("data_deleted");
        }
        return result;
    }

    // Metode untuk memvalidasi inputan pengguna
    private String inputValidation(String name, String phoneNumber) {
        String result = "";
        if (Utility.inputCheck(name) == 1) {
            result = "Sorry, employee name cannot be blank.";
        }
        if (Utility.inputCheck(name) == 2) {
            result = "Sorry, employee name can only filled by letters and numbers";
        }
        System.out.println(Utility.phoneTrim(phoneNumber).length());
        if (Utility.inputCheck(phoneNumber) == 1) {
            result = "Sorry, employee phone cannot be blank.";
        } else if (!Utility.phoneTrim(phoneNumber).matches("^\\d+$") || !(Utility.phoneTrim(phoneNumber).length() >= 10 && Utility.phoneTrim(phoneNumber).length() <= 13)) {
            result = "Invalid phone number. Please enter a valid phone number.";
        }
        return result;
    }
}
