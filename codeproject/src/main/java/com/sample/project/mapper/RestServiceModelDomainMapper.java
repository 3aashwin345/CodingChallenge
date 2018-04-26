package com.sample.project.mapper;


import ma.glasnost.orika.MapperFactory;

public class RestServiceModelDomainMapper {
    
    private RestServiceModelDomainMapper() {
    }
    public static void registerClassMaps(MapperFactory factory) {

        
    
        mapVehicleDetails(factory);
        
      
    }
    
    
    private static void mapVehicleDetails(MapperFactory factory){
        factory
            .classMap(
                    com.sample.project.model.Vehicle.class,
                    com.sample.project.domain.VehicleDetails.class)
            .field("model", "modelNo")
            .mapNulls(false)
//            .customize(new MyCustomPowerChangeEventMapper())
            .byDefault()
            .register();
    }

}
