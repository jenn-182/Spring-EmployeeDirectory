package io.zipcoder.persistenceapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Employee Directory API");
        response.put("version", "1.0");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("All Employees", "/api/employees");
        endpoints.put("All Departments", "/api/departments");
        endpoints.put("Employee by ID", "/api/employees/{id}");
        endpoints.put("Department by ID", "/api/departments/{id}");
        endpoints.put("Employee Reports", "/api/employees/{id}/reports");
        endpoints.put("Top Managers", "/api/employees/top-managers");
        endpoints.put("Department Employees", "/api/departments/{id}/employees");
        endpoints.put("H2 Console", "/h2-console");
        
        response.put("endpoints", endpoints);
        return response;
    }
}