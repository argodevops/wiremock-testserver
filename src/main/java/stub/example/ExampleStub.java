package stub.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stub.common.BaseStub;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Example stub endpoint to put examples are experiment with different response logic.
 */
public class ExampleStub extends BaseStub {
    
    final static Logger logger = LoggerFactory.getLogger(ExampleStub.class);

    final Map<String, String> testData = new HashMap<>();

    public ExampleStub(final String name) {
        super(name);
    }
 
    @Override
    public void configureEndpoints(final WireMockServer wireMockServer) {
        logger.info("Configure endpoints");
 
        wireMockServer.stubFor(get("/some/thing")
            .willReturn(aResponse().withStatus(200)));
 
        wireMockServer.stubFor(delete("/fine")
            .willReturn(ok()));
 
        wireMockServer.stubFor(get(urlPathEqualTo("/hello"))
            .willReturn(okJson(testData.get("hello"))));

         wireMockServer.stubFor(get(urlPathEqualTo("/goodbye"))
            .willReturn(okJson(testData.get("goodbye"))));
 
        wireMockServer.stubFor(get("/json")
            .willReturn(okJson("{ \"message\": \"Hello\" }")));
  
        wireMockServer.stubFor(get("/sorry-no")
            .willReturn(unauthorized()));
 
        wireMockServer.stubFor(get("/status-only")
            .willReturn(status(418)));
    }

    @Override
    public void readTestData(final String filename) {
        try (Reader reader = new FileReader(filename)) {
            if (FilenameUtils.isExtension(filename, "csv")) {
                try (CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
                    for (CSVRecord csvRecord : parser) {
                        testData.put(csvRecord.get("name"), csvRecord.get("value"));
                    }
                }
            } else {
                throw new IOException("Unknown file type " + filename);
            }

        } catch (IOException e) {
            logger.error("Failed to open file {}", filename);
        }
    }
}
