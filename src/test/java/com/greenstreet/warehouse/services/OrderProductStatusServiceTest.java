package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.OrderProductStatus;
import com.greenstreet.warehouse.repository.OrderProductStatusRepository;
import com.greenstreet.warehouse.services.impl.OrderProductStatusServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderProductStatusServiceTest {

    @Mock
    OrderProductStatusRepository productStatusRepository;

    @InjectMocks
    OrderProductStatusServiceImpl productStatusService;

    @Test
    void shouldCreatedStatus() {
        OrderProductStatus orderProductStatus = getOrderProductStatus();
        given(productStatusRepository.save(orderProductStatus)).willReturn(orderProductStatus);

        assertThat(productStatusService.create(orderProductStatus)).isEqualTo(orderProductStatus);
    }

    @Test
    void shouldUpdatedStatus() {
        OrderProductStatus oldProductStatus = getOrderProductStatus();
        OrderProductStatus newProductStatus = getOrderProductStatus();
        newProductStatus.setName("CANCELED");

        given(productStatusRepository.findById(newProductStatus.getId())).willReturn(Optional.of(oldProductStatus));
        given(productStatusRepository.save(newProductStatus)).willReturn(newProductStatus);

        assertThat(productStatusService.update(newProductStatus)).isEqualTo(newProductStatus);
    }

    @Test
    void getAll() {
        List<OrderProductStatus> orderProductStatusList = List.of(getOrderProductStatus());

        given(productStatusRepository.findAll()).willReturn(orderProductStatusList);

        assertThat(productStatusService.getAll()).isEqualTo(orderProductStatusList);
    }

    private OrderProductStatus getOrderProductStatus(){
        return new OrderProductStatus(2, "OK");
    }
}