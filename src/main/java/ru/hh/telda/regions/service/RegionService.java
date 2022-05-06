package ru.hh.telda.regions.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hh.telda.regions.exception.EntityNotFoundException;
import ru.hh.telda.regions.exception.EntityNotUniqueException;
import ru.hh.telda.regions.model.Region;
import ru.hh.telda.regions.repository.RegionMapper;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    private static final Logger logger = LoggerFactory.getLogger(RegionService.class);

    private final RegionMapper regionMapper;

    public RegionService(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    public List<Region> findByFilters(String name, String shortName) {
        logger.debug("Find all regions");

        return regionMapper.getRegions(name, shortName);
    }

    public Region findById(Long id) {
        logger.debug("Find region by id {}", id);

        return regionMapper.getRegionById(id)
            .orElseThrow(() -> new EntityNotFoundException("Can`t find any region with id: " + id));
    }

    public Long update(Region region) {
        logger.debug("Save region");
        return regionMapper.updateRegion(region);
    }

    public Region save(Region region) {
        logger.debug("Save region");
        uniqueCheck(region);
        regionMapper.insertRegion(region);

        return region;
    }

    public Long delete(Long id) {
        logger.debug("Delete region with id: {}", id);
        return regionMapper.deleteRegion(id);
    }

    private void uniqueCheck(Region region) {
        logger.debug("Check audience is unique");
        Optional<Region> existingRegionByName = regionMapper.getRegionByName(region.getName());
        Optional<Region> existingRegionByShortName = regionMapper.getRegionByShortName(region.getShortName());
        if ((existingRegionByName.isPresent() && (existingRegionByName.get().getId() != region.getId()))
            || (existingRegionByShortName.isPresent() && (existingRegionByShortName.get().getId() != region.getId()))) {
            throw new EntityNotUniqueException(
                "Region with name " + region.getName() + " or short name " + region.getShortName() + " is already exists!");
        }
    }
}
