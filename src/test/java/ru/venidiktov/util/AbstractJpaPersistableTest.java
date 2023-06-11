package ru.venidiktov.util;

import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.Test;
import ru.venidiktov.model.Sensors;

class AbstractJpaPersistableTest {

    @Test
    void equals_returnTrue_IfObjectReallyEquals() {
        UUID id = UUID.randomUUID();
        Sensors entity_1 = Sensors.builder().name("Сущность 1").build();
        entity_1.setId(id);
        Sensors entity_2 = Sensors.builder().name("Сущность 2").build();
        entity_2.setId(id);
        Sensors entity_3 = Sensors.builder().name("Сущность 3").build();
        entity_3.setId(id);
        Sensors entity_4 = entity_3;

        assertAll(
                () -> assertThat(entity_1.equals(entity_2))
                        .as("Прямое сравнение x.equals(y)").isTrue(),
                () -> assertThat(entity_2.equals(entity_1))
                        .as("Симметричность y.equals(x)").isTrue(),
                () -> assertThat(entity_1.equals(entity_1))
                        .as("Рефлексивность x.equals(x)").isTrue(),
                () -> assertThat(entity_1.equals(entity_2) && entity_2.equals(entity_3))
                        .as("Транзитивность x.equals(y) => y.equals(z)").isTrue(),
                () -> assertThat(entity_1.equals(entity_4)).as("Один и тот же объект").isTrue()
        );
    }

    @Test
    void equals_returnFalse_IfObjectReallyNotEquals() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Sensors entity_1 = Sensors.builder().name("Сущность 1").build();
        entity_1.setId(id1);
        Sensors entity_2 = Sensors.builder().name("Сущность 1").build();
        entity_2.setId(id2);
        Object entity_3 = new Object();
        Sensors entity_4 = Sensors.builder().name("Сущность 1").build();

        assertAll(
                () -> assertThat(entity_1.equals(entity_3))
                        .as("Разные классы").isFalse(),
                () -> assertThat(entity_2.equals(entity_1))
                        .as("Разные id").isFalse(),
                () -> assertThat(entity_4.equals(entity_1))
                        .as("this id = null").isFalse(),
                () -> assertThat(entity_1.equals(null)).as("that = null").isFalse()
        );
    }
}