package com.jd.springboot.repository;

import com.jd.springboot.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Created by jd birla on 23-11-2022 at 10:37
 */
@DataJpaTest
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Testing employee object insertion")
    public void givenEmployeeObject_whenSave_thenReturnEmployee()
    {

        //Given
        Employee employee =  Employee.builder().firstName("Jitendra").lastName("Birla").email("jitendra.birla@gmail.com").build();

        //When
        employeeRepository.save(employee);

        //Then
        Assertions.assertThat(employee).isNotNull();
        Assertions.assertThat(employee.getId()).isNotNull();
        Assertions.assertThat(employee.getId()).isGreaterThan(0);

    }


}
