package ru.hh.telda.regions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.hh.telda.regions.dto.RegionDto;
import ru.hh.telda.regions.dto.Slice;
import ru.hh.telda.regions.dto.mapper.RegionDtoMapper;
import ru.hh.telda.regions.model.Region;
import ru.hh.telda.regions.service.RegionService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RegionRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Spy
    private RegionDtoMapper regionMapper = Mappers.getMapper(RegionDtoMapper.class);
    @Mock
    private RegionService regionService;
    @InjectMocks
    private RegionRestController regionRestController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(regionRestController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void whenGetRegions_thenAllRegionsReturned() throws Exception {
        Region region1 = Region.builder().id(1L).name("AnyRegion1").shortName("ANY1").build();
        Region region2 = Region.builder().id(2L).name("AnyRegion2").shortName("ANY2").build();
        List<Region> regions = Arrays.asList(region1, region2);
        List<RegionDto> regionDtos = regions.stream().map(regionMapper::regionToDto).collect(Collectors.toList());
        when(regionService.findByFilters(null, null)).thenReturn(regions);

        mockMvc.perform(get("/api/regions")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(objectMapper.writeValueAsString(new Slice(regionDtos))))
            .andExpect(status().isOk());

        verifyNoMoreInteractions(regionService);
    }

    @Test
    public void whenGetRegionsWithCaseInsensitiveParams_thenAllRegionsByFilterReturned() throws Exception {
        Region region1 = Region.builder().id(1L).name("AnyRegion1").shortName("ANY1").build();
        Region region2 = Region.builder().id(2L).name("AnyRegion2").shortName("ANY2").build();
        List<Region> regions1 = Arrays.asList(region1);
        List<Region> regions2 = Arrays.asList(region2);
        List<RegionDto> regionDtos1 = regions1.stream().map(regionMapper::regionToDto).collect(Collectors.toList());
        List<RegionDto> regionDtos2 = regions2.stream().map(regionMapper::regionToDto).collect(Collectors.toList());
        when(regionService.findByFilters("anYRegiOn1", null)).thenReturn(regions1);

        mockMvc.perform(get("/api/regions?name=anYRegiOn1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(objectMapper.writeValueAsString(new Slice(regionDtos1))))
            .andExpect(status().isOk());

        when(regionService.findByFilters(null, "any2")).thenReturn(regions2);

        mockMvc.perform(get("/api/regions?shortName=any2")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(objectMapper.writeValueAsString(new Slice(regionDtos2))))
            .andExpect(status().isOk());

        when(regionService.findByFilters("anYRegiOn2", "any2")).thenReturn(regions2);

        mockMvc.perform(get("/api/regions?name=anYRegiOn2&shortName=any2")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(objectMapper.writeValueAsString(new Slice(regionDtos2))))
            .andExpect(status().isOk());

        verifyNoMoreInteractions(regionService);
    }

    @Test
    public void whenGetOneRegion_thenOneRegionReturned() throws Exception {
        Region region = Region.builder().id(1L).name("AnyRegion").shortName("ANY").build();
        RegionDto regionDto = regionMapper.regionToDto(region);
        when(regionService.findById(region.getId())).thenReturn(region);

        mockMvc.perform(get("/api/regions/{id}", region.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(objectMapper.writeValueAsString(regionDto)))
            .andExpect(status().isOk());
    }

    @Test
    public void whenSaveRegion_thenRegionSaved() throws Exception {
        Region region = Region.builder().id(1L).name("AnyRegion").shortName("ANY").build();
        RegionDto regionDto = regionMapper.regionToDto(region);
        when(regionService.save(region)).thenAnswer(I -> {
            region.setId(4L);
            return region;
        });
        mockMvc.perform(post("/api/regions")
            .content(objectMapper.writeValueAsString(regionDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/regions/4"))
            .andExpect(status().isCreated());
    }

    @Test
    public void whenUpdateRegion_thenRegionUpdated() throws Exception {
        Region region = Region.builder().id(1L).name("AnyRegion").shortName("ANY").build();
        Region region2 = Region.builder().id(1L).name("AnyRegion123").shortName("ANY").build();
        RegionDto regionDto = regionMapper.regionToDto(region);
        regionDto.setName("AnyRegion123");

        mockMvc.perform(patch("/api/regions/{id}", 1)
            .content(objectMapper.writeValueAsString(regionDto))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(regionService).update(region2);
    }

    @Test
    public void whenDeleteRegion_thenRegionDeleted() throws Exception {
        mockMvc.perform(delete("/api/regions/{id}", 1)
            .contentType(APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(regionService).delete(1L);
    }
}
