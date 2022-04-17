package ru.hh.telda.regions.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hh.telda.regions.model.Region;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
@DBRider
@DataSet(value = {"data.yml"}, cleanAfter = true)
public class RegionMapperTest {

    @Autowired
    private RegionMapper regionMapper;

    @Test
    void whenFindAll_thenAllExistingRegionsFound() {
        List<Region> actual = regionMapper.getRegions(null, null);

        assertEquals(10, actual.size());
    }

    @Test
    void whenFindById_thenExistingRegionFound() {
        Optional<Region> actual = regionMapper.getRegionById(1L);
        Optional<Region> expected = Optional.of(Region.builder().id(1L).name("Belgorod").shortName("BEL").build());

        assertEquals(expected, actual);
    }

    @Test
    void whenFindByName_thenExistingRegionFound() {
        Optional<Region> actual = regionMapper.getRegionByName("Belgorod");
        Optional<Region> expected = Optional.of(Region.builder().id(1L).name("Belgorod").shortName("BEL").build());

        assertEquals(expected, actual);
    }

    @Test
    void whenFindByShortName_thenExistingRegionFound() {
        Optional<Region> actual = regionMapper.getRegionByShortName("BEL");
        Optional<Region> expected = Optional.of(Region.builder().id(1L).name("Belgorod").shortName("BEL").build());

        assertEquals(expected, actual);
    }

    @Test
    void whenInsertRegion_thenRegionsCountUpgraded() {
        Region region = Region.builder().name("Belgorod123").shortName("BEL2").build();
        Long count = regionMapper.insertRegion(region);

        assertEquals(1L, count);
    }

    @Test
    void whenUpdateRegion_thenExistingRegionFound() {
        Region expected = Region.builder().id(1L).name("Belgorod123").shortName("BEL").build();
        Long count = regionMapper.updateRegion(expected);
        Optional<Region> actual = regionMapper.getRegionById(1L);

        assertEquals(expected, actual.get());
        assertEquals(1L, count);
    }

    @Test
    void whenDeleteRegion_thenRegionsCountUpgraded() {
        Long count = regionMapper.deleteRegion(1L);

        assertEquals(1L, count);
    }
}
