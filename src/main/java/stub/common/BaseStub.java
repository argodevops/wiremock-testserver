package stub.common;

import org.apache.commons.csv.CSVParser;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tomakehurst.wiremock.WireMockServer;

/**
 * Base class for common stub tasks.
 */
public abstract class BaseStub {

    private static final Logger logger = LoggerFactory.getLogger(BaseStub.class);

    protected final Properties properties;

    protected CSVParser parser = null;

    /**
     * Constructor
     * 
     * @param name the stub endpoint name
     */
    protected BaseStub(final String name) {
        this.properties = this.readStubProperties(name);
    }

    /**
     * Configure the wiremock server for stub endpoint
     * 
     * @param wireMockServer the wiremock server
     */
    public abstract void configureEndpoints(final WireMockServer wireMockServer);

    /**
     * Reads the test data from a file
     * 
     * @param filename the test data file
     */
    public abstract void readTestData(final String filename);

    /**
     * Configure template for configuring the stub endpoint
     * 
     * @param wireMockServer the wiremock server
     */
    public void configure(final WireMockServer wireMockServer) {
        readTestData(this.properties.getProperty("testdata.filename"));

        configureEndpoints(wireMockServer);
    }

    /**
     * Reads the stub properties file
     * 
     * @param name the name of the stub endpoint
     * @return
     */
    private Properties readStubProperties(final String name) {
        Properties props = new Properties();
        try {
            props.load(BaseStub.class.getClassLoader().getResourceAsStream(name + ".properties"));
        } catch (IOException ioex) {
            logger.error("Failed to load stub properties", ioex);
        }
        return props;
    }

    /**
     * Returns the stub endpoint port
     * 
     * @return the port number
     */
    public int getPort() {
        return Integer.valueOf(properties.getProperty("server.port", "8089"));
    }
}
