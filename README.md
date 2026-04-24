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

### Create a Sensor 

curl --location 'http://localhost:8080/SmartCampusApi/api/v1/sensors' \
--header 'Content-Type: application/json' \
--data '{"id": "S1","type": "CO2","status": "ACTIVE","currentValue": 0,"roomId": 1}'

### Add a Reading

curl --location 'http://localhost:8080/SmartCampusApi/api/v1/sensors/S1/readings' \
--header 'Content-Type: application/json' \
--data '{"id": "R1","timestamp": 1710000000,"value": 45.5}'

### Invalid Sensor Creation(422 Error)

curl --location 'http://localhost:8080/SmartCampusApi/api/v1/sensors' \
--header 'Content-Type: application/json' \
--data '{"id": "S99","type": "CO2","status": "ACTIVE","currentValue": 0,"roomId": 999}'
This request fails because the roomId does not exist.The Api returns a 422 Unprocessable Entity error.

## Error Handling

The API also has error handling using custom exception types and mappers (proper) to provide meaningful HTTP status codes:
- 404 Not Found
  .Resource is non-existent.
- 409 Conflict
  . A Room cannot be removed if there are Sensors associated with it.
- 422 Unprocessable Entity
  .When a new Sensor is created, an invalid Room Reference was provided for that Sensor creation request.

- 403 Forbidden
  .If a Sensor in Maintenance mode attempts to receive readings then this will result in a 403 Forbidden response from the server.

- 500 Internal Server Error
  . All other unexpected errors result in a 500 Internal Server Error response


 ## Coursework Questions

### Part 1

#### Question:

In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures.

#### Answer:

By default, JAX-RS resource classes are created per request, meaning a new instance is instantiated for each incoming request.Therefore, due to this approach of thread safety (each request is processed independently) we cannot utilize in-memory HashMaps like DataStore and need to store shared data in static classes. If the JAX-RS Resource Classes were singletons we would have had to take great care to ensure synchronization to prevent race conditions

#### Question:

Why is the provision of Hypermedia (HATEOAS) considered a hallmark of advanced RESTful design? How does this approach benefit client developers compared to static documentation?s


#### Answer:

Hypermedia provides a way for APIs to provide links to related resources in their responses. Therefore, hypermedia enables the client to discover the available resources and navigate the API at runtime without having to reference hard-coded URLs. In addition to being more dynamic and flexible than statically documented APIs, using hypermedia also improves usability for developers.


### Part 2

#### Question:

When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects?

#### Answer:
Return only IDs is going to reduce the amount of data returned over the network, improve performance, etc. especially with large amounts of data. On the other hand, return full objects) provides more detail about the resource (s) requested and minimizes the number of requests required. Depending upon your application you may want to find a good balance.


#### Question:

Is the DELETE operation idempotent in your implementation? Provide a detailed justification.

#### Answer:
Yes, DELETE is idempotent. So, if a client send multiple instances of the same DELETE request, after sending the first DELETE request there will be no resource remaining, so subsequent DELETE requests will result in a "Not Found" response and will do nothing else to modify the system further.


### Part 3

#### Question:

Explain the technical consequences if a client sends data in a different format (e.g., text/plain or application/xml) when the method expects JSON.

#### Answer:

If a client sends data in a format other than JSON (i.e. text/plain, XML), JAX-RS will not know how to map the request to the method annotated with
@Consumes(MediaType. APPLICATION_SON). As such, this will result in an HTTP 415 Unsupported Media Type error.

#### Question:

Why is using @QueryParam for filtering considered better than including the filter in the path?

#### Answer:

QueryParam's (e.g. /sensors?type=CO2) are generally used for filtering resources since they make it easy to apply multiple filters to the collection. PathParams' are generally used for accessing specific resources; hence, pathparams are best utilized for determining whether the resource identified exists, rather than applying filters to a collection.

### Part 4

#### Question:

Discuss the architectural benefits of the Sub-Resource Locator pattern.

#### Answer:

Sub-resource locators help break up the logic into smaller classes making it possible to write more modular and easier-to-maintain code. Prior to sub-resource locator patterns, many applications developed with RESTful web services were very monolithic and difficult to read/maintain/expand.

### Part 5

#### Question:

Why is HTTP 422 more semantically accurate than 404 when the issue is a missing reference inside a valid JSON payload?

#### Answer:

An HTTP status code of 422 is much more precise than a status code of 404. An example where this might be true is when the client attempts to create a resource and references a room that does not exist. Since the room referenced was correct (the room was created prior to attempting to create another resource referencing that room), but the data provided was incorrect the room did not exist), then an HTTP status code of 404 would indicate that the resource itself could not be located. A status code of 422 indicates that the request was invalid for some reason, but that does not necessarily mean that the requested resource could not be located.

#### Question:

From a cybersecurity standpoint, explain the risks of exposing stack traces.

#### Answer:

Stack traces reveal internal implementation details (class name, file location etc.) of your server. These can be attacked upon by malicious users and therefore, it is always better to return a generic error message instead of exposing them.

#### Question:

Why is it advantageous to use JAX-RS filters for logging instead of adding logging in every method?

#### Answer:

Filters allow logging to occur in one place for both incoming requests and outgoing responses. This eliminates the duplication of logging code in every resource method allowing your application to grow more easily and with less maintenance cost.


