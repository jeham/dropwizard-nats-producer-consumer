package eu.hammarback;

import eu.hammarback.infrastructure.NatsMessageProducer;
import eu.hammarback.model.MessageProducer;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ProducerApplication extends Application<ProducerConfiguration> {

    @Override
    public void initialize(final Bootstrap<ProducerConfiguration> bootstrap) {
    }

    @Override
    public void run(final ProducerConfiguration config, final Environment environment) {
        MessageProducer messageProducer = new NatsMessageProducer(config.natsUrl, config.clusterId, config.clientId);
        environment.lifecycle().manage(messageProducer);
        environment.jersey().register(new ProducerResource(messageProducer));
    }

    public static void main(final String[] args) throws Exception {
        new ProducerApplication().run(args);
    }

}
