package com.mitocode.reservation.adapter.in.rest.reservation;

import com.mitocode.reservation.application.port.in.reservation.GetUserReservationsUseCase;
import com.mitocode.reservation.model.customer.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mitocode.reservation.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class GetReservationsController {
    private final GetUserReservationsUseCase getUserReservationsUseCase;

    @PreAuthorize("hasRole('ROLE_spring-keycloak-client_view-reservation')")
    @GetMapping("/{customerId}")
    public List<ReservationWebModel> getUserReservations(@PathVariable("customerId") String customerIdString){
        CustomerId customerId = parseCustomerId(customerIdString);
        return getUserReservationsUseCase.getReservations(customerId)
                .stream()
                .map(ReservationWebModel::fromDomainModel)
                .toList();
    }
}
