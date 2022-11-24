package com.jd.springboot.repository;

import com.jd.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jd birla on 23-11-2022 at 09:49
 */

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
