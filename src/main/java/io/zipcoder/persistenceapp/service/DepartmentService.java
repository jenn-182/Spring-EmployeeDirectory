package io.zipcoder.persistenceapp.service;

import io.zipcoder.persistenceapp.entity.Department;
import io.zipcoder.persistenceapp.entity.Employee;
import io.zipcoder.persistenceapp.repository.DepartmentRepository;
import io.zipcoder.persistenceapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    // Basic CRUD operations
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }
    
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }
    
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
    
    // Business logic methods
    public Optional<Department> findByName(String departmentName) {
        return departmentRepository.findByDepartmentName(departmentName);
    }
    
    public boolean departmentNameExists(String departmentName) {
        return departmentRepository.existsByDepartmentNameIgnoreCase(departmentName);
    }
    
    public Optional<Department> findByManager(Employee manager) {
        return departmentRepository.findByDepartmentManager(manager);
    }
    
    public List<Employee> getDepartmentEmployees(Long departmentId) {
        return employeeRepository.findByDepartmentDepartmentNumber(departmentId);
    }
    
    public int getDepartmentSize(Long departmentId) {
        return employeeRepository.findByDepartmentDepartmentNumber(departmentId).size();
    }
    
    public Department createDepartment(String departmentName, Employee manager) {
        Department department = new Department(departmentName, manager);
        return departmentRepository.save(department);
    }
    
    public Optional<Department> updateDepartmentManager(Long departmentId, Employee newManager) {
        Optional<Department> departmentOpt = departmentRepository.findById(departmentId);
        if (departmentOpt.isPresent()) {
            Department department = departmentOpt.get();
            department.setDepartmentManager(newManager);
            return Optional.of(departmentRepository.save(department));
        }
        return Optional.empty();
    }
}