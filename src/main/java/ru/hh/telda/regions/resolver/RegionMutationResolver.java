package ru.hh.telda.regions.resolver;

import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import ru.hh.telda.regions.model.Region;
import ru.hh.telda.regions.service.RegionService;

@Component
public class RegionMutationResolver implements GraphQLMutationResolver {

    private final RegionService regionService;

    public RegionMutationResolver(RegionService regionService) {
        this.regionService = regionService;
    }

    public Region createRegion(String name, String shortName) {
        return regionService.save(Region.builder().name(name).shortName(shortName).build());
    }

    public Long updateRegion(Long id, String name, String shortName) {
        return regionService.update(Region.builder().id(id).name(name).shortName(shortName).build());
    }

    public Long deleteRegion(Long id) {
        return regionService.delete(id);
    }
}
