package ru.hh.telda.regions.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import ru.hh.telda.regions.model.Region;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface RegionMapper {

    @Select("<script>" +
        "SELECT * FROM regions WHERE" +
        "<if test='name != null'> lower(name) like lower('%' || #{name} || '%') AND</if>" +
        "<if test='shortName != null'> lower(short_name) like lower('%' || #{shortName} || '%') AND</if>" +
        " 1=1" +
        "</script>")
    @Results(value = {
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "shortName", column = "short_name")
    })
    List<Region> getRegions(@Param("name") String name, @Param("shortName") String shortName);

    @Select("SELECT * FROM regions WHERE id=#{id}")
    @Results(value = {
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "shortName", column = "short_name")
    })
    Optional<Region> getRegionById(@Param("id") Long id);

    @Select("SELECT * FROM regions WHERE name=#{name}")
    @Results(value = {
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "shortName", column = "short_name")
    })
    Optional<Region> getRegionByName(@Param("name") String name);

    @Select("SELECT * FROM regions WHERE short_name=#{shortName}")
    @Results(value = {
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "shortName", column = "short_name")
    })
    Optional<Region> getRegionByShortName(@Param("shortName") String shortName);

    @Insert("INSERT INTO regions(name, short_name) VALUES(#{name}, #{shortName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insertRegion(Region region);

    @Update("UPDATE regions SET name=#{name}, short_name=#{shortName} WHERE id=#{id}")
    Long updateRegion(Region region);

    @Delete("DELETE FROM regions WHERE id =#{id}")
    Long deleteRegion(@Param("id") Long id);
}
