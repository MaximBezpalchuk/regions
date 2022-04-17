package ru.hh.telda.regions.model;

import java.util.Objects;

public class Region {

    private Long id;
    private String name;
    private String shortName;

    private Region(Long id, String name, String shortName) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
    }

    public Region() {
    }

    public static Builder builder() {
        return new Builder();
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
        Region region = (Region) o;
        return id == region.id &&
            name.equals(region.name) &&
            shortName.equals(region.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortName);
    }

    public static class Builder {
        private Long id;
        private String name;
        private String shortName;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder shortName(String shortName) {
            this.shortName = shortName;
            return this;
        }

        public Region build() {
            return new Region(id, name, shortName);
        }
    }
}