package com.jd.springboot.repository;

import com.jd.springboot.intergration.AbstractionBaseTest;
import com.jd.springboot.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

/**
 * Created by jd birla on 23-11-2022 at 10:37
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryITTestContainer extends AbstractionBaseTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;
    private Employee employee1;

    @BeforeEach
    private void setup() {
        //Given
        employee = Employee.builder().firstName("Jitendra").lastName("Birla").email("jitendra.birla@gmail.com").build();
        employee1 = Employee.builder().firstName("Jitu").lastName("Birla").email("jitendra@gmail.com").build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);
    }

    @Test
    @DisplayName("Testing employee object insertion")
    public void givenEmployeeObject_whenSave_thenReturnEmployee() {

        //Given
//        Employee employee =  Employee.builder().firstName("Jitendra").lastName("Birla").email("jitendra.birla@gmail.com").build();

        //When
//        employeeRepository.save(employee);

        //Then
        Assertions.assertThat(employee).isNotNull();
        Assertions.assertThat(employee.getId()).isNotNull();
        Assertions.assertThat(employee.getId()).isGreaterThan(0);

    }

    @Test
    @DisplayName("Testing all employees suing find all")
    public void givenAllEmployees_whenFetching_thenReturingAllEmployeeList() {
        //given
//             Employee employee =  Employee.builder().firstName("Jitendra").lastName("Birla").email("jitendra.birla@gmail.com").build();
//             Employee employee1 =  Employee.builder().firstName("Jitu").lastName("Birla").email("jitendra@gmail.com").build();
//             employeeRepository.save(employee);
//             employeeRepository.save(employee1);
        //When
        List<Employee> empList = employeeRepository.findAll();

        //Then
        Assertions.assertThat(empList).isNotEmpty();
        Assertions.assertThat(empList).isNotNull();
        Assertions.assertThat(empList).size().isEqualTo(2);

    }

    @Test
    @DisplayName("Testing an employee using employee id")
    public void givenEmployeeId_whenFetching_thenReturingAnEmployee() {
        //given
//        Employee employee =  Employee.builder().firstName("Jitendra").lastName("Birla").email("jitendra.birla@gmail.com").build();
//        Employee employee1 =  Employee.builder().firstName("Jitu").lastName("Birla").email("jitendra@gmail.com").build();
//        employeeRepository.save(employee);
//        employeeRepository.save(employee1);
        //When
        Employee emp = employeeRepository.findById(employee.getId()).get();

        //Then
        Assertions.assertThat(emp).isNotNull();

    }

    @Test
    @DisplayName("Test employee fetch using index on first name and last name")
    public void givenEmployeeFistLastName_whenFetching_thenReturingAnEmployee() {
        //given
//        Employee employee =  Employee.builder().firstName("Jitendra").lastName("Birla").email("jitendra.birla@gmail.com").build();
//        Employee employee1 =  Employee.builder().firstName("Jitu").lastName("Birla").email("jitendra@gmail.com").build();
//        employeeRepository.save(employee);
//        employeeRepository.save(employee1);
        //When
        Employee emp = employeeRepository.findByFirstLastName("Jitendra", "Birla");
        //Then
        Assertions.assertThat(emp).isNotNull();
        Assertions.assertThat(emp).isEqualTo(employee);

    }

    @Test
    @DisplayName("Test employee fetch using named params on first name and last name")
    public void givenEmployeeFistLastName_whenFetching_thenReturingAnEmployeebyNamedParams() {
        //given
//        Employee employee =  Employee.builder().firstName("Jitendra").lastName("Birla").email("jitendra.birla@gmail.com").build();
//        Employee employee1 =  Employee.builder().firstName("Jitu").lastName("Birla").email("jitendra@gmail.com").build();
//        employeeRepository.save(employee);
//        employeeRepository.save(employee1);
        //When
        Employee emp = employeeRepository.findByNamedParams("Jitendra", "Birla");

        //Then
        Assertions.assertThat(emp).isNotNull();
        Assertions.assertThat(emp).isEqualTo(employee);

    }

    @Test
    @DisplayName("Test employee fetch using Native sql query by index on first name and last name")
    public void givenEmployeeFistLastName_whenFetching_thenReturingAnEmployeebyNativeSQl() {
        //given
//        Employee employee =  Employee.builder().firstName("Jitendra").lastName("Birla").email("jitendra.birla@gmail.com").build();
//        Employee employee1 =  Employee.builder().firstName("Jitu").lastName("Birla").email("jitendra@gmail.com").build();
//        employeeRepository.save(employee);
//        employeeRepository.save(employee1);
        //When
        Employee emp = employeeRepository.findByNativeSql("Jitendra", "Birla");

        //Then
        Assertions.assertThat(emp).isNotNull();
        Assertions.assertThat(emp).isEqualTo(employee);

    }

    @Test
    @DisplayName("Test employee fetch using Native sql query by named paramson first name and last name")
    public void givenEmployeeFistLastName_whenFetching_thenReturingAnEmployeebyNativeSQlbyNamedParams() {
        //given
//        Employee employee =  Employee.builder().firstName("Jitendra").lastName("Birla").email("jitendra.birla@gmail.com").build();
//        Employee employee1 =  Employee.builder().firstName("Jitu").lastName("Birla").email("jitendra@gmail.com").build();
//        employeeRepository.save(employee);
//        employeeRepository.save(employee1);
        //When
        Employee emp = employeeRepository.findByNativeSqlNamedParams("Jitendra", "Birla");

        //Then
        Assertions.assertThat(emp).isNotNull();
        Assertions.assertThat(emp).isEqualTo(employee);

    }

    @Test
    @DisplayName("Test find all employees")
    public void givenEmployeeList_whenFindAll_thenReturingEmployeeList() {
        //given
//        Employee employee =  Employee.builder().firstName("Jitendra").lastName("Birla").email("jitendra.birla@gmail.com").build();
//        Employee employee1 =  Employee.builder().firstName("Jitu").lastName("Birla").email("jitendra@gmail.com").build();
//        employeeRepository.save(employee);
//        employeeRepository.save(employee1);
        //When
        Employee emp = employeeRepository.findByNativeSql("Jitendra", "Birla");

        //Then
        Assertions.assertThat(emp).isNotNull();
        Assertions.assertThat(emp).isEqualTo(employee);

    }

}
