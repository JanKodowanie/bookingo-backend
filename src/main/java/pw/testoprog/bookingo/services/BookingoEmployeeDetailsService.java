package pw.testoprog.bookingo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.testoprog.bookingo.models.Employee;
import pw.testoprog.bookingo.repositories.EmployeeRepository;

@Service
public class BookingoEmployeeDetailsService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
}
