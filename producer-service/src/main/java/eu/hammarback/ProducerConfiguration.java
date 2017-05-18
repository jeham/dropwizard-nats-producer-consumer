package eu.hammarback;

import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class ProducerConfiguration extends Configuration {

    @NotEmpty
    public String natsUrl;

    @NotEmpty
    public String clusterId;

    @NotEmpty
    public String clientId;

}
