package com.mitocode.reservation.adapter.in.rest.gymclass;

import com.mitocode.reservation.model.gymclass.GymClass;

public record GymClassInListWebModel(String id, String type, String description, int capacity, int spotsAvailable) {

    public static GymClassInListWebModel fromDomainModel(GymClass gymClass){
        return new GymClassInListWebModel(
                gymClass.id().value(),
                gymClass.type(),
                gymClass.description(),
                gymClass.capacity(),
                gymClass.spotsAvailable()
        );
    }

}
