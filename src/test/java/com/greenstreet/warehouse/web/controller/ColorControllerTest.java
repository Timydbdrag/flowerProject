package com.greenstreet.warehouse.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenstreet.warehouse.entity.Color;
import com.greenstreet.warehouse.model.request.ColorDTO;
import com.greenstreet.warehouse.services.ColorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ColorControllerTest {

    private final String uri = "/api/color";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    ColorService colorService;

    @Test
    @WithMockUser(value = "tester", roles = "MANAGER")
    void getAll() throws Exception {
        List<Color> testColors = List.of(getTestColor());
        given(colorService.getAll()).willReturn(testColors);

        mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").value(testColors.get(0).getId()))
                .andExpect(jsonPath("$.[*].name").value(testColors.get(0).getName()));
    }

    @Test
    @WithMockUser(value = "tester", roles = "MANAGER")
    void createColor() throws Exception {
        Color testColor = getTestColor();
        ColorDTO colorDTO = new ColorDTO(testColor.getId(), testColor.getName());

        given(colorService.create(colorDTO)).willReturn(testColor);

        String content = objectMapper.writeValueAsString(colorDTO);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testColor.getId()))
                .andExpect(jsonPath("$.name").value(testColor.getName()));
    }

    @Test
    @WithMockUser(value = "tester", roles = "MANAGER")
    void shouldUpdatedColor() throws Exception {
        Color testColor = getTestColor();
        ColorDTO colorDTO = new ColorDTO(testColor.getId(), testColor.getName());

        given(colorService.update(colorDTO)).willReturn(testColor);

        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(colorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testColor.getId()))
                .andExpect(jsonPath("$.name").value(testColor.getName()));
    }

    @Test
    @WithMockUser(value = "tester", roles = "CLIENT")
    void shouldReturnStatus_403_whenUpdatedColor() throws Exception {
        String color = objectMapper.writeValueAsString(getTestColor());

        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(color))
                .andExpect(status().isForbidden());
    }

    private Color getTestColor() {
        return new Color(1, "Green");
    }
}