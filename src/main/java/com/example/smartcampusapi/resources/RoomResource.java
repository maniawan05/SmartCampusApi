/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampusapi.resources;

import java.util.Collection;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.example.exception.DataNotFoundException;
import com.example.exception.RoomNotEmptyException;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {
    
    @GET
    public Collection<Room> getAllRooms(){
        return DataStore.rooms.values();
    }
    
    @POST
    public Room createRoom(Room room){
        DataStore.rooms.put(room.getId(),room);
        return room;
    }
    
    @GET
    @Path("/{id}")
    
    public Room getRoom(@PathParam("id") String id){
       
        Room room=DataStore.rooms.get(id);
        
        if(room==null){
            throw new DataNotFoundException("Room not found");
        }
        return room;
    }
    
    @DELETE
    @Path("/{id}")
    public void deleteRoom(@PathParam("id") String id){
        
        Room room=DataStore.rooms.get(id);
        if(room==null){
            throw new DataNotFoundException("Room not Found");
        }
        
        if(!room.getSensorId().isEmpty()){
            throw new RoomNotEmptyException("Room cannot be deleted beacuse it still has sensors.");
        }
        
        DataStore.rooms.remove(id);
        
    }
    
}
