# Test project for NTC Argus based on project for NPP Telda
A sample project using Spring-boot, Mybatis, GraphQL and H2 database

This is a simple region catalog, where someone can execute basic CRUD operations on regions. Using Mybatis entities are mapped in mapper. Annotations are used instead of xml.This project have rest end points for insert, update, delete, get by Id, getAll regions with filters by name and shortName. In addition, the application have API to let clients get data in JSON format with only fields what they needed. 
# Technical Stack description
__Spring Boot__ as core of the project.  The framework takes an opinionated approach to configuration, freeing developers from the need to define boilerplate configuration.

__H2__ as database. Runned in in-memory mode.

__MyBatis__ as ORM. In this project i have used annotations instead of xml configuration.

__Mapstruct__ as mapper. Used to provide a conversion from entity to DTO and vice versa.

__JUnit 5__ as testing framework. Project included controller-, mapper-, repository- and service layer testing.

__DBRider__ as framework for testing with own h2 dataset (with data.yml).

__Swagger__ to document API. Access on: <a href="http://localhost:8080/swagger-ui/index.html">link</a>.

__GraphQL__ as API that gives clients the power to ask for exactly what they need.
## You can simlpy run this application and interact with it using url`s
method |	functionality |	url |	request body
--- | --- | --- | ---
GET |	get all regions |	http://localhost:8080/api/regions |	N/A
POST |	create a new |	http://localhost:8080/api/regions |	{"id": 1, "name": "Belgorod", "shortName": "BEL"}
PATCH |	update an existing region |	http://localhost:8080/api/regions/1 |	{"id": 1, "name": "Belgorod2", "shortName": "BEL2"}
DELETE |	delete a region |	http://localhost:8080/api/regions/1 |	N/A
GET |	get a region by id |	http://localhost:8080/api/regions/1 |	N/A

_You can use case-insensitive parameters on 'get all regions' request to filter response:_ ***?name=BelGo&shortName=eL***
# GraphQL API description
You can use address <a href="http://localhost:8080/graphql">http://localhost:8080/graphql </a> for asking server with query language what data you need.

Also, you can use UI on <a href="http://localhost:8080/graphiql">link</a> to check schema and sending queries with Documentation Explorer.

For getting data you can use __allRegions__ (additionally with a filter by name and shortName) or __region__ queries: 

```
#Get all regions
query{
  allRegions(name: "gor", shortName: "B"){
    id,
    name,
    shortName
  }
}

#OR

#Get one region by id
query{
  region(id: 1){
    name
  }
}
```

For modifying data you can use __createRegion(name, shortName)__, __updateRegion(id, name, shortName)__ or __deleteRegion__ mutations:

 ```
#Create
mutation {
  createRegion(name: "AnyName", shortName: "ANY"){
    id,
    name,
    shortName
  }
}

#Update
mutation {
  updateRegion(id: 1, name: "bEeeee", shortName: "BEL")
}

#Delete
mutation {
  deleteRegion(id: 1)
}
 ```