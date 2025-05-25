package com.mitocode.reservation.adapter.out.persistence.jpa;

import com.mitocode.reservation.adapter.out.persistence.DemoGymClasses;
import com.mitocode.reservation.application.port.out.persistence.GymClassRepository;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.gymclass.GymClass;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@ConditionalOnProperty(name = "persistence", havingValue = "mysql")
@Repository
@RequiredArgsConstructor
public class JpaGymClassRepository implements GymClassRepository {

    private final JpaGymClassSpringDataRepository springDataRepository;

    @PostConstruct
    private void createDemoGymClasses(){
        DemoGymClasses.DEMO_GYM_CLASSES.forEach(this::save);
    }

    @Override
    @Transactional
    public void save(GymClass gymClass) {
        springDataRepository.save(GymClassMapper.toJpaEntity(gymClass));
    }

    @Override
    public Optional<GymClass> findById(ClassId classId) {
        return springDataRepository.findById(classId.value())
                .flatMap(GymClassMapper::toModelEntityOptional);
    }

    @Override
    public List<GymClass> findByTypeOrDescription(String queryString) {
        return springDataRepository.findByTypeOrDescription("%" + queryString + "%")
                .stream()
                .map(GymClassMapper::toModelEntity)
                .toList();
    }
}
