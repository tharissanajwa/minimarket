package com.springboot.minimarket.repositories;

import com.springboot.minimarket.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByDeletedAtIsNullOrderByName();
    Optional<Employee> findByIdAndDeletedAtIsNull(Long id);
}
