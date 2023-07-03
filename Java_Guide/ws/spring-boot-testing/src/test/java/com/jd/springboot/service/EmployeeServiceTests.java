package com.jd.springboot.service;

import static org.mockito.ArgumentMatchers.any;

import com.jd.springboot.exception.ResurceNotFound;
import com.jd.springboot.model.Employee;
import com.jd.springboot.repository.EmployeeRepository;
import com.jd.springboot.services.Impl.EmployeeServiceImpl;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


/**
 * Created by jd birla on 24-11-2022 at 08:35
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeServiceImpl employeeService;
    private Employee e;
    private Employee employee1;

    @BeforeEach
    public void setup() {
        // employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
        e = Employee.builder().id(1L).firstName("Jitu").lastName("Birla").email("jdbirla@gmail.com").build();
        employee1 = Employee.builder().id(2L).firstName("Jitu").lastName("Birla").email("jitendra@gmail.com").build();

    }

    @Test
    @DisplayName("Test save employee using serviceimpl")
    public void givenEmployee_whenSaveEmployee_thenEmployeeObject() {
        //given
        BDDMockito.given(employeeRepository.findByEmail(e.getEmail())).willReturn(Optional.empty());
        BDDMockito.given(employeeService.saveEmployee(e)).willReturn(e);

        //When
        Employee savedEmp = employeeService.saveEmployee(e);
        System.out.println(savedEmp.toString());
        //Then
        Assertions.assertThat(savedEmp).isNotNull();
    }

    @Test
    @DisplayName("Test by given existing email and returning resurce not found exception")
    public void givenExistingEmail_whenSaveEmployee_thenThrowRSNF() {
        //given
        BDDMockito.given(employeeRepository.findByEmail(e.getEmail())).willReturn(Optional.of(e));
        //BDDMockito.given(employeeService.saveEmployee(e)).willReturn(e);

        //When

        org.junit.jupiter.api.Assertions.assertThrows(ResurceNotFound.class, () -> {
            employeeService.saveEmployee(e);
        });

        //Then
        BDDMockito.verify(employeeRepository, Mockito.never()).save(any(Employee.class));

    }

    @Test
    @DisplayName("Test find all employees")
    public void givenEmployeeList_whenFindAll_thenReturningEmployeeList() {
        //given
        BDDMockito.given(employeeRepository.findAll()).willReturn(List.of(e, employee1));


        //When
        List<Employee> empList = employeeService.findAll();
        //Then
        Assertions.assertThat(empList).isNotNull();
        Assertions.assertThat(empList).size().isEqualTo(2);
    }


    @Test
    @DisplayName("Test update employee ")
    public void givenEmployee_whenUpdateEmployee_thenReturningEmployee() {
        //given
        BDDMockito.given(employeeRepository.save(e)).willReturn(e);
        e.setFirstName("shivam");
        e.setLastName("karode");
        //When
        Employee employee = employeeService.updateEmployee(e);
        //Then
        Assertions.assertThat(employee).isNotNull();
        Assertions.assertThat(employee.getFirstName()).isEqualTo("shivam");

    }

    @Test
    @DisplayName("Test delete employee ")
    public void givenEmployeeId_whenDeleteEmployee_thenReturningNOting() {
        long employeeid = 1L;
        //given
        BDDMockito.willDoNothing().given(employeeRepository).deleteById(employeeid);
        //When
         employeeService.deleteEmployee(employeeid);
        //Then
      BDDMockito.verify(employeeRepository,Mockito.times(1)).deleteById(employeeid);

    }

}
