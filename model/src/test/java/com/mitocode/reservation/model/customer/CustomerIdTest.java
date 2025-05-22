package com.mitocode.reservation.model.customer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class CustomerIdTest {

    @ParameterizedTest
    @ValueSource(strings = {"invalid-email", "1@co"})
    void test1(String email){
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new CustomerId(email))
                .withMessageContaining("Invalid email address");
    }

    @ParameterizedTest
    @ValueSource(strings = {"valid@example.com", "another.valid@example.com"})
    void test2(String email){
        CustomerId customerId = new CustomerId(email);
        assertThat(customerId.email()).isEqualTo(email);
    }

}
