package eu.hammarback;

import eu.hammarback.infrastructure.NatsMessageConsumer;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ConsumerApplication extends Application<ConsumerConfiguration> implements MessageConsumer.MessageListener {

    @Override
    public void initialize(final Bootstrap<ConsumerConfiguration> bootstrap) {
    }

    @Override
    public void run(final ConsumerConfiguration config, final Environment environment) {
        MessageConsumer messageConsumer = new NatsMessageConsumer(config.natsUrl, config.clusterId,
                config.clientId, config.topicName);
        messageConsumer.addListener(this);
        environment.lifecycle().manage(messageConsumer);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("message received = " + message);
    }

    public static void main(final String[] args) throws Exception {
        new ConsumerApplication().run(args);
    }

}
