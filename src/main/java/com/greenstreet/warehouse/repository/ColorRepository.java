package com.greenstreet.warehouse.repository;

import com.greenstreet.warehouse.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Integer> {
}
