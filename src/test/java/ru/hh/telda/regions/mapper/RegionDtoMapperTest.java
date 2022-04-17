package ru.hh.telda.regions.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.hh.telda.regions.dto.RegionDto;
import ru.hh.telda.regions.dto.mapper.RegionDtoMapper;
import ru.hh.telda.regions.model.Region;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RegionDtoMapperTest {

    private RegionDtoMapper regionDtoMapper = Mappers.getMapper(RegionDtoMapper.class);

    @Test
    void shouldMapRegionToRegionDto() {
        // given
        Region region = Region.builder().id(1L).name("AnyRegion").shortName("ANY").build();
        // when
        RegionDto regionDto = regionDtoMapper.regionToDto(region);
        // then
        assertNotNull(regionDto);
        assertEquals(regionDto.getId(), 1);
        assertEquals(regionDto.getName(), "AnyRegion");
        assertEquals(regionDto.getShortName(), "ANY");
    }

    @Test
    void shouldMapRegionDtoToRegion() {
        // given
        RegionDto regionDto = new RegionDto(1L, "AnyRegion", "ANY");
        // when
        Region region = regionDtoMapper.dtoToRegion(regionDto);
        // then
        assertNotNull(region);
        assertEquals(region.getId(), 1);
        assertEquals(region.getName(), "AnyRegion");
        assertEquals(region.getShortName(), "ANY");
    }
}
