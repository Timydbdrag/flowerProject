package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.OrderStatus;
import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.repository.OrderStatusRepository;
import com.greenstreet.warehouse.services.impl.OrderStatusServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderStatusServiceTest {

    @Mock
    OrderStatusRepository orderStatusRepository;

    @InjectMocks
    OrderStatusServiceImpl orderStatusService;

    @Test
    void shouldCreatedOrderStatus() {
        OrderStatus testStatus = getTestStatus();
        given(orderStatusRepository.save(any())).willReturn(testStatus);

        assertThat(orderStatusService.create(testStatus)).isEqualTo(testStatus);
    }

    @Test
    void shouldReturnException() {
        OrderStatus testStatus = getTestStatus();
        Exception exception = assertThrows(ApiRequestException.class, () -> {
            orderStatusService.update(testStatus);
        });

        String expectedMessage = "Status with ID 1 cannot be changed!";

        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldNotUpdatedOrderStatus() {
        OrderStatus testStatus = getTestStatus();
        testStatus.setId(2);
        given(orderStatusRepository.findById(testStatus.getId()))
                .willReturn(Optional.of(testStatus));

        assertThat(orderStatusService.update(testStatus)).isEqualTo(testStatus);
    }

    @Test
    void shouldUpdatedOrderStatus() {
        OrderStatus testStatus = getTestStatus();
        testStatus.setId(2);
        testStatus.setName("Fixed");
        OrderStatus statusInDB = getTestStatus();
        statusInDB.setId(2);
        given(orderStatusRepository.findById(testStatus.getId()))
                .willReturn(Optional.of(statusInDB));
         given(orderStatusRepository.save(testStatus)).willReturn(testStatus);

        assertThat(orderStatusService.update(testStatus)).isEqualTo(testStatus);
    }

    @Test
    void shouldReturnAllStatus() {
        List<OrderStatus> statusList = List.of(getTestStatus());
        given(orderStatusRepository.findAll()).willReturn(statusList);

        assertThat(orderStatusService.getAll()).isEqualTo(statusList);
    }

    private OrderStatus getTestStatus() {
        return new OrderStatus(1, "Done");
    }
}