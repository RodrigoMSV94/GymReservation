package com.mitocode.reservation.adapter.out.persistence.jpa;

import com.mitocode.reservation.application.port.out.persistence.ReservationRepository;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.reservation.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class JpaReservationRepository implements ReservationRepository {

    private final EntityManagerFactory entityManagerFactory;

    public JpaReservationRepository(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Reservation reservation) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            entityManager.merge(ReservationMapper.toJpaEntity(reservation));
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void deleteByCustomerId(CustomerId customerId) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            TypedQuery<ReservationJpaEntity> query = entityManager.createQuery(
                    "from ReservationJpaEntity r where r.customerId = :customerId", ReservationJpaEntity.class);
            query.setParameter("customerId", customerId.email());

            List<ReservationJpaEntity> reservationJpaEntities = query.getResultList();

            for (ReservationJpaEntity reservationJpaEntity:reservationJpaEntities){
                entityManager.remove(reservationJpaEntity);
            }
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void deleteReservationByCustomerIdAndClassId(CustomerId customerId, ClassId classId) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            TypedQuery<ReservationJpaEntity> query = entityManager.createQuery(
                    "from ReservationJpaEntity r where r.customerId = :customerId and r.gymClass.id = :classId", ReservationJpaEntity.class);
            query.setParameter("customerId", customerId.email());
            query.setParameter("classId", classId.value());

            query.getResultStream()
                    .findFirst()
                            .ifPresent(entityManager::remove);

            entityManager.getTransaction().commit();
        }
    }

    @Override
    public List<Reservation> findReservationsByCustomerId(CustomerId customerId) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            TypedQuery<ReservationJpaEntity> query = entityManager.createQuery(
                    "from ReservationJpaEntity r where r.customerId = :customerId", ReservationJpaEntity.class);
            query.setParameter("customerId", customerId.email());
            List<ReservationJpaEntity> reservationJpaEntities = query.getResultList();
            return ReservationMapper.toModelEntities(reservationJpaEntities);
        }
    }

    @Override
    public Optional<Reservation> findByCustomerIdAndClassId(CustomerId customerId, ClassId classId) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            TypedQuery<ReservationJpaEntity> query = entityManager.createQuery(
                    "from ReservationJpaEntity r where r.customerId = :customerId and r.gymClass.id = :classId", ReservationJpaEntity.class);
            query.setParameter("customerId", customerId.email());
            query.setParameter("classId", classId.value());

            ReservationJpaEntity reservationJpaEntity = query.getResultStream().findFirst().orElse(null);

            return reservationJpaEntity !=null
                    ?ReservationMapper.toModelEntityOptional(reservationJpaEntity)
                    :Optional.empty();
        }
    }
}
