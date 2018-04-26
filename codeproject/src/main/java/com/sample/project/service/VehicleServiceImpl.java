package com.sample.project.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sample.project.dao.repository.VehicleRepository;
import com.sample.project.domain.VehicleDetails;
import com.sample.project.exception.RestServiceException;
import com.sample.project.literals.RestServiceLiterals;
import com.sample.project.model.Vehicle;

import ma.glasnost.orika.MapperFacade;

@Service("vehicleService")
public class VehicleServiceImpl implements VehicleService {
    
    @Resource
    VehicleRepository repo;
    
    @Resource(name = "restServiceModelDomainMapperFacade")
    private MapperFacade mapper;
    
    /**
     * add vehicle details
     */
    @Override
    public Vehicle addVehicleDetails(Vehicle vehicle){
        
        validate(vehicle);
        
        VehicleDetails domain = mapper.map(vehicle, VehicleDetails.class);
                
        domain = repo.save(domain);
       
        return mapper.map(domain, Vehicle.class);
    }

    /**
     * get all vehicles
     */
    @Override
    public List<Vehicle> getAllVehicles(){
        List<VehicleDetails> results = repo.findAll();
        return mapper.mapAsList(results, Vehicle.class);
    }
    
    /**
     * get vehicles by make
     */
    @Override
    public List<Vehicle> getVehiclesByMake(String make){
        List<VehicleDetails> results = repo.findByMake(make);
        return mapper.mapAsList(results, Vehicle.class);
    }
    
    /**
     * get vehicle details by id
     */
    @Override
    public Vehicle getByVehicleId(Integer id){
        VehicleDetails domain = throwIfNotFound(repo.findById(id));
     
        return mapper.map(domain, Vehicle.class);
        
    }
    
    /**
     * deletes vehicle details by id 
     */
    @Override
    public void deleteVehicleById(Integer id){
        VehicleDetails domain = throwIfNotFound(repo.findById(id));
        
        repo.delete(domain);
        
    }
    
    /**
     * update any vehicle details
     */
    @Override
    public Vehicle updateVehicleDetails(Vehicle model, Integer id){
        VehicleDetails domain = throwIfNotFound(repo.findById(id));
        domain = validateAndPatchRequest(domain, model);
        domain = repo.saveAndFlush(domain);
        return mapper.map(domain, Vehicle.class);
    }
    
    /**
     * 
     * @param domain
     * @return
     */
    private VehicleDetails throwIfNotFound(Optional<VehicleDetails> domain){
        if(!domain.isPresent()){
            throw new RestServiceException(RestServiceLiterals.VEHICLE_NOT_FOUND_CD,
                    RestServiceLiterals.VEHICLE_NOT_FOUND_MESSAGE);
        }
        return domain.get();
    }
    
    /**
     * 
     * @param domain
     * @param model
     * @return
     */
    private VehicleDetails validateAndPatchRequest(VehicleDetails domain, Vehicle model){
        
        if(!StringUtils.isEmpty(model.getMake())){
           domain.setMake(model.getMake());
        }else if(!StringUtils.isEmpty(model.getModel())){
            domain.setModelNo(model.getModel());
        }else if(model.getYear().intValue()>0){
            domain.setYear(model.getYear());
        }
        
        validate(mapper.map(domain, Vehicle.class));
        return domain;
    }
    
    /**
     * 
     * @param vehicle
     */
    private void validate(Vehicle vehicle){
        int year = vehicle.getYear();
        if(StringUtils.isEmpty(vehicle.getMake())){
            throw new RestServiceException(RestServiceLiterals.VEHICLE_MAKE_IS_NULL_CD,
                    RestServiceLiterals.VEHICLE_MAKE_IS_NULL_MESSAGE);
        }else if(StringUtils.isEmpty(vehicle.getModel())){
            throw new RestServiceException(RestServiceLiterals.VEHICLE_MODEL_IS_NULL_CD,
                    RestServiceLiterals.VEHICLE_MODEL_IS_NULL_MESSAGE);
        }else if(year > 2050 || year < 1950){
            throw new RestServiceException(RestServiceLiterals.VEHICLE_YEAR_NOT_VALID_CD,
                    RestServiceLiterals.VEHICLE_YEAR_NOT_VALID_MESSAGE);
        }
    }
}
