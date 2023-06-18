package ru.venidiktov.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.venidiktov.dto.response.MeasurementRs;
import ru.venidiktov.dto.response.MeasurementSensorRs;
import ru.venidiktov.util.AbstractJpaPersistable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Measurements extends AbstractJpaPersistable<UUID> {

    @Column(nullable = false, precision = 4, scale = 1)
    private Double value;

    @Column(nullable = false)
    private Boolean raining;

    @CreationTimestamp
    @Column(nullable = false, length = 0)
    private LocalDateTime created;

    @Column(nullable = false)
    private String sensorName;

    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "id")
    private Sensors sensor;

    public MeasurementRs toMeasurementRs() {
        return MeasurementRs.builder()
                .value(value)
                .raining(raining)
                .sensor(new MeasurementSensorRs(sensorName))
                .build();
    }
}
