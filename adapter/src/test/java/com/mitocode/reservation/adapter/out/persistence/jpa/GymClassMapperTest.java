package com.mitocode.reservation.adapter.out.persistence.jpa;

import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.gymclass.GymClass;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GymClassMapperTest {

    @Test
    void toJpaEntity_mapsAllFieldsCorrectly() {
        GymClass gymClass = new GymClass(new ClassId("123"), "Yoga", "Clase de yoga", 20, 15);

        GymClassJpaEntity entity = GymClassMapper.toJpaEntity(gymClass);

        assertThat(entity.getId()).isEqualTo("123");
        assertThat(entity.getType()).isEqualTo("Yoga");
        assertThat(entity.getDescription()).isEqualTo("Clase de yoga");
        assertThat(entity.getCapacity()).isEqualTo(20);
        assertThat(entity.getSpotsAvailable()).isEqualTo(15);
    }

    @Test
    void toModelEntity_mapsAllFieldsCorrectly() {
        GymClassJpaEntity entity = new GymClassJpaEntity();
        entity.setId("456");
        entity.setType("Pilates");
        entity.setDescription("Clase de pilates");
        entity.setCapacity(10);
        entity.setSpotsAvailable(8);

        GymClass gymClass = GymClassMapper.toModelEntity(entity);

        assertThat(gymClass.id().value()).isEqualTo("456");
        assertThat(gymClass.type()).isEqualTo("Pilates");
        assertThat(gymClass.description()).isEqualTo("Clase de pilates");
        assertThat(gymClass.capacity()).isEqualTo(10);
        assertThat(gymClass.spotsAvailable()).isEqualTo(8);
    }
}
