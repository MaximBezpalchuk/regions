package ru.hh.telda.regions.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hh.telda.regions.exception.EntityNotFoundException;
import ru.hh.telda.regions.exception.EntityNotUniqueException;
import ru.hh.telda.regions.model.Region;
import ru.hh.telda.regions.repository.RegionMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegionServiceTest {

    @Mock
    private RegionMapper regionMapper;
    @InjectMocks
    private RegionService regionService;

    @Test
    void givenListOfRegions_whenFindAll_thenAllExistingRegionsFound() {
        Region region1 = Region.builder().id(1L).name("AnyRegion1").shortName("ANY1").build();
        Region region2 = Region.builder().id(2L).name("AnyRegion2").shortName("ANY2").build();
        List<Region> expected = Arrays.asList(region1, region2);
        when(regionMapper.getRegions(null, null)).thenReturn(expected);
        List<Region> actual = regionService.findByFilters(null, null);

        assertEquals(expected, actual);
    }

    @Test
    void givenExistingRegion_whenFindById_thenRegionFound() {
        Optional<Region> expected = Optional.of(Region.builder().id(1L).name("AnyRegion").shortName("ANY").build());
        when(regionMapper.getRegionById(1L)).thenReturn(expected);
        Region actual = regionService.findById(1L);

        assertEquals(expected.get(), actual);
    }

    @Test
    void givenExistingRegion_whenFindById_thenEntityNotFoundException() {
        when(regionMapper.getRegionById(1000000L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            regionService.findById(1000000L);
        });

        assertEquals("Can`t find any region with id: 1000000", exception.getMessage());
    }

    @Test
    void givenNewRegion_whenSave_thenSaved() {
        Region region = Region.builder().name("AnyRegion").shortName("ANY").build();
        regionService.save(region);

        verify(regionMapper).insertRegion(region);
    }

    @Test
    void givenExistingRegion_whenSave_thenSaved() {
        Region region = Region.builder().id(1L).name("AnyRegion").shortName("ANY").build();
        when(regionMapper.getRegionByName(region.getName())).thenReturn(Optional.empty());
        when(regionMapper.getRegionByShortName(region.getShortName())).thenReturn(Optional.empty());
        regionService.save(region);

        verify(regionMapper).insertRegion(region);
    }

    @Test
    void givenExistingRegionId_whenDelete_thenDeleted() {
        regionService.delete(1L);

        verify(regionMapper).deleteRegion(1L);
    }

    @Test
    void givenNotUniqueRegion_whenSave_thenEntityNotUniqueException() {
        Region region1 = Region.builder().id(1L).name("AnyRegion1").shortName("ANY1").build();
        Region region2 = Region.builder().id(100L).name("AnyRegion1").shortName("ANY1").build();
        when(regionMapper.getRegionByName(region1.getName())).thenReturn(Optional.of(region2));
        when(regionMapper.getRegionByShortName(region1.getShortName())).thenReturn(Optional.of(region2));
        Exception exception = assertThrows(EntityNotUniqueException.class, () -> {
            regionService.save(region1);
        });

        assertEquals("Region with name AnyRegion1 or short name ANY1 is already exists!", exception.getMessage());
    }
}
