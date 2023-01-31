# MusalaDrone
Musala interview task submission for Tinashe Mugaro


interview task submission for Tinashe Mugaro

Instructions

The user must have at least the JDK 1.17 version installed on their machine (which is available on the Oracle website) and the java environment variables properly set. On a Windows 10 machine follow this path:control panel -> System and Security -> System -> Advanced System Settings ,then click on path and add the path to the bin file present in the JRE folder and the JDK folder to the Path variable. The database credentials are in the application.properties file

To pull your dependencies and run the application

mvn clean install 
java -jar target/drone-0.0.1-SNAPSHOT.jar 

The files within the project with endpoints to call are DroneController.java

Below are examples of how to call the endpoints:

Checking Available Drones

curl --location --request GET 'http://localhost:8080/drones'

Registering a Drone

curl --location --request POST 'http://localhost:8080/drone/'
--header 'Content-Type: application/json'
--data-raw '{ "droneModel": "MIDDLE_WEIGHT", "weightLimit": 500, "batteryCapacityPercent": 100 }'
