package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.DeliverySchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryScheduleService {
    DeliverySchedule create(DeliverySchedule deliverySchedule);
    DeliverySchedule update(DeliverySchedule deliverySchedule);
    Page<DeliverySchedule> getAll(Pageable pageable);
}
