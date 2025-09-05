package io.zipcoder.persistenceapp.service;

import io.zipcoder.persistenceapp.entity.Employee;
import io.zipcoder.persistenceapp.entity.Department;
import io.zipcoder.persistenceapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    // Basic CRUD operations
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    
    // Business logic methods
    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentDepartmentNumber(departmentId);
    }
    
    public List<Employee> getDirectReports(Long managerId) {
        return employeeRepository.findDirectReports(managerId);
    }
    
    public Optional<Employee> getDirectManager(Long employeeId) {
        return employeeRepository.findDirectManager(employeeId);
    }
    
    public List<Employee> getTopLevelManagers() {
        return employeeRepository.findByManagerIsNull();
    }
    
    // Complex hierarchy operations
    public List<Employee> getAllReportsUnderManager(Long managerId) {
        List<Employee> allReports = new ArrayList<>();
        collectAllReports(managerId, allReports);
        return allReports;
    }
    
    private void collectAllReports(Long managerId, List<Employee> allReports) {
        List<Employee> directReports = employeeRepository.findDirectReports(managerId);
        for (Employee employee : directReports) {
            allReports.add(employee);
            // Recursively get reports of this employee
            collectAllReports(employee.getEmployeeNumber(), allReports);
        }
    }
    
    public List<Employee> getManagerChain(Long employeeId) {
        List<Employee> managerChain = new ArrayList<>();
        Optional<Employee> currentEmployee = employeeRepository.findById(employeeId);
        
        while (currentEmployee.isPresent() && currentEmployee.get().getManager() != null) {
            Employee manager = currentEmployee.get().getManager();
            managerChain.add(manager);
            currentEmployee = Optional.of(manager);
        }
        
        return managerChain;
    }
    
    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
    
    public boolean isManagerOf(Long managerId, Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        return employee.isPresent() && 
               employee.get().getManager() != null && 
               employee.get().getManager().getEmployeeNumber().equals(managerId);
    }
    
    public int getTeamSize(Long managerId) {
        return getAllReportsUnderManager(managerId).size();
    }
}