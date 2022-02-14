# Steps to Start the secure Websocket-Server
1. Build the project with, 
```sh
mvn clean install
```
2. Update the src/main/resources/config.properties file with the necessary parameters.

Ex:
```sh
port:8090
keystorepath:src/main/resources/wso2carbon.jks
storepassword:wso2carbon
keypassword:wso2carbon
```
3. Run the jar file with, 
```sh
java -jar target/Websocket-Server-1.0-SNAPSHOT-jar-with-dependencies.jar src/main/resources/config.properties
```
4. The server can be accessed via the flowing URL.
```sh
wss://localhost:8090
```
Ex: Output
```sh
Server started!
```
