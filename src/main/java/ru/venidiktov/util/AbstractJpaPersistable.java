package ru.venidiktov.util;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 * Данный класс сделан из идей того что при сравнении моделей для БД стоит использовать только id
 * Подсказал Леонид Балихин, спасибо ему
 * Статьи на тему
 * Хорошее объяснение причин https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
 * Готовое решение из офф документации https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/domain/AbstractPersistable.html
 * */

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractJpaPersistable<T extends Serializable>  {

    // TODO: 5/20/2023 Со стратегией IDENTITY не работает id всегда Null даже если его передать,
    //  вроде как эта стратегия ориентируется на механизм генерации в БД который задан для поля, а я его не указал
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private T id;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        if(this.id == null) return false;
        AbstractJpaPersistable<?> that = (AbstractJpaPersistable<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return String.format("Entity of type: %s with id: %s", this.getClass().getSimpleName(), getId().toString());
    }
}
