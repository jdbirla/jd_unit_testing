package com.jd.springboot.services;

import com.jd.springboot.model.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Created by jd birla on 24-11-2022 at 07:51
 */
public interface EmployeeService {
    public Employee saveEmployee(Employee employee);

    public List<Employee> findAll();

    public Employee updateEmployee(Employee updatedEmployee);

    public void deleteEmployee(Long id);

    public Optional<Employee> getEmployeeById(Long id);

}
