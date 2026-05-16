package com.challenge.api.repository;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImpl;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {

    private final Map<UUID, Employee> database = new ConcurrentHashMap<>();

    public EmployeeRepository() {
        // Seed mock data for demonstration
        UUID seedId1 = UUID.randomUUID();
        EmployeeImpl emp1 = new EmployeeImpl();
        emp1.setUuid(seedId1);
        emp1.setFirstName("Shreyash");
        emp1.setLastName("Ghanekar");
        emp1.setFullName("Shreyash Ghanekar");
        emp1.setAge(22);
        emp1.setSalary(150000);
        emp1.setJobTitle("Software Engineer");
        emp1.setEmail("shreyash@gmail.com");
        emp1.setContractHireDate(Instant.now());
        database.put(seedId1, emp1);
    }

    public List<Employee> findAll() {
        return new ArrayList<>(database.values());
    }

    public Optional<Employee> findByUuid(UUID uuid) {
        return Optional.ofNullable(database.get(uuid));
    }

    public Employee save(Employee employee) {
        if (employee.getUuid() == null) {
            employee.setUuid(UUID.randomUUID());
        }
        database.put(employee.getUuid(), employee);
        return employee;
    }
}