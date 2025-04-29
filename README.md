# Network Application 

## Overview
- this Spring Boot application uses Neo4j to showcase the uses of a GraphDB, highlighting the functionality gained

## Project Setup
- **Note it is highly recommend to use intellij as IDE for simplicity**
  - To get started with application first ensure that maven file dependencies are loaded
    - Right click `pom.xml` within main directory
    - Find `maven` tab and hover over it
    - Click the option `reload project`
  - After loading maven dependencies, a run configuration should load called `NetworkapplicationApplication`, this is the primary means of running the Spring Boot app.
    - **Note to properly run application make sure to add profile `local` when running, by adding it under profiles within run configurations** 
  - Now to set up Neo4j server we leverage docker to handle the configuration for us.
    - Make sure docker client is installed
      - https://docs.docker.com/desktop/setup/install/windows-install/
    - Next within the base project directory `../networkapplication` run the make command `make neo4j-server`
      - If you do not have make installed simply copy the command from the Makefile file

## Project Usage
- This project supports basic CRUD functionality by utilizing basic REST api endpoints.
- These endpoints can be called directly or by using the swagger ui found [here](http://localhost:8080/swagger-ui/index.html) when running app
- By nature of neo4j docker server data does not persist when server is stopped so mock data can be generated, as well as mock relations.
