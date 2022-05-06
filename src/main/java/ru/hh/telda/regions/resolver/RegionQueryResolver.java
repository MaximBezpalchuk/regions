package ru.hh.telda.regions.resolver;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import ru.hh.telda.regions.model.Region;
import ru.hh.telda.regions.service.RegionService;

import java.util.List;

@Component
public class RegionQueryResolver implements GraphQLQueryResolver {

    private final RegionService regionService;

    public RegionQueryResolver(RegionService regionService) {
        this.regionService = regionService;
    }

    public List<Region> getAllRegions(String name, String shortName) {
        return this.regionService.findByFilters(name, shortName);
    }

    public Region getRegion(Long id) {
        return this.regionService.findById(id);
    }
}
