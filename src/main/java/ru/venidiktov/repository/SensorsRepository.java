package ru.venidiktov.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.venidiktov.model.Sensors;

public interface SensorsRepository extends JpaRepository<Sensors, UUID> {
    Optional<Sensors> findByNameIgnoreCase(String name);
}
