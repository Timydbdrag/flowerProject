package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.Color;
import com.greenstreet.warehouse.model.request.ColorDTO;
import com.greenstreet.warehouse.repository.ColorRepository;
import com.greenstreet.warehouse.services.impl.ColorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ColorServiceTest {

    @Mock
    ColorRepository colorRepository;

    @InjectMocks
    ColorServiceImpl colorService;

    @Test
    void create() {
        Color expected = getTestColor();
        given(colorRepository.save(any())).willReturn(expected);

        assertThat(colorService.create(new ColorDTO())).isEqualTo(expected);
    }

    @Test
    void update() {
        Color color = getTestColor();
        ColorDTO colorDTO = new ColorDTO(color.getId(), color.getName());
        given(colorRepository.findById(colorDTO.getId())).willReturn(Optional.of(color));
        given(colorRepository.save(any())).willReturn(color);

        assertThat(colorService.update(colorDTO)).isEqualTo(color);
    }

    @Test
    void getAll() {
        List<Color> testColors = List.of(getTestColor());
        given(colorRepository.findAll()).willReturn(testColors);

        assertThat(colorService.getAll()).isEqualTo(testColors);
    }

    private Color getTestColor() {
        return new Color(1, "Yellow");
    }
}