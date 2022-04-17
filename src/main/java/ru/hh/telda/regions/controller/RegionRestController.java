package ru.hh.telda.regions.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.hh.telda.regions.dto.RegionDto;
import ru.hh.telda.regions.dto.Slice;
import ru.hh.telda.regions.dto.mapper.RegionDtoMapper;
import ru.hh.telda.regions.model.Region;
import ru.hh.telda.regions.service.RegionService;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/regions")
public class RegionRestController {

    private static final Logger logger = LoggerFactory.getLogger(RegionRestController.class);

    private final RegionService regionService;
    private final RegionDtoMapper regionDtoMapper;

    public RegionRestController(RegionService regionService, RegionDtoMapper regionDtoMapper) {
        this.regionService = regionService;
        this.regionDtoMapper = regionDtoMapper;
    }

    @GetMapping
    @Operation(summary = "Get all regions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Show all regions",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Slice.class))}),
        @ApiResponse(responseCode = "404", description = "Regions not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)})
    public ResponseEntity<Slice> getRegions(@RequestParam(required = false) String name, @RequestParam(required = false) String shortName) {
        logger.debug("Show all regions");

        return ResponseEntity.ok(new Slice(regionService.findByFilters(name, shortName).stream().map(regionDtoMapper::regionToDto).collect(Collectors.toList())));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an region by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the region",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RegionDto.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
        @ApiResponse(responseCode = "404", description = "Region not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)})
    public ResponseEntity<RegionDto> showRegion(@Parameter(description = "Id of region to be searched") @PathVariable Long id) {
        logger.debug("Show region with id {}", id);

        return ResponseEntity.ok(regionDtoMapper.regionToDto(regionService.findById(id)));
    }

    @PostMapping
    @Operation(summary = "Create a new region by its DTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Region successfully created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
        @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)})
    public ResponseEntity create(@RequestBody RegionDto regionDto) {
        logger.debug("Create new region. Id {}", regionDto.getId());
        Region region = regionService.save(regionDtoMapper.dtoToRegion(regionDto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(region.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing region by its DTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Region successfully updated", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
        @ApiResponse(responseCode = "404", description = "Region not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)})
    public ResponseEntity update(@RequestBody RegionDto regionDto) {
        logger.debug("Update region with id {}", regionDto.getId());
        regionService.update(regionDtoMapper.dtoToRegion(regionDto));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing region by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Region successfully deleted", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
        @ApiResponse(responseCode = "404", description = "Region not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)})
    public ResponseEntity delete(@PathVariable Long id) {
        logger.debug("Delete region. Id {}", id);
        regionService.delete(id);

        return ResponseEntity.noContent().build();
    }
}