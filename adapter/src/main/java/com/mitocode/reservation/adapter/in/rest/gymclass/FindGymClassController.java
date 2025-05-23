package com.mitocode.reservation.adapter.in.rest.gymclass;


import com.mitocode.reservation.application.port.in.gymclass.FindGymClassUseCase;
import com.mitocode.reservation.model.gymclass.GymClass;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.mitocode.reservation.adapter.in.rest.common.ControllerCommons.clientErrorException;

@Path("/gym-classes")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class FindGymClassController {
    private final FindGymClassUseCase findGymClassUseCase;

    @GET
    public List<GymClassInListWebModel> findGymClasses(@QueryParam("query") String query){
        if(query == null){
            throw  clientErrorException(Response.Status.BAD_REQUEST, "Missing 'query'");
        }

        List<GymClass> gymClasses;
        try{
            gymClasses = findGymClassUseCase.findByTypeOrDescription(query);
        }catch (IllegalArgumentException e){
            throw clientErrorException(Response.Status.BAD_REQUEST, "Invalid 'query'");
        }

        return gymClasses.stream().map(GymClassInListWebModel::fromDomainModel).toList();
    }

}
