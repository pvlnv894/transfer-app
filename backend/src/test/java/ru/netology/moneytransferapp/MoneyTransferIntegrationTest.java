package ru.netology.moneytransferapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoneyTransferIntegrationTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    static final GenericContainer<?> moneyTransferApp =
            new GenericContainer<>("transfer-app-backend:latest").withExposedPorts(8080);

    @BeforeAll
    static void setUp() {
        moneyTransferApp.start();
    }

    private ResponseEntity<String> post(String path, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(
                "http://localhost:" + moneyTransferApp.getMappedPort(8080) + path,
                request,
                String.class
        );
    }

    @Test
    void addTransferTest() throws Exception {
        String transfer = """
                {
                    "cardFromNumber": "5567567834254897",
                    "cardFromValidTill": "12/29",
                    "cardFromCVV": "342",
                    "cardToNumber": "7654637482790154",
                    "amount": {
                        "value": 10000,
                        "currency": "RUR"
                    }
                }""";

        ResponseEntity<String> forEntity = post("/transfer", transfer);

        assertEquals(HttpStatus.OK, forEntity.getStatusCode());

        JsonNode jsonNode = objectMapper.readTree(forEntity.getBody());
        assertTrue(jsonNode.has("operationId"));

        String operationId = jsonNode.get("operationId").asText();
        assertFalse(operationId.isEmpty());
    }

    @Test
    void addTransferErrorTransferTest() throws Exception {
        String transfer = """
                {
                    "cardFromNumber": "5567567834254897",
                    "cardFromValidTill": "12/29",
                    "cardFromCVV": "342",
                    "cardToNumber": "7654637482790154",
                    "amount": {
                        "value": -10000,
                        "currency": "RUR"
                    }
                }""";

        ResponseEntity<String> forEntity = post("/transfer", transfer);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, forEntity.getStatusCode());
    }

    @Test
    void addTransferErrorInputDataTest() throws Exception {
        String transfer = """
                {
                    "cardFromNumber": "5567567834254897",
                    "cardFromValidTill": "12/29",
                    "cardFromCVV": "34",
                    "cardToNumber": "7654637482790154",
                    "amount": {
                        "value": 10000,
                        "currency": "RUR"
                    }
                }""";

        ResponseEntity<String> forEntity = post("/transfer", transfer);
        assertEquals(HttpStatus.BAD_REQUEST, forEntity.getStatusCode());
    }

    @Test
    void addConfirmOperationTest() throws Exception {
        String transfer = """
                {
                    "cardFromNumber": "5567567834254897",
                    "cardFromValidTill": "12/29",
                    "cardFromCVV": "342",
                    "cardToNumber": "7654637482790154",
                    "amount": {
                        "value": 10000,
                        "currency": "RUR"
                    }
                }""";

        ResponseEntity<String> transferForEntity = post("/transfer", transfer);

        assertEquals(HttpStatus.OK, transferForEntity.getStatusCode());

        JsonNode jsonNode = objectMapper.readTree(transferForEntity.getBody());
        assertTrue(jsonNode.has("operationId"));

        String operationId = jsonNode.get("operationId").asText();
        assertFalse(operationId.isEmpty());

        String confirmOperation = String.format("""
                {
                    "operationId": "%s", 
                    "code": "0000"
                }""", operationId);

        ResponseEntity<String> confirmOperationForEntity = post("/confirmOperation", confirmOperation);

        assertEquals(HttpStatus.OK, confirmOperationForEntity.getStatusCode());

        jsonNode = objectMapper.readTree(confirmOperationForEntity.getBody());
        assertTrue(jsonNode.has("operationId"));

        operationId = jsonNode.get("operationId").asText();
        assertFalse(operationId.isEmpty());
    }

    @Test
    void addConfirmOperationErrorConfirmationTest() throws Exception {
        String transfer = """
                {
                    "cardFromNumber": "5567567834254897",
                    "cardFromValidTill": "12/29",
                    "cardFromCVV": "342",
                    "cardToNumber": "7654637482790154",
                    "amount": {
                        "value": 10000,
                        "currency": "RUR"
                    }
                }""";

        ResponseEntity<String> transferForEntity = post("/transfer", transfer);

        assertEquals(HttpStatus.OK, transferForEntity.getStatusCode());

        JsonNode jsonNode = objectMapper.readTree(transferForEntity.getBody());
        assertTrue(jsonNode.has("operationId"));

        String operationId = jsonNode.get("operationId").asText();
        assertFalse(operationId.isEmpty());

        String confirmOperation = String.format("""
                {
                    "operationId": "%s", 
                    "code": "1111"
                }""", operationId);

        ResponseEntity<String> confirmOperationForEntity = post("/confirmOperation", confirmOperation);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, confirmOperationForEntity.getStatusCode());
    }

    @Test
    void addConfirmOperationErrorInputDataTest() throws Exception {
        String transfer = """
                {
                    "cardFromNumber": "5567567834254897",
                    "cardFromValidTill": "12/29",
                    "cardFromCVV": "342",
                    "cardToNumber": "7654637482790154",
                    "amount": {
                        "value": 10000,
                        "currency": "RUR"
                    }
                }""";

        ResponseEntity<String> transferForEntity = post("/transfer", transfer);

        assertEquals(HttpStatus.OK, transferForEntity.getStatusCode());

        String confirmOperation = """
                {
                    "operationId": "0", 
                    "code": "0000"
                }""";

        ResponseEntity<String> confirmOperationForEntity = post("/confirmOperation", confirmOperation);

        assertEquals(HttpStatus.BAD_REQUEST, confirmOperationForEntity.getStatusCode());
    }
}
