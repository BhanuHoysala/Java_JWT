# Java_JWT
Spring Boot and JWT security demo

### REST application demonstrates enabling JWT Authentication and Authorization to selected routes

### it has Swagger integration as well to explore the APIs

Security enabled for only one route http://localhost:8080/welcome . to test the security integration<br>
Create a new Driver from Swagger<br>
Authenticate by using the same username and password at POST-  http://localhost:8080/authenticate . It can also be done from the Swagger.<br>
Copy the token received from previous 2nd step and replace JWT_TOKEN in below step<br>
Either from REST Clients tool or command invoke this curl -X GET http://localhost:8080/welcome -H 'Authorization: Bearer <JWT_TOKEN>'<br>
