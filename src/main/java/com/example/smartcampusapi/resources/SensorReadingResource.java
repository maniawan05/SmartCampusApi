/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampusapi.resources;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.*;
import com.example.exception.SensorUnavailableException;
import com.example.exception.DataNotFoundException;

@Path("/sensors/{sensorId}/readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {
    
    @POST
    public SensorReading newReading(@PathParam("sensorId") String sensorId,SensorReading reading){
        if(!DataStore.sensors.containsKey(sensorId)){
            throw new DataNotFoundException("Sensor not found");
            
        }
        
        Sensor sensor=DataStore.sensors.get(sensorId);
        if("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())){
            throw new SensorUnavailableException("Sensor is under maintenance");
        }
        
        DataStore.readings.putIfAbsent(sensorId, new ArrayList<>());
        DataStore.readings.get(sensorId).add(reading);
        
        
        sensor.setCurrentValue(reading.getValue());
        
        return reading;
        
    }
    
    
    
    @GET
    public List<SensorReading> getReadings(@PathParam("sensorId") String sensorId){
        
        if (!DataStore.sensors.containsKey(sensorId)){
            throw new RuntimeException("Sensor not found");
            
        }
        
        return DataStore.readings.getOrDefault(sensorId,new ArrayList<>());
    }
        
        
   
    
    
}
