package pw.testoprog.bookingo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pw.testoprog.bookingo.models.Employee;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository <Employee, UUID>{

}
