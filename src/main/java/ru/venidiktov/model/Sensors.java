package ru.venidiktov.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.venidiktov.util.AbstractJpaPersistable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Sensors extends AbstractJpaPersistable<UUID> {

    private String name;

    private LocalDateTime registrationDate;

    public Sensors(String name) {
        this.name = name;
    }

    @PrePersist
    public void prePersist() {
        if(registrationDate == null) {
            registrationDate = LocalDateTime.now();
        }
    }
}
