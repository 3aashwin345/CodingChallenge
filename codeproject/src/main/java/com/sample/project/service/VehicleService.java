package com.sample.project.service;

import java.util.List;

import com.sample.project.model.Vehicle;

public interface VehicleService {

    public List<Vehicle> getAllVehicles();
    
    public List<Vehicle> getVehiclesByMake(String make);
    
    public Vehicle getByVehicleId(Integer id);
    
    public Vehicle addVehicleDetails(Vehicle vehicle);
    
    public Vehicle updateVehicleDetails(Vehicle vehicle, Integer id);
    
    public void deleteVehicleById(Integer id);
    
}
