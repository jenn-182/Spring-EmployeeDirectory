package io.zipcoder.persistenceapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "DEPARTMENT")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPT_NUM")
    private Long departmentNumber;

    @Column(name = "DEPT_NAME", nullable = false)
    private String departmentName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPT_MANAGER_ID")
    @JsonIgnore
    private Employee departmentManager;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Employee> employees;

    // Default constructor
    public Department() {}

    // Constructor
    public Department(String departmentName, Employee departmentManager) {
        this.departmentName = departmentName;
        this.departmentManager = departmentManager;
    }

    // Getters and Setters
    public Long getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(Long departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Employee getDepartmentManager() {
        return departmentManager;
    }

    public void setDepartmentManager(Employee departmentManager) {
        this.departmentManager = departmentManager;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentNumber=" + departmentNumber +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}