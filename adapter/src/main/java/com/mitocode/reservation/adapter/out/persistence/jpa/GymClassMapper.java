package com.mitocode.reservation.adapter.out.persistence.jpa;

import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.gymclass.GymClass;

import java.util.List;
import java.util.Optional;

public class GymClassMapper {

    private GymClassMapper(){}

    static GymClassJpaEntity toJpaEntity(GymClass gymClass){
        GymClassJpaEntity jpaEntity = new GymClassJpaEntity();
        jpaEntity.setId(gymClass.id().value());
        jpaEntity.setType(gymClass.type());
        jpaEntity.setDescription(gymClass.description());
        jpaEntity.setCapacity(gymClass.capacity());
        jpaEntity.setSpotsAvailable(gymClass.spotsAvailable());

        return jpaEntity;
    }

    static GymClass toModelEntity(GymClassJpaEntity jpaEntity){
        return new GymClass(
                new ClassId(jpaEntity.getId()),
                jpaEntity.getType(),
                jpaEntity.getDescription(),
                jpaEntity.getCapacity(),
                jpaEntity.getSpotsAvailable()
        );
    }

    static List<GymClass> toModelEntities(List<GymClassJpaEntity> jpaEntities){
        return jpaEntities.stream().map(GymClassMapper::toModelEntity).toList();
    }

    static Optional<GymClass> toModelEntityOptional(GymClassJpaEntity jpaEntity){
        return Optional.ofNullable(jpaEntity).map(GymClassMapper::toModelEntity);
    }

}
