package com.incedo.smart_inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incedo.smart_inventory.entities.Employees;

public interface EmployeeRepository extends JpaRepository<Employees, Integer> {

}
