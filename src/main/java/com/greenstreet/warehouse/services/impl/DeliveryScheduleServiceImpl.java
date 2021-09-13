package com.greenstreet.warehouse.services.impl;

import com.greenstreet.warehouse.entity.DeliverySchedule;
import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.repository.DeliveryScheduleRepository;
import com.greenstreet.warehouse.services.DeliveryScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.greenstreet.warehouse.exception.ExceptionConstant.DELIVERY_DATE_NOT_FOUND;
import static com.greenstreet.warehouse.exception.ExceptionConstant.SORTED_PARAM_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryScheduleServiceImpl implements DeliveryScheduleService {

    private final DeliveryScheduleRepository deliveryScheduleRepository;

    @Override
    public DeliverySchedule create(DeliverySchedule deliverySchedule) {
        log.info("A new delivery date has been added to the database, date: {} ", deliverySchedule.getDate());
        deliverySchedule.setId(null);
        return deliveryScheduleRepository.save(deliverySchedule);
    }

    @Override
    public DeliverySchedule update(DeliverySchedule deliverySchedule) {
        return Optional.of(deliveryScheduleRepository.findById(deliverySchedule.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(deliveryDB -> {
                    log.info("Delivery date {} changed to {}",
                            deliveryDB.getDate(), deliverySchedule.getDate());
                    deliveryDB.setDate(deliverySchedule.getDate());

                    return deliveryScheduleRepository.save(deliveryDB);
                })
                .orElseThrow(() -> new ApiRequestException(DELIVERY_DATE_NOT_FOUND));
    }

    @Override
    public Page<DeliverySchedule> getAll(Pageable pageable) {
        if (!onlyContainsAllowedProperties(pageable))
            throw new ApiRequestException(SORTED_PARAM_NOT_FOUND);

        log.debug("Getting all delivery dates");
        return deliveryScheduleRepository.findAll(pageable);
    }

    private List<String> getAllowedOrderedProperties() {
        return List.of("id", "date");
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort()
                .stream()
                .map(Sort.Order::getProperty)
                .allMatch(getAllowedOrderedProperties()::contains);
    }
}
