package com.mitocode.reservation.adapter.out.persistence.jpa;

import com.mitocode.reservation.adapter.out.persistence.AbstractGymClassRepositoryTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("jpa-test")
public class JpaGymClassRepositoryTest extends AbstractGymClassRepositoryTest {
}
