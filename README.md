## Download NATS Streaming Server
 
    http://nats.io/download/nats-io/nats-streaming-server/

## Start NATS, local installation

    nats-streaming-server-v0.4.0.exe

## Start consumer

    java -jar target\consumer-service-1.0-SNAPSHOT.jar server config.yml
    
## Start producer

    java -jar target\producer-service-1.0-SNAPSHOT.jar server config.yml

## Post a message to the producer

    curl -H "Content-Type: application/json" -X POST -d '{"some-key":"some-value"}' http://localhost:8080/my-topic
