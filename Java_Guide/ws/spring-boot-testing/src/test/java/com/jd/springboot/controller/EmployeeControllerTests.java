package com.jd.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jd.springboot.model.Employee;
import com.jd.springboot.services.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by jd birla on 25-11-2022 at 06:50
 */
@WebMvcTest
public class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployee_whenCreateEmployee_thenReturnEmployee() throws Exception {
        //given
        Employee employee = Employee.builder().firstName("Jitu").lastName("Birla").email("jitu@gmail.com").build();

        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation -> invocation.getArgument(0)));

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)));

        //Then
        response.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName()))).andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName()))).andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));

    }

    @Test
    public void givenListOfEmployee_whenFindAllEmployees_thenReturnEmployeeList() throws Exception {
        //given
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(Employee.builder().firstName("Jitu").lastName("Birla").email("jitu@gmail.com").build());
        employeeList.add(Employee.builder().firstName("Jitu1").lastName("Birla1").email("jitu1@gmail.com").build());

        BDDMockito.given(employeeService.findAll()).willReturn(employeeList);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"));

        //Then
        response.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(employeeList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", CoreMatchers.is("Jitu")));


    }

    //Positive scenario
    @Test
    public void givenEmployeeId_whenFindById_thenReturnEmployee() throws Exception {
        //given
        long empid = 1L;
        Employee employee = Employee.builder().id(1L).firstName("Jitu").lastName("Birla").email("jitu@gmail.com").build();

        BDDMockito.given(employeeService.getEmployeeById(empid)).willReturn(Optional.of(employee));

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", empid));

        //Then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));


    }

    //Negative scenario
    @Test
    public void givenEmployeeId_whenFindById_thenReturnNotFound() throws Exception {
        //given
        long empid = 1L;
        Employee employee = Employee.builder().id(1L).firstName("Jitu").lastName("Birla").email("jitu@gmail.com").build();

        BDDMockito.given(employeeService.getEmployeeById(empid)).willReturn(Optional.empty());

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", empid));

        //Then
        response.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    //Positive scenario
    @Test
    public void givenEmployeeIdAndEmployee_whenUpdate_thenReturnUpdatedEmployee() throws Exception {
        //given
        long empid = 1L;
        Employee savedEmployee = Employee.builder().id(1L).firstName("Jitu").lastName("Birla").email("jitu@gmail.com").build();
        Employee updatedEmployee = Employee.builder().id(1L).firstName("Jitu1").lastName("Birla1").email("jitu1@gmail.com").build();

        BDDMockito.given(employeeService.getEmployeeById(empid)).willReturn(Optional.of(savedEmployee));
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation -> invocation.getArgument(0)));

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", empid)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedEmployee)));


        //Then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(updatedEmployee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(updatedEmployee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(updatedEmployee.getEmail())));

    }

    //Ngative scenario
    @Test
    public void givenEmployeeIdAndEmployee_whenUpdate_thenReturnNotFound() throws Exception {
        //given
        long empid = 1L;
        Employee savedEmployee = Employee.builder().id(1L).firstName("Jitu").lastName("Birla").email("jitu@gmail.com").build();
        Employee updatedEmployee = Employee.builder().id(1L).firstName("Jitu1").lastName("Birla1").email("jitu1@gmail.com").build();

        BDDMockito.given(employeeService.getEmployeeById(empid)).willReturn(Optional.empty());
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation -> invocation.getArgument(0)));

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", empid)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedEmployee)));


        //Then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());


    }


    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        //given
        long empid = 1L;
        Employee employee = Employee.builder().id(1L).firstName("Jitu").lastName("Birla").email("jitu@gmail.com").build();

        BDDMockito.willDoNothing().given(employeeService).deleteEmployee(empid);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}", empid));

        //Then
        response.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());

    }

}
