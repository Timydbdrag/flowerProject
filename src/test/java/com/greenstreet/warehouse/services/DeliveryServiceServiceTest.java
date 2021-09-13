package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.DeliverySchedule;
import com.greenstreet.warehouse.repository.DeliveryScheduleRepository;
import com.greenstreet.warehouse.services.impl.DeliveryScheduleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceServiceTest {

    @Mock
    DeliveryScheduleRepository deliveryRepository;

    @InjectMocks
    DeliveryScheduleServiceImpl deliveryService;

    @Test
    void create() {
        DeliverySchedule expected = getTestDeliveryDate();
        given(deliveryRepository.save(any())).willReturn(expected);

        assertThat(deliveryService.create(new DeliverySchedule())).isEqualTo(expected);
    }

    @Test
    void update() {
        DeliverySchedule deliveryDate = getTestDeliveryDate();
        given(deliveryRepository.findById(deliveryDate.getId())).willReturn(Optional.of(deliveryDate));
        given(deliveryRepository.save(any())).willReturn(deliveryDate);

        assertThat(deliveryService.update(deliveryDate)).isEqualTo(deliveryDate);
    }

    @Test
    void getAll() {
        Page<DeliverySchedule> deliveryDates = new PageImpl<>(List.of(getTestDeliveryDate()));

        Pageable pageable = PageRequest.of(0,10, Sort.by("id"));
        given(deliveryRepository.findAll(pageable)).willReturn(deliveryDates);

        assertThat(deliveryService.getAll(pageable)).isEqualTo(deliveryDates);
    }

    private DeliverySchedule getTestDeliveryDate() {
        return new DeliverySchedule(1, LocalDate.now());
    }
}