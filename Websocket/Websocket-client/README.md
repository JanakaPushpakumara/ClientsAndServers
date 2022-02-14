# Steps to Start the secure Websocket-Client
1. Build the project with, 
```sh
mvn clean install
```
2. Update the src/main/resources/config.properties file with the necessary parameters.

Ex:
```sh
# for external API
# wssurl:wss://localhost:8099/EchoWebSocket/1.0.0
# token:xxxxxxxxx

# for local server
wssurl:wss://localhost:8090

truststorepath:src/main/resources/client-truststore.jks
storepassword:wso2carbon
keypassword:wso2carbon
```
3. Run the jar file with, 
```sh
java -jar target/Websocket-client-1.0-SNAPSHOT-jar-with-dependencies.jar src/main/resources/config.properties
```
Ex: output 

```sh
Connected
got: Welcome to the server!
got: new connection: /
```