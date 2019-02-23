package org.manu.threadlocal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/hello",
                String.class)).isEqualTo("Hello ");
    }

    @Test
    public void greetingShouldReturnUserNameFromQueryParameter() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/hello?name=Manu&address=nnn",
                String.class)).isEqualTo("Hello Manu");
    }


    @Test
    public void greetingShouldReturnUserNameFromHeader() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("name", "Raj");

        HttpEntity entityReq = new HttpEntity(headers);


        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/hello?name=Manu&address=nnn",
                HttpMethod.GET, entityReq,
                String.class).getBody()).isEqualTo("Hello Raj");
    }
}