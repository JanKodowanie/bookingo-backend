package pw.testoprog.bookingo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pw.testoprog.bookingo.models.ServiceType;
import pw.testoprog.bookingo.repositories.ServiceTypeRepository;

@RestController
public class ServiceTypeController {
    private final ServiceTypeRepository repository;

    ServiceTypeController(ServiceTypeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/service-types")
    List<ServiceType> all() {
        return repository.findAll();
    }

    @PostMapping("/service-types")
    ServiceType newServiceType(@RequestBody ServiceType newServiceType) {
        return repository.save(newServiceType);
    }

    @PutMapping("/service-types/{id}")
    ServiceType replaceServiceType(@RequestBody ServiceType newServiceType, @PathVariable Long id) {
        return repository.findById(id)
                .map(serviceType -> {
                    serviceType.setName(newServiceType.getName());
                    return repository.save(serviceType);
                })
                .orElseGet(() -> {
                    newServiceType.setId(id);
                    return repository.save(newServiceType);
                });
    }

    @DeleteMapping("/service-types/{id}")
    void deleteServiceTypes(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
