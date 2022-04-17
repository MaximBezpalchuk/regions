package ru.hh.telda.regions.dto;

import java.util.Objects;

public class RegionDto {

    private Long id;
    private String name;
    private String shortName;

    public RegionDto(Long id, String name, String shortName) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
    }

    public RegionDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionDto regionDto = (RegionDto) o;
        return id.equals(regionDto.id) &&
            name.equals(regionDto.name) &&
            shortName.equals(regionDto.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortName);
    }
}