package stub.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stub.common.BaseStub;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ExampleStub extends BaseStub {
    
    final static Logger logger = LoggerFactory.getLogger(ExampleStub.class);
 
    @Override
    public void configureEndpoints(final WireMockServer wireMockServer) {
        logger.info("Configure endpoints");
 
        wireMockServer.stubFor(get("/some/thing")
            .willReturn(aResponse().withStatus(200)));
 
        wireMockServer.stubFor(delete("/fine")
            .willReturn(ok()));
 
        wireMockServer.stubFor(get("/fine-with-body")
            .willReturn(ok("body content")));
 
        wireMockServer.stubFor(get("/json")
            .willReturn(okJson("{ \"message\": \"Hello\" }")));
 
        wireMockServer.stubFor(post("/redirect")
            .willReturn(temporaryRedirect("/new/place")));
 
        wireMockServer.stubFor(get("/sorry-no")
            .willReturn(unauthorized()));
 
        wireMockServer.stubFor(get("/status-only")
            .willReturn(status(418)));
    }
}
