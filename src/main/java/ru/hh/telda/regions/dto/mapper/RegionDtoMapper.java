package ru.hh.telda.regions.dto.mapper;

import org.mapstruct.Mapper;
import ru.hh.telda.regions.dto.RegionDto;
import ru.hh.telda.regions.model.Region;

@Mapper(componentModel = "spring")
public interface RegionDtoMapper {

    RegionDto regionToDto(Region region);

    Region dtoToRegion(RegionDto regionDto);
}
