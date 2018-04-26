package com.sample.project.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.project.model.ResponseInfo;
import com.sample.project.model.Vehicle;
import com.sample.project.service.VehicleService;
/**
 * 
 *  controller class
 * @author 
 *
 */
@RestController
@RequestMapping("/codingchallenge/v1")
public class VehicleController {
    
    @Autowired
    VehicleService service;
    /**
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/ping/", produces = "text/plain")
    @ResponseBody
    public String ping() {

        return "Vehicle service is up and running!!!";
    }
    
    /**
     * 
     * return all vehicles information available
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "", produces = "application/json")
    @ResponseBody
    public List<Vehicle> getAllVehicleDetails() {
        
        return service.getAllVehicles();
    }
    
    /**
     * returns vehicle information based in id
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
    @ResponseBody
    public Vehicle getVehicleDetails(@PathVariable Integer id) {
        
        return service.getByVehicleId(id);
    }
    
    
    /**
     * filter vehicles based on make
     * 
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "", params={"make"}, produces = "application/json")
    @ResponseBody
    public List<Vehicle> getVehicleDetails(@RequestParam(value = "make", required = true) String make) {
        return service.getVehiclesByMake(make);
    }
    
    /**
     * update a vehicle details
     * 
     * @param details
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", 
            produces = "application/json", consumes="application/json")
    @ResponseBody
    public ResponseEntity<ResponseInfo> updateVehicleDetails(@RequestBody Vehicle details, 
            @PathVariable(value = "id", required = true) Integer id) {
        service.updateVehicleDetails(details, id);
        ResponseInfo info = new ResponseInfo(200,"Vehicle info updated successfully");
        return new ResponseEntity<ResponseInfo>(info, HttpStatus.OK);
        
    }
    
    /**
     * add vehicle details
     * 
     * @param details
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "",
            produces = "application/json", consumes="application/json")
    @ResponseBody
    public ResponseEntity<ResponseInfo> addVehicleDetails(@RequestBody Vehicle details) {
        Vehicle veh = service.addVehicleDetails(details);
        ResponseInfo info = new ResponseInfo(200,"Vehicle info added successfully with id:"+veh.getId());
        return new ResponseEntity<ResponseInfo>(info, HttpStatus.OK);
        
    }
    
    /**
     * delete vehicle details with id
     * 
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<ResponseInfo> deleteVehicleDetailsById(@PathVariable Integer id) {
        
        service.deleteVehicleById(id);
        ResponseInfo info = new ResponseInfo(200,"Vehicle details deleted successfully with id:"+id);
        return new ResponseEntity<ResponseInfo>(info, HttpStatus.OK);
    }

}
