# wiremock-testserver

# running docker image
# Ensure to add the mock interface name to the INTERFACE var
docker run -e INTERFACE=example -p 8089:8089 wireframe_test

# Diagram Explanation

1. Java Gatling makes a request to a static CSV file stored on Azure, the file returns the name column of each record
2. Each name is sent to the service bus on Azure at the same rate as requests would be made in production
3. The names are stored in a queue on the service bus
4. Azure Functions is listening for changes to the queue and will retrieve the names and send them to API being tested
5. WireMock will exist in place of the live APIs and will use the names from the request to obtain further information from the orignal CSV
6. The data from the CSV is transformed and passed back to Java in XML format

![WireMockDiagram][Diagram]

[Diagram]: /WireMock_poc.png