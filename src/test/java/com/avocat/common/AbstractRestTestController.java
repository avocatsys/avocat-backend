package com.avocat.common;

import com.avocat.util.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

/*
    Essa classe abstrata tem o proposito de oferecer a configuração base para testes
    utilizando o RestTemplate.

    Deve ser usada para testes onde o objetivo é integrar ao máixmo com o bando de dados e
    evitar a utilização de Mocks.

    Essa classe executa uma instância real do Servidor tornando possível testar todas as camadas
    da aplicação principalmente com o banco de dados integrado.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractRestTestController extends TokenUtil {

    @LocalServerPort
    private int port;

    protected String HOST;

    protected HttpHeaders headers = new HttpHeaders();

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @BeforeEach
    public void init(){
        super.init();
        HOST = "http://localhost:" + port;
        headers.set("Authorization", "Bearer " + defaultAccessToken);
    }
}
