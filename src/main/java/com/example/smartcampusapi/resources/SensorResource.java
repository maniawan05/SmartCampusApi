/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampusapi.resources;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.example.exception.LinkedResourceNotFoundException;


@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class SensorResource {
    
    @POST
    public Sensor createSensor(Sensor sensor){
        if(!DataStore.rooms.containsKey(sensor.getRoomId())){
            throw new LinkedResourceNotFoundException("Room does not exist for this sensor");
        }
        
        DataStore.sensors.put(sensor.getId(),sensor);
        
        Room room=DataStore.rooms.get(sensor.getRoomId());
        room.getSensorId().add(sensor.getId());
        return sensor;
    }
    
    
    @GET
    public Collection<Sensor> getSensors(@QueryParam("type") String type){
        if(type==null || type.isEmpty()){
            return DataStore.sensors.values();
        }
        
        List<Sensor> finalSensors=DataStore.sensors.values()
                .stream()
                .filter(sensor->sensor.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
        return finalSensors;
    }
    
    
}


