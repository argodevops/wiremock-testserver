package stub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stub.common.BaseStub;
import stub.example.ExampleStub;
import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.io.IOException;

public class Main {
    final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) throws IOException {
        
        String option = null;

        if (args.length == 0) {
            logger.info("Provide a mock interface name to simulate");
            return;
        } else {    
            option = args[0];
        }
        
        BaseStub stub = null;
    
        switch (option.toLowerCase()) {
        case "example":
            logger.info("Starting stub");
            stub = new ExampleStub(option);
            break;
        default:
            logger.error("Unknown stub interface: " + option);
            return;
        }

        // Start wiremock server. See https://wiremock.org/docs/configuration for configuring server for higher performance.
        logger.info("WireMock server starting - {}", stub.getPort());
        WireMockServer wireMockServer = new WireMockServer(options().port(stub.getPort()));
        wireMockServer.start();
        logger.info("WireMock server started");
        
        stub.configure(wireMockServer);
    }
}
