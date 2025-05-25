package com.mitocode.reservation.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public interface JpaGymClassSpringDataRepository extends JpaRepository<GymClassJpaEntity, String> {

    @Query("SELECT g FROM GymClassJpaEntity g WHERE g.type LIKE ?1 OR g.description LIKE ?1")
    List<GymClassJpaEntity> findByTypeOrDescription(String queryString);

}
