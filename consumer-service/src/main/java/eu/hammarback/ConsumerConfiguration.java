package eu.hammarback;

import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class ConsumerConfiguration extends Configuration {

    @NotEmpty
    public String natsUrl;

    @NotEmpty
    public String clusterId;

    @NotEmpty
    public String clientId;

    @NotEmpty
    public String topicName = "my-topic";

}
