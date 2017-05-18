package eu.hammarback.infrastructure;

import eu.hammarback.model.MessageProducer;
import io.nats.stan.Connection;
import io.nats.stan.ConnectionFactory;

import java.io.IOException;

public class NatsMessageProducer implements MessageProducer {

    private final String natsUrl;
    private final String clusterId;
    private final String clientId;

    private Connection connection;

    public NatsMessageProducer(String natsUrl, String clusterId, String clientId) {
        this.natsUrl = natsUrl;
        this.clusterId = clusterId;
        this.clientId = clientId;
    }

    @Override
    public void start() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setNatsUrl(natsUrl);
        connectionFactory.setClusterId(clusterId);
        connectionFactory.setClientId(clientId);
        connection = connectionFactory.createConnection();
    }

    @Override
    public void send(String topicName, String message) {
        try {
            connection.publish(topicName, message.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() throws Exception {

        this.connection.close();
    }

}
