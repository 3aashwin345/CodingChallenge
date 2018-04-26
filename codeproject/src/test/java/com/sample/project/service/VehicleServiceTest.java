package com.sample.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.project.dao.repository.VehicleRepository;
import com.sample.project.domain.VehicleDetails;
import com.sample.project.exception.RestServiceException;
import com.sample.project.literals.RestServiceLiterals;
import com.sample.project.model.Vehicle;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class VehicleServiceTest {
    
    @Configuration
    static class VehicleConfig{
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
        
        @Bean
        VehicleService vehicleService(){
            return new VehicleServiceImpl();
        }
        
        @MockBean
        VehicleRepository vehicleRepository;
    }
    
    @Autowired
    VehicleRepository vehicleRepository;
    
    @Autowired
    VehicleService vehicleService;

    @Autowired
    MapperFacade restServiceModelDomainMapperFacade;

    @Test
    public void testGetVehicleDetails(){
        Mockito.when(vehicleRepository.findById(Mockito.anyInt()))
        .thenReturn(getVehicleDetails(1));
        Vehicle result = vehicleService.getByVehicleId(1);
        Assert.assertNotNull(result);
        Assert.assertEquals("Camry", result.getModel());
        
        List<VehicleDetails> vlist = new ArrayList<>();
        vlist.add(getVehicleDetails(1).get());
        
        Mockito.when(vehicleRepository.findAll())
        .thenReturn(vlist);
        
        List<Vehicle> vsList = vehicleService.getAllVehicles();
        Assert.assertNotNull(vsList);
        Assert.assertEquals(1, vsList.size());
        
        vlist.add(getVehicleDetails(2).get());
        Mockito.when(vehicleRepository.findByMake(Mockito.anyString()))
        .thenReturn(vlist);
        
        vsList = vehicleService.getVehiclesByMake("Toyota");
        Assert.assertNotNull(vsList);
        Assert.assertEquals(2, vsList.size());
        
    }
    
    @Test
    public void testPost(){
        
        Mockito.when(vehicleRepository.save(Mockito.any()))
        .thenReturn(getVehicleDetails(1).get());
        
        Vehicle result = vehicleService.addVehicleDetails(getModel());
        Assert.assertNotNull(result);
        Assert.assertEquals("Camry", result.getModel());
    }
    
    @Test
    public void testPostValidations(){
        Vehicle model = getModel();
        model.setMake(null);
        try{
            vehicleService.addVehicleDetails(model);
            Assert.fail();
        }catch(RestServiceException ex){
         
            Assert.assertEquals(RestServiceLiterals.VEHICLE_MAKE_IS_NULL_MESSAGE, ex.getMessage());
        }
        
        model = getModel();
        model.setModel(null);
        try{
            vehicleService.addVehicleDetails(model);
            Assert.fail();
        }catch(RestServiceException ex){
         
            Assert.assertEquals(RestServiceLiterals.VEHICLE_MODEL_IS_NULL_MESSAGE, ex.getMessage());
        }
        
        model = getModel();
        model.setYear(3000);
        try{
            vehicleService.addVehicleDetails(model);
            Assert.fail();
        }catch(RestServiceException ex){
         
            Assert.assertEquals(RestServiceLiterals.VEHICLE_YEAR_NOT_VALID_MESSAGE, ex.getMessage());
        }
    }
    
    @Test
    public void testPutVehicle(){
        Mockito.when(vehicleRepository.findById(Mockito.anyInt()))
        .thenReturn(getVehicleDetails(10));
        Mockito.when(vehicleRepository.saveAndFlush(Mockito.any()))
        .thenReturn(getVehicleDetails(10).get());
        
        Vehicle result = vehicleService.updateVehicleDetails(getModel(),10);
        Assert.assertNotNull(result);
        Assert.assertEquals("Toyota", result.getMake());
    }
    
    
    @Test
    public void testDelete(){
        Mockito.when(vehicleRepository.findById(Mockito.anyInt()))
        .thenReturn(getVehicleDetails(1));
        vehicleService.deleteVehicleById(1);
    }
    
    
    static Optional<VehicleDetails> getVehicleDetails(int id){
        VehicleDetails det = new VehicleDetails();
        det.setId(id);
        det.setMake("Toyota");
        det.setModelNo("Camry");
        det.setYear(2015);
        
        return Optional.of(det);
    }
    
    static Vehicle getModel(){
        Vehicle model = new Vehicle();
        model.setYear(2015);
        model.setMake("abc");
        model.setModel("test");
        return model;
    }
    
    
    
    
    
    
}
