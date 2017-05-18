package eu.hammarback.infrastructure;

import eu.hammarback.MessageConsumer;
import io.nats.stan.Connection;
import io.nats.stan.ConnectionFactory;
import io.nats.stan.Subscription;
import io.nats.stan.SubscriptionOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class NatsMessageConsumer implements MessageConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String topicName;
    private final String natsUrl;
    private final String clusterId;
    private final String clientId;
    private final List<MessageListener> listeners = new ArrayList<>();

    private Connection connection;
    private Subscription subscription;

    public NatsMessageConsumer(String natsUrl, String clusterId, String clientId, String topicName) {
        this.natsUrl = natsUrl;
        this.clusterId = clusterId;
        this.clientId = clientId;
        this.topicName = topicName;
    }

    public void addListener(MessageListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void start() throws Exception {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setNatsUrl(natsUrl);
        cf.setClusterId(clusterId);
        cf.setClientId(clientId);
        connection = cf.createConnection();
        subscription = connection.subscribe(topicName, natsMessage ->
                        listeners.forEach(messageListener -> {
                            String message = new String(natsMessage.getData());
                            messageListener.onMessage(message);
                        }),
                new SubscriptionOptions.Builder()
                        .deliverAllAvailable()
                        .build());

    }

    @Override
    public void stop() throws Exception {
        logger.info("Shutting down consumer...");
        subscription.unsubscribe();
        connection.close();
    }

}
