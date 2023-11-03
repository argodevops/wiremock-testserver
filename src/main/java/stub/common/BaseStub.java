package stub.common;

import java.util.Properties;

import com.github.tomakehurst.wiremock.WireMockServer;

/**
 * Base class for common stub tasks.
 */
public abstract class BaseStub {

    protected Properties properties = null;

    public void configure(final WireMockServer wireMockServer, final Properties properties) {
        this.properties = properties;
        
        readTestDataFile(null);

        configureEndpoints(wireMockServer);
    }

    
    public void readTestDataFile(final String filename) {
        
    }

    public abstract void configureEndpoints(final WireMockServer wireMockServer);
}
