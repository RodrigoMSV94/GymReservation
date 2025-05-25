package com.mitocode.reservation.adapter.out.persistence.jpa;

import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.gymclass.GymClass;
import com.mitocode.reservation.model.reservation.Reservation;
import com.mitocode.reservation.model.reservation.ReservationStatus;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationMapperTest {

    @Test
    void toJpaEntity_mapsAllFieldsCorrectly() {
        GymClass gymClass = new GymClass(new ClassId("1"), "Yoga", "Clase", 10, 5);
        Reservation reservation = new Reservation(gymClass, new CustomerId("test@correo.com"), 2, ReservationStatus.CONFIRMED);

        ReservationJpaEntity entity = ReservationMapper.toJpaEntity(reservation);

        assertThat(entity.getCustomerId()).isEqualTo("test@correo.com");
        assertThat(entity.getSpotsReserved()).isEqualTo(2);
        assertThat(entity.getStatus()).isEqualTo(ReservationStatus.CONFIRMED);
        assertThat(entity.getGymClass().getId()).isEqualTo("1");
    }

    @Test
    void toModelEntityOptional_mapsAllFieldsCorrectly() {
        GymClassJpaEntity gymClassEntity = new GymClassJpaEntity();
        gymClassEntity.setId("2");
        gymClassEntity.setType("Pilates");
        gymClassEntity.setDescription("Clase pilates");
        gymClassEntity.setCapacity(8);
        gymClassEntity.setSpotsAvailable(3);

        ReservationJpaEntity entity = new ReservationJpaEntity();
        entity.setCustomerId("otro@correo.com");
        entity.setSpotsReserved(1);
        entity.setStatus(ReservationStatus.CANCELLED);
        entity.setGymClass(gymClassEntity);

        Optional<Reservation> reservationOpt = ReservationMapper.toModelEntityOptional(entity);

        assertThat(reservationOpt).isPresent();
        Reservation reservation = reservationOpt.get();
        assertThat(reservation.customerId().email()).isEqualTo("otro@correo.com");
        assertThat(reservation.spotsReserved()).isEqualTo(1);
        assertThat(reservation.status()).isEqualTo(ReservationStatus.CANCELLED);
        assertThat(reservation.gymClass().id().value()).isEqualTo("2");
        assertThat(reservation.gymClass().type()).isEqualTo("Pilates");
    }
}
