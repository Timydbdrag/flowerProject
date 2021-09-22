package com.greenstreet.warehouse.model.request;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ColorDTO {

    private Integer id;

    @NotNull
    private String name;

    public ColorDTO(){}

    public ColorDTO(Integer id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorDTO colorDTO = (ColorDTO) o;
        return Objects.equals(id, colorDTO.id) && Objects.equals(name, colorDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
