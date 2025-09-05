package io.zipcoder.persistenceapp.repository;

import io.zipcoder.persistenceapp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // Find employees by manager
    List<Employee> findByManager(Employee manager);
    
    // Find employees by manager ID
    List<Employee> findByManagerEmployeeNumber(Long managerId);
    
    // Find employees without a manager
    List<Employee> findByManagerIsNull();
    
    // Find employees by department
    List<Employee> findByDepartmentDepartmentNumber(Long departmentNumber);
    
    // Find employee by email
    Optional<Employee> findByEmail(String email);
    
    // Simple query to get direct manager
    @Query("SELECT e.manager FROM Employee e WHERE e.employeeNumber = :employeeId")
    Optional<Employee> findDirectManager(@Param("employeeId") Long employeeId);
    
    // Get all direct reports for a manager
    @Query("SELECT e FROM Employee e WHERE e.manager.employeeNumber = :managerId")
    List<Employee> findDirectReports(@Param("managerId") Long managerId);
}