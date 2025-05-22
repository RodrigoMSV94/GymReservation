package com.mitocode.reservation.application.service.gymclass;

import com.mitocode.reservation.application.port.out.persistence.GymClassRepository;
import com.mitocode.reservation.model.gymclass.GymClass;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.mitocode.reservation.model.gymclass.TestGymClassFactory.createTestClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindGymClassServiceTest {

    private static final GymClass TEST_GYM_CLASS_1 = createTestClass(30, 20);
    private static final GymClass TEST_GYM_CLASS_2 = createTestClass(40, 15);

    private final GymClassRepository gymClassRepository = mock(GymClassRepository.class);
    private final FindGymClassService findGymClassService =
            new FindGymClassService(gymClassRepository);

    @Test
    @DisplayName("search query")
    void test1(){
        when(gymClassRepository.findByTypeOrDescription("one")).thenReturn(List.of(TEST_GYM_CLASS_1));
        when(gymClassRepository.findByTypeOrDescription("two")).thenReturn(List.of(TEST_GYM_CLASS_2));
        when(gymClassRepository.findByTypeOrDescription("one-two"))
                .thenReturn(List.of(TEST_GYM_CLASS_1, TEST_GYM_CLASS_2));
        when(gymClassRepository.findByTypeOrDescription("empty")).thenReturn(List.of());

        assertThat(findGymClassService.findByTypeOrDescription("one")).containsExactly(TEST_GYM_CLASS_1);
        assertThat(findGymClassService.findByTypeOrDescription("two")).containsExactly(TEST_GYM_CLASS_2);
        assertThat(findGymClassService.findByTypeOrDescription("one-two"))
                .containsExactly(TEST_GYM_CLASS_1, TEST_GYM_CLASS_2);
        assertThat(findGymClassService.findByTypeOrDescription("empty")).isEmpty();
    }

    @Test
    @DisplayName("given a too short search query")
    void test2(){
        String searchQuery = "x";
        ThrowableAssert.ThrowingCallable invocation =
                () -> findGymClassService.findByTypeOrDescription(searchQuery);

        assertThatIllegalArgumentException().isThrownBy(invocation);
    }

}
