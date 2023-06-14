package ru.venidiktov.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.venidiktov.model.Measurements;

public interface MeasurementsRepository extends JpaRepository<Measurements, UUID> {
}
