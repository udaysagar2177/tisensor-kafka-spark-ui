# TiSensor-Kafka-Spark-UI

This application is a demo of using Kafka and Spark to do real-time processing on the data. 

## How To Run:

**Note:** Check `com.rest.config.Constants` class and setup your environment variables or modify default values to fit your configuration.

### REST API
A simple way is to use tomcat7-maven-plugin. The dependency is included in the pom.xml. For IntelliJ, add a new 'Run Configuration' of type 'Maven' with the command `tomcat7:run`.
You can however create and deploy a war file on an Apache Tomcat server.

### Loading Tool
To simulate the data pumping from TiSensor, execute `rest-api/src/main/java/com/load/SimulatedTiSensor.java`