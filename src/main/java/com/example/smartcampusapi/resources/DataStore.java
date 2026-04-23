/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampusapi.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author maniawan
 */
public class DataStore {
    
    public static Map<String,Room> rooms=new HashMap<>();
    public static Map<String,Sensor> sensors=new HashMap<>();
    public static Map<String,List<SensorReading>> readings=new HashMap<>();
    
    
}
