package stub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stub.example.ExampleStub;
import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.util.Properties;

public class Main {
    final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        System.out.println("Main");
        args[0] = System.getenv("INTERFACE");
        if (args.length == 0) {
            logger.info("Provide a mock interface name to simulate");
            return;
        }

        String option = args[0];

        ExampleStub stub = null;

        switch (option.toLowerCase()) {
            case "example":
                logger.info("Starting Example stub");
                stub = new ExampleStub();
                break;
            default:
                logger.error("Unknown stub interface: " + option);
                return;
        }

        // Start wiremock server. See https://wiremock.org/docs/configuration for
        // configuring server for higher performance.
        logger.info("Starting WireMock server");
        // CPF TODO
        // Read in example.properties file
        // server.port
        // testdata.filename
        final Properties properties = new Properties();

        WireMockServer wireMockServer = new WireMockServer(options().port(8089));
        wireMockServer.start();
        logger.info("Started WireMock server");

        stub.configure(wireMockServer, properties);
    }
}
