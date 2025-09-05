package io.zipcoder.persistenceapp.repository;

import io.zipcoder.persistenceapp.entity.Department;
import io.zipcoder.persistenceapp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    // Find department by name
    Optional<Department> findByDepartmentName(String departmentName);
    
    // Find department by manager
    Optional<Department> findByDepartmentManager(Employee manager);
    
    // Check if department name exists (case insensitive)
    @Query("SELECT COUNT(d) > 0 FROM Department d WHERE LOWER(d.departmentName) = LOWER(:name)")
    boolean existsByDepartmentNameIgnoreCase(@Param("name") String departmentName);
}