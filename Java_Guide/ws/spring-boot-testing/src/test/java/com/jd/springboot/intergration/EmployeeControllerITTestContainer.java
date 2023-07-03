package com.jd.springboot.intergration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jd.springboot.model.Employee;
import com.jd.springboot.repository.EmployeeRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jd birla on 26-11-2022 at 08:31
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITTestContainer extends AbstractionBaseTest{



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
    }




    @Test
    public void givenEmployee_whenCreateEmployee_thenReturnEmployee() throws Exception {


        //given
        Employee employee = Employee.builder().firstName("Jitu").lastName("Birla").email("jitu@gmail.com").build();

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)));

        //Then
        response.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));

    }

    @Test
    public void givenListOfEmployee_whenFindAllEmployees_thenReturnEmployeeList() throws Exception {
        //given
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(Employee.builder().firstName("Jitu").lastName("Birla").email("jitu@gmail.com").build());
        employeeList.add(Employee.builder().firstName("Jitu1").lastName("Birla1").email("jitu1@gmail.com").build());

        employeeRepository.saveAll(employeeList);
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
        Employee employee = Employee.builder().firstName("Jitu").lastName("Birla").email("jitu@gmail.com").build();

        employeeRepository.save(employee);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", employee.getId()));

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
        Employee employee = Employee.builder().firstName("Jitu").lastName("Birla").email("jitu@gmail.com").build();
        employeeRepository.save(employee);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", empid));

        //Then
        response.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    //Positive scenario
    @Test
    public void givenEmployeeIdAndEmployee_whenUpdate_thenReturnUpdatedEmployee() throws Exception {
        //given

        Employee savedEmployee = Employee.builder().firstName("Jitu").lastName("Birla").email("jitu@gmail.com").build();
        Employee updatedEmployee = Employee.builder().firstName("Jitu1").lastName("Birla1").email("jitu1@gmail.com").build();
        employeeRepository.save(savedEmployee);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", savedEmployee.getId())
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
        Employee savedEmployee = Employee.builder().firstName("Jitu").lastName("Birla").email("jitu@gmail.com").build();
        Employee updatedEmployee = Employee.builder().firstName("Jitu1").lastName("Birla1").email("jitu1@gmail.com").build();

        employeeRepository.save(savedEmployee);

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

        Employee employee = Employee.builder().firstName("Jitu").lastName("Birla").email("jitu@gmail.com").build();

        employeeRepository.save(employee);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}", employee.getId()));

        //Then
        response.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());

    }


}
