package pw.testoprog.bookingo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.testoprog.bookingo.models.ServiceType;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {

}
