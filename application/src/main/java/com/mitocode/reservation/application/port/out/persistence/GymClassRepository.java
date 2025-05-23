package com.mitocode.reservation.application.port.out.persistence;

import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.gymclass.GymClass;

import java.util.List;
import java.util.Optional;

public interface GymClassRepository {

    List<GymClass> findByTypeOrDescription(String query);

    void save (GymClass gymClass);

    Optional<GymClass> findById(ClassId classId);
}
