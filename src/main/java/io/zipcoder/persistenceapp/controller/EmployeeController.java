package io.zipcoder.persistenceapp.controller;

import io.zipcoder.persistenceapp.entity.Employee;
import io.zipcoder.persistenceapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    // GET /api/employees 
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
    
    // GET /api/employees/{id} 
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    // POST /api/employees
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }
    
    // PUT /api/employees/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Optional<Employee> existingEmployee = employeeService.getEmployeeById(id);
        if (existingEmployee.isPresent()) {
            employee.setEmployeeNumber(id);
            Employee updatedEmployee = employeeService.saveEmployee(employee);
            return ResponseEntity.ok(updatedEmployee);
        }
        return ResponseEntity.notFound().build();
    }
    
    // DELETE /api/employees/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // GET /api/employees/{id}/reports 
    @GetMapping("/{id}/reports")
    public ResponseEntity<List<Employee>> getDirectReports(@PathVariable Long id) {
        List<Employee> reports = employeeService.getDirectReports(id);
        return ResponseEntity.ok(reports);
    }
    
    // GET /api/employees/{id}/all-reports
    @GetMapping("/{id}/all-reports")
    public ResponseEntity<List<Employee>> getAllReports(@PathVariable Long id) {
        List<Employee> allReports = employeeService.getAllReportsUnderManager(id);
        return ResponseEntity.ok(allReports);
    }
    
    // GET /api/employees/{id}/manager 
    @GetMapping("/{id}/manager")
    public ResponseEntity<Employee> getDirectManager(@PathVariable Long id) {
        Optional<Employee> manager = employeeService.getDirectManager(id);
        return manager.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    // GET /api/employees/{id}/manager-chain 
    @GetMapping("/{id}/manager-chain")
    public ResponseEntity<List<Employee>> getManagerChain(@PathVariable Long id) {
        List<Employee> managerChain = employeeService.getManagerChain(id);
        return ResponseEntity.ok(managerChain);
    }

    // GET /api/employees/top-managers
    @GetMapping("/top-managers")
    public ResponseEntity<List<Employee>> getTopLevelManagers() {
        List<Employee> topManagers = employeeService.getTopLevelManagers();
        return ResponseEntity.ok(topManagers);
    }
    
    // GET /api/employees/search?email={email}
    @GetMapping("/search")
    public ResponseEntity<Employee> findByEmail(@RequestParam String email) {
        Optional<Employee> employee = employeeService.findByEmail(email);
        return employee.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    // GET /api/employees/{managerId}/team-size
    @GetMapping("/{managerId}/team-size")
    public ResponseEntity<Integer> getTeamSize(@PathVariable Long managerId) {
        int teamSize = employeeService.getTeamSize(managerId);
        return ResponseEntity.ok(teamSize);
    }
}