package com.greenstreet.warehouse.services.impl;

import com.greenstreet.warehouse.entity.Color;
import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.model.request.ColorDTO;
import com.greenstreet.warehouse.repository.ColorRepository;
import com.greenstreet.warehouse.services.ColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.greenstreet.warehouse.exception.ExceptionConstant.COLOR_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Override
    public Color create(ColorDTO colorDTO) {
        Color color = colorConverter(colorDTO);
        log.info("A new color has been added to the database, name: {} ", color.getName());
        color.setId(null);

        return colorRepository.save(color);
    }

    @Override
    public Color update(ColorDTO colorDTO) {
        Color color = colorConverter(colorDTO);

        Color colorDB = colorRepository.findById(color.getId())
                .orElseThrow(() -> new ApiRequestException(COLOR_NOT_FOUND));

        log.info("Color name {} changed to {}", colorDB.getName(), color.getName());
        colorDB.setName(color.getName());

        return colorRepository.save(colorDB);
    }

    @Override
    public List<Color> getAll() {
        return colorRepository.findAll();
    }

    private Color colorConverter(ColorDTO colorDTO) {
        Color color = new Color();
        color.setId(colorDTO.getId());
        color.setName(colorDTO.getName());
        return color;
    }
}
