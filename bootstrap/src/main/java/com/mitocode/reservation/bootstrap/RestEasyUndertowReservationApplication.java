package com.mitocode.reservation.bootstrap;

import com.mitocode.reservation.adapter.in.rest.gymclass.FindGymClassController;
import com.mitocode.reservation.adapter.in.rest.reservation.CancelReservationController;
import com.mitocode.reservation.adapter.in.rest.reservation.GetReservationsController;
import com.mitocode.reservation.adapter.in.rest.reservation.MakeReservationController;
import com.mitocode.reservation.adapter.out.persistence.inmemory.InMemoryGymClassRepository;
import com.mitocode.reservation.adapter.out.persistence.inmemory.InMemoryReservationRepository;
import com.mitocode.reservation.adapter.out.persistence.jpa.EntityManagerFactoryFactory;
import com.mitocode.reservation.adapter.out.persistence.jpa.JpaGymClassRepository;
import com.mitocode.reservation.adapter.out.persistence.jpa.JpaReservationRepository;
import com.mitocode.reservation.application.port.in.gymclass.FindGymClassUseCase;
import com.mitocode.reservation.application.port.in.reservation.CancelReservationUseCase;
import com.mitocode.reservation.application.port.in.reservation.GetUserReservationsUseCase;
import com.mitocode.reservation.application.port.in.reservation.MakeReservationUseCase;
import com.mitocode.reservation.application.port.out.persistence.GymClassRepository;
import com.mitocode.reservation.application.port.out.persistence.ReservationRepository;
import com.mitocode.reservation.application.service.gymclass.FindGymClassService;
import com.mitocode.reservation.application.service.reservation.CancelReservationService;
import com.mitocode.reservation.application.service.reservation.GetUserReservationsService;
import com.mitocode.reservation.application.service.reservation.MakeReservationService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.core.Application;

import java.util.Set;

public class RestEasyUndertowReservationApplication extends Application {
    private ReservationRepository reservationRepository;
    private GymClassRepository gymClassRepository;

    @Override
    public Set<Object> getSingletons(){
        initPersistenceAdapters();
        return Set.of(
                makeReservationController(),
                cancelReservationController(),
                getReservationsController(),
                findGymClassController()
        );
    }

    private void initPersistenceAdapters(){
        String persistence = System.getProperty("persistence", "inmemory");
        switch (persistence){
            case "inmemory" -> initInMemoryAdapters();
            case "mysql" -> initMysqlAdapters();
            default -> throw new IllegalArgumentException(
                    "Invalid 'persistence' property"
            );
        }
    }

    private void initInMemoryAdapters(){
        gymClassRepository = new InMemoryGymClassRepository();
        reservationRepository = new InMemoryReservationRepository();
    }

    private void initMysqlAdapters(){
        EntityManagerFactory entityManagerFactory =
                EntityManagerFactoryFactory.createMysqlEntityManagerFactory(
                        "jdbc:mysql://localhost:3306/reservation", "root", "test"
                );

        reservationRepository = new JpaReservationRepository(entityManagerFactory);
        gymClassRepository = new JpaGymClassRepository(entityManagerFactory);
    }

    private FindGymClassController findGymClassController(){
        FindGymClassUseCase findGymClassUseCase = new FindGymClassService(gymClassRepository);
        return new FindGymClassController(findGymClassUseCase);
    }

    private MakeReservationController makeReservationController(){
        MakeReservationUseCase makeReservationUseCase = new MakeReservationService(reservationRepository, gymClassRepository);
        return new MakeReservationController(makeReservationUseCase);
    }

    private GetReservationsController getReservationsController(){
        GetUserReservationsUseCase getUserReservationsUseCase = new GetUserReservationsService(reservationRepository);
        return new GetReservationsController(getUserReservationsUseCase);
    }

    private CancelReservationController cancelReservationController(){
        CancelReservationUseCase cancelReservationUseCase = new CancelReservationService(reservationRepository, gymClassRepository);
        return new CancelReservationController(cancelReservationUseCase);
    }
}
