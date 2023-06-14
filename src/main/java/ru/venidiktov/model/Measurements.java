package ru.venidiktov.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.venidiktov.util.AbstractJpaPersistable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Measurements extends AbstractJpaPersistable<UUID> {

    private Double value;

    private Boolean raining;

    private String sensorName;

    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "id")
    private Sensors sensor;

}
