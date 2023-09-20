package com.springboot.minimarket.repositories;

import com.springboot.minimarket.models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAllByDeletedAtIsNullOrderByName(Pageable pageable);
    Optional<Employee> findByIdAndDeletedAtIsNull(Long id);
}
