package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.Color;
import com.greenstreet.warehouse.model.request.ColorDTO;

import java.util.List;

public interface ColorService {
    Color create(ColorDTO color);
    Color update(ColorDTO color);
    List<Color> getAll();
}
