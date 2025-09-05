package io.zipcoder.persistenceapp.controller;

import io.zipcoder.persistenceapp.entity.Department;
import io.zipcoder.persistenceapp.entity.Employee;
import io.zipcoder.persistenceapp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;
    
    // GET /api/departments 
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }
    
    // GET /api/departments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Optional<Department> department = departmentService.getDepartmentById(id);
        return department.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }
    
    // POST /api/departments
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.saveDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
    }
    
    // PUT /api/departments/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        Optional<Department> existingDepartment = departmentService.getDepartmentById(id);
        if (existingDepartment.isPresent()) {
            department.setDepartmentNumber(id);
            Department updatedDepartment = departmentService.saveDepartment(department);
            return ResponseEntity.ok(updatedDepartment);
        }
        return ResponseEntity.notFound().build();
    }
    
    // DELETE /api/departments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        Optional<Department> department = departmentService.getDepartmentById(id);
        if (department.isPresent()) {
            departmentService.deleteDepartment(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // GET /api/departments/{id}/employees
    @GetMapping("/{id}/employees")
    public ResponseEntity<List<Employee>> getDepartmentEmployees(@PathVariable Long id) {
        List<Employee> employees = departmentService.getDepartmentEmployees(id);
        return ResponseEntity.ok(employees);
    }

    // GET /api/departments/{id}/size
    @GetMapping("/{id}/size")
    public ResponseEntity<Integer> getDepartmentSize(@PathVariable Long id) {
        int size = departmentService.getDepartmentSize(id);
        return ResponseEntity.ok(size);
    }

    // GET /api/departments/search?name={name}
    @GetMapping("/search")
    public ResponseEntity<Department> findByName(@RequestParam String name) {
        Optional<Department> department = departmentService.findByName(name);
        return department.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }
}