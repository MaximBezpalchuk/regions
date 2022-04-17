package ru.hh.telda.regions;

import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.hh.telda.regions.model.Region;

@MappedTypes({Region.class})
@MapperScan("ru.hh.telda.regions.repository")
@SpringBootApplication
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting the application");
        SpringApplication.run(Application.class, args);
    }
}
