package com.greenstreet.warehouse.repository;

import com.greenstreet.warehouse.entity.DeliverySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryScheduleRepository extends JpaRepository<DeliverySchedule, Integer> {
}
