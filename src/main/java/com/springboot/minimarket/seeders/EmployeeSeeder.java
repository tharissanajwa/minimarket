package com.springboot.minimarket.seeders;

import com.springboot.minimarket.models.Employee;
import com.springboot.minimarket.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeSeeder {
    @Autowired
    private EmployeeRepository employeeRepository;

    public void seed() {
        // Daftar pegawai yang akan disimpan dalam database
        List<Employee> employees = new ArrayList<>(Arrays.asList(
                new Employee("Putri Budiman", "Jl. Bengawan 2", "081234567890"),
                new Employee("Kayla Alzena", "Jl. Anggrek 6", "089579454543"),
                new Employee("Chava Aurorae Hakim", "Jl. Kenanga 8", "089765673854"),
                new Employee("Freya Putri Sabila", "Jl. Majapahit 42", "08781264536"),
                new Employee("Firman Megantara", "Jl. Soekarno Hatta 104", "087845453454"),
                new Employee("Najwa Khairunnisa", "Jl. Kemanisan 1", "089584758743"),
                new Employee("Ahmad Budi Santoso", "Jl. Delima 77", "083174957203"),
                new Employee("Farhan Rosyadi", "Jl. Kenanga 4", "0897343432432"),
                new Employee("Dimas Anggara", "Jl. Hayam Wuruk 5", "08776548905"),
                new Employee("Alexander Jonathan", "Jl. Banda 6", "081285748478")
        ));

        // Cek apakah database sudah berisi data pegawai atau tidak
        if (employeeRepository.findAll().isEmpty()) {
            // Jika tidak ada data, maka simpan data pegawai ke dalam database
            employeeRepository.saveAll(employees);
        }
    }
}
