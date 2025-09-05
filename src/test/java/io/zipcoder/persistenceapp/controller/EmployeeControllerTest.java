package io.zipcoder.persistenceapp.controller;

import io.zipcoder.persistenceapp.entity.Employee;
import io.zipcoder.persistenceapp.entity.Department;
import io.zipcoder.persistenceapp.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;
    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department("Engineering", null);
        department.setDepartmentNumber(1L);

        employee1 = new Employee("John", "Smith", "Engineering Director", 
                                "555-0101", "john.smith@company.com", 
                                LocalDate.of(2020, 1, 15), null, department);
        employee1.setEmployeeNumber(1L);

        employee2 = new Employee("Sarah", "Johnson", "Senior Developer", 
                                "555-0102", "sarah.johnson@company.com", 
                                LocalDate.of(2020, 3, 20), employee1, department);
        employee2.setEmployeeNumber(2L);
    }

    @Test
    void getAllEmployees_ShouldReturnEmployeeList() throws Exception {
        // Given
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        // When & Then
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Smith")))
                .andExpect(jsonPath("$[1].firstName", is("Sarah")))
                .andExpect(jsonPath("$[1].lastName", is("Johnson")));
    }

    @Test
    void getEmployeeById_WhenEmployeeExists_ShouldReturnEmployee() throws Exception {
        // Given
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employee1));

        // When & Then
        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Smith")))
                .andExpect(jsonPath("$.email", is("john.smith@company.com")));
    }

    @Test
    void getEmployeeById_WhenEmployeeDoesNotExist_ShouldReturn404() throws Exception {
        // Given
        when(employeeService.getEmployeeById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/employees/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getDirectReports_ShouldReturnReportsList() throws Exception {
        // Given
        List<Employee> reports = Arrays.asList(employee2);
        when(employeeService.getDirectReports(1L)).thenReturn(reports);

        // When & Then
        mockMvc.perform(get("/api/employees/1/reports"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("Sarah")));
    }

    @Test
    void getTopLevelManagers_ShouldReturnManagersList() throws Exception {
        // Given
        List<Employee> managers = Arrays.asList(employee1);
        when(employeeService.getTopLevelManagers()).thenReturn(managers);

        // When & Then
        mockMvc.perform(get("/api/employees/top-managers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Engineering Director")));
    }
}