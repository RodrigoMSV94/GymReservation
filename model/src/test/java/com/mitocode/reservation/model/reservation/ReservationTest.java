package com.mitocode.reservation.model.reservation;

import com.mitocode.reservation.model.gymclass.GymClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.mitocode.reservation.model.customer.TestCustomerIdFactory.randomCustomerId;
import static com.mitocode.reservation.model.gymclass.TestGymClassFactory.createTestClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationTest {

    @Test
    @DisplayName("given a valid reservation")
    void test1() throws NotEnoughSpotsAvailableException{
        GymClass gymClass = createTestClass(10, 10);
        Reservation reservation = new Reservation(gymClass, randomCustomerId(), 5);
        assertThat(gymClass.spotsAvailable()).isEqualTo(5);
    }

    @Test
    @DisplayName("given a reservation with insufficient spots")
    void test2(){
        GymClass gymClass = createTestClass(10, 2);
        assertThrows(NotEnoughSpotsAvailableException.class, () -> new Reservation(gymClass, randomCustomerId(), 5));
    }

    @Test
    @DisplayName("given a reservation")
    void test3() throws NotEnoughSpotsAvailableException{
        GymClass gymClass = createTestClass(10, 10);
        Reservation reservation = new Reservation(gymClass, randomCustomerId(), 3);
        assertThat(reservation.status()).isEqualTo(ReservationStatus.CONFIRMED);
    }

    @Test
    @DisplayName("given a reservation, cancel it and check status")
    void test4() throws NotEnoughSpotsAvailableException{
        GymClass gymClass = createTestClass(10, 10);
        Reservation reservation = new Reservation(gymClass, randomCustomerId(), 3);
        reservation.cancel();

        assertThat(reservation.status()).isEqualTo(ReservationStatus.CANCELLED);
        assertThat(gymClass.spotsAvailable()).isEqualTo(10);
    }

    @Test
    @DisplayName("given a negative spots value")
    void test5() {
        GymClass gymClass = createTestClass(10, 10);
        assertThrows(IllegalArgumentException.class, () -> new Reservation(gymClass, randomCustomerId(), -1));
    }

}
