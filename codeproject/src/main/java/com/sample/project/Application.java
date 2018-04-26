package com.sample.project;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
/**
 * 
 * @author ashwin
 *
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = { "com.sample.project.dao.repository" })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    /**
     * rest service model domain mapper
     * @return
     */
    @Bean(name = "restServiceModelDomainMapperFacade")
    public MapperFacade restServiceModelDomainMapperFacade() {

        MapperFactory factory = new DefaultMapperFactory.Builder().mapNulls(false).build();

        com.sample.project.mapper.RestServiceModelDomainMapper.registerClassMaps(factory);

        return factory.getMapperFacade();
    }

}
