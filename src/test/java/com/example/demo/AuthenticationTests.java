package com.example.demo;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import net.minidev.json.JSONObject;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationTests {
    private Logger logger = LoggerFactory.getLogger(AuthenticationTests.class);

    final String host = "http://localhost:";
    final String createUserUrl = "/api/user/create";
    final String loginUrl = "/login";
    private String testUsername = null;

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate = mock(RestTemplate.class);
    private HttpHeaders headers;
    private JSONObject userJsonObject;

    @Order(1)
    @Test
    public void createUserTest() throws Exception {
        logger.info("Authentication test: CreateUserTest");
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.userJsonObject = new JSONObject();
        userJsonObject.put("username", "Test Guy");
        userJsonObject.put("password", "abcdefg");
        userJsonObject.put("confirmPassword", "abcdefg");

        HttpEntity<String> request = new HttpEntity<String>(userJsonObject.toString(), headers);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<String> responseEntityStr = this.restTemplate.postForEntity(host + port + createUserUrl, request,
                String.class);
        JsonNode entityRoot = objectMapper.readTree(responseEntityStr.getBody());

        System.out.println("\n\n\n\n\n Rest template result " + responseEntityStr.getBody());

        assertNotNull(responseEntityStr);
        assertEquals("\"Test Guy\"", entityRoot.get("username").toString());
    }

    @Order(2)
    @Test
    public void getJWTTest() throws Exception {
        logger.info("Authentication test: getJWTTest");
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.userJsonObject = new JSONObject();
        userJsonObject.put("username", "New Guy");
        userJsonObject.put("password", "abcdefg");
        userJsonObject.put("confirmPassword", "abcdefg");

        HttpEntity<String> request = new HttpEntity<String>(userJsonObject.toString(), headers);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<String> responseEntityStr = this.restTemplate.postForEntity(host + port + createUserUrl, request,
                String.class);
        JsonNode entityRoot = objectMapper.readTree(responseEntityStr.getBody());
        this.restTemplate = new RestTemplate();

        userJsonObject.remove("confirmPassword");
        HttpEntity<String> loginRequest = new HttpEntity<String>(userJsonObject.toString(), headers);
        ResponseEntity<String> loginEntityStr = this.restTemplate.postForEntity(host + port + loginUrl, loginRequest,
                String.class);

        HttpHeaders responseHeaders = loginEntityStr.getHeaders();
        assertNotNull(responseHeaders);
        assertNotNull(responseHeaders.get("Authorization"));

    }

    @Order(3)
    @Test
    public void useJWT_and_getUser() throws Exception {
        logger.info("Authentication test: getJWTTest");
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.userJsonObject = new JSONObject();
        userJsonObject.put("username", "Third Guy");
        userJsonObject.put("password", "abcdefg");
        userJsonObject.put("confirmPassword", "abcdefg");

        HttpEntity<String> request = new HttpEntity<String>(userJsonObject.toString(), headers);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<String> responseEntityStr = this.restTemplate.postForEntity(host + port + createUserUrl, request,
                String.class);
        JsonNode entityRoot = objectMapper.readTree(responseEntityStr.getBody());

        try {
            userJsonObject.remove("confirmPassword");
        } catch (Exception e) {
            System.out.println(e);
        }

        HttpEntity<String> loginRequest = new HttpEntity<String>(userJsonObject.toString(), headers);
        ResponseEntity<String> loginEntityStr = this.restTemplate.postForEntity(host + port + loginUrl, loginRequest,
                String.class);

        HttpHeaders responseHeaders = loginEntityStr.getHeaders();

        String authHeader = responseHeaders.get("Authorization").get(0);
        headers.add("Authorization", authHeader);

        HttpEntity<String> getUserRequest = new HttpEntity<String>(null, headers);
        ObjectMapper userObjectMapper = new ObjectMapper();
        ResponseEntity<String> responseUserStr = restTemplate.exchange(host + port + "/api/user/Third Guy",
                HttpMethod.GET, getUserRequest, String.class);

        JsonNode userRoot = userObjectMapper.readTree(responseUserStr.getBody());

        assertNotNull(userRoot);
        assertEquals("\"Third Guy\"", userRoot.get("username").toString());

    }

    @Order(4)
    @Test
    public void FailedLoginTest() throws Exception {
        logger.info("Authentication test: FailedLoginTest");
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.userJsonObject = new JSONObject();
        userJsonObject.put("username", "ForgetfulUser");
        userJsonObject.put("password", "abcdefg");
        userJsonObject.put("confirmPassword", "abcdefg");

        HttpEntity<String> request = new HttpEntity<String>(userJsonObject.toString(), headers);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<String> responseEntityStr = this.restTemplate.postForEntity(host + port + createUserUrl, request,
                String.class);
        JsonNode entityRoot = objectMapper.readTree(responseEntityStr.getBody());
        this.restTemplate = new RestTemplate();

        userJsonObject.remove("confirmPassword");
        userJsonObject.replace("password", "abcdefg", "iforgotmypassword");
        HttpEntity<String> loginRequest = new HttpEntity<String>(userJsonObject.toString(), headers);
        ResponseEntity<String> loginEntityStr = new ResponseEntity<String>(HttpStatus.OK);
        try {
            loginEntityStr = this.restTemplate.postForEntity(host + port + loginUrl, loginRequest, String.class);
        } catch (BadCredentialsException e) {
            System.out.println("\n\n\n\n\n" + loginEntityStr + "" +  e);
        } catch (HttpClientErrorException e) {
            assertEquals(e.getClass(), HttpClientErrorException.Forbidden.class);    
        }
        assertNotNull(loginRequest);
    }

}
