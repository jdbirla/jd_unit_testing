package com.jd.springboot.services.Impl;

import com.jd.springboot.exception.ResurceNotFound;
import com.jd.springboot.model.Employee;
import com.jd.springboot.repository.EmployeeRepository;
import com.jd.springboot.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by jd birla on 24-11-2022 at 07:58
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> savedEmp = employeeRepository.findByEmail(employee.getEmail());
        if(savedEmp.isPresent())
        {
            throw new ResurceNotFound("Employee already present with this email id:"+employee.getEmail());
        }
        return employeeRepository.save(employee);

    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee) {
        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
}
