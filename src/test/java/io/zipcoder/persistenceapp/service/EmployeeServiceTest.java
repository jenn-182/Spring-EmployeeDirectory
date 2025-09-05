package io.zipcoder.persistenceapp.service;

import io.zipcoder.persistenceapp.entity.Employee;
import io.zipcoder.persistenceapp.entity.Department;
import io.zipcoder.persistenceapp.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee manager;
    private Employee employee1;
    private Employee employee2;
    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department("Engineering", null);
        department.setDepartmentNumber(1L);

        manager = new Employee("John", "Smith", "Engineering Director", 
                              "555-0101", "john.smith@company.com", 
                              LocalDate.of(2020, 1, 15), null, department);
        manager.setEmployeeNumber(1L);

        employee1 = new Employee("Sarah", "Johnson", "Senior Developer", 
                                "555-0102", "sarah.johnson@company.com", 
                                LocalDate.of(2020, 3, 20), manager, department);
        employee1.setEmployeeNumber(2L);

        employee2 = new Employee("Mike", "Davis", "Developer", 
                                "555-0103", "mike.davis@company.com", 
                                LocalDate.of(2021, 6, 10), employee1, department);
        employee2.setEmployeeNumber(3L);
    }

    @Test
    void getAllEmployees_ShouldReturnAllEmployees() {
        // Given
        List<Employee> expectedEmployees = Arrays.asList(manager, employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(expectedEmployees);

        // When
        List<Employee> actualEmployees = employeeService.getAllEmployees();

        // Then
        assertEquals(3, actualEmployees.size());
        assertEquals(expectedEmployees, actualEmployees);
        verify(employeeRepository).findAll();
    }

    @Test
    void getEmployeeById_WhenEmployeeExists_ShouldReturnEmployee() {
        // Given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(manager));

        // When
        Optional<Employee> result = employeeService.getEmployeeById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(manager, result.get());
        assertEquals("John", result.get().getFirstName());
    }

    @Test
    void getEmployeeById_WhenEmployeeDoesNotExist_ShouldReturnEmpty() {
        // Given
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Employee> result = employeeService.getEmployeeById(999L);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void getDirectReports_ShouldReturnDirectReports() {
        // Given
        List<Employee> directReports = Arrays.asList(employee1);
        when(employeeRepository.findDirectReports(1L)).thenReturn(directReports);

        // When
        List<Employee> result = employeeService.getDirectReports(1L);

        // Then
        assertEquals(1, result.size());
        assertEquals(employee1, result.get(0));
    }

    @Test
    void getTopLevelManagers_ShouldReturnManagersWithoutManager() {
        // Given
        List<Employee> topManagers = Arrays.asList(manager);
        when(employeeRepository.findByManagerIsNull()).thenReturn(topManagers);

        // When
        List<Employee> result = employeeService.getTopLevelManagers();

        // Then
        assertEquals(1, result.size());
        assertEquals(manager, result.get(0));
    }

    @Test
    void saveEmployee_ShouldReturnSavedEmployee() {
        // Given
        when(employeeRepository.save(employee1)).thenReturn(employee1);

        // When
        Employee result = employeeService.saveEmployee(employee1);

        // Then
        assertEquals(employee1, result);
        verify(employeeRepository).save(employee1);
    }

    @Test
    void isManagerOf_WhenEmployeeHasManager_ShouldReturnTrue() {
        // Given
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee1));

        // When
        boolean result = employeeService.isManagerOf(1L, 2L);

        // Then
        assertTrue(result);
    }

    @Test
    void isManagerOf_WhenEmployeeHasDifferentManager_ShouldReturnFalse() {
        // Given
        when(employeeRepository.findById(3L)).thenReturn(Optional.of(employee2));

        // When
        boolean result = employeeService.isManagerOf(1L, 3L);

        // Then
        assertFalse(result);
    }
}