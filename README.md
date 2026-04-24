# SmartCampusApi

## Overview
This project is a restful API for management of a smart campus system. Users are able to manage rooms, sensors and readings from the sensors. The API was developed with jax-rs as it follows the principles of REST.

## How to run
1. Open the project in NetBeans
2. Run the project
3. The API will be running at:
http://localhost:8080/SmartCampusApi/api/v1

## API ENDPOINTS

### Rooms
- GET /rooms → Get all rooms
- GET /rooms/{id} → Get a room by ID
- POST /rooms → Create a new room
- DELETE /rooms/{id} → Delete a room

### Sensors
- GET /sensors → Get all sensors
- GET /sensors/{id} → Get sensor by ID
- POST /sensors → Create a new sensor

### Sensor Readings
- GET /sensors/{id}/readings → Get readings for a sensor
- POST /sensors/{id}/readings → Add a new reading

## Sample curl Commands

### Create a Room
curl --location 'http://localhost:8080/SmartCampusApi/api/v1/rooms' \
--header 'Content-Type: application/json' \
--data '{"id": "1","name": "Lab","capacity": 30}

