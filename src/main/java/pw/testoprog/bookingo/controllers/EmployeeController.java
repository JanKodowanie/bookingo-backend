package pw.testoprog.bookingo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pw.testoprog.bookingo.models.Employee;
import pw.testoprog.bookingo.services.BookingoEmployeeDetailsService;

@RestController
public class EmployeeController {

    @Autowired
    BookingoEmployeeDetailsService employeeDetailsService;

    @PostMapping("/employee/add")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeDetailsService.addEmployee(employee);
    }
}
