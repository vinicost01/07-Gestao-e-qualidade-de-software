package br.unibh.gqs.market_solution;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import br.unibh.gqs.market_solution.model.Cliente;
import br.unibh.gqs.market_solution.model.CarrinhoCompra;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;




@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TesteRest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test01()
    {
        assertTrue(
                this.restTemplate
                        .getForObject("http://localhost:" + port + "/cliente/1", Cliente.class)
                        .getCpf().equals("11111111111"));
    }

    @Test
    public void test02() {
        assertTrue(
                this.restTemplate
                        .getForObject("http://localhost:" + port + "/produtos", List.class).size() == 3);
    }

    @Test
    public void test03() {
        CarrinhoCompra carrinho = this.restTemplate
                .getForObject("http://localhost:" + port + "/carrinhocompra/1", CarrinhoCompra.class);
        assertTrue(carrinho != null);
        assertTrue(carrinho.getItens().size() > 0);
    }

    @Test
    public void test04() {
        Cliente novoCliente = new Cliente();
        novoCliente.setCpf("44444444444");
        novoCliente.setNome("Ana");
        novoCliente.setClasseBonus("Bronze");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Cliente> request = new HttpEntity<>(novoCliente, headers);

        ResponseEntity<Cliente> response = restTemplate.exchange(
            "http://localhost:" + port + "/cliente", 
            HttpMethod.POST, 
            request, 
            Cliente.class
        );

        assertTrue(response.getStatusCode().is2xxSuccessful());
        Cliente clienteCriado = response.getBody();
        assertTrue(clienteCriado != null);
        assertTrue(clienteCriado.getCpf().equals("44444444444"));
        assertTrue(clienteCriado.getNome().equals("Ana"));
        assertTrue(clienteCriado.getClasseBonus().equals("Bronze"));
    }

    @Test
    public void test05() {
        Cliente clienteExistente = this.restTemplate
                .getForObject("http://localhost:" + port + "/cliente/1", Cliente.class);
        assertTrue(clienteExistente != null);

        clienteExistente.setNome("Joana Alterada");
        clienteExistente.setClasseBonus("Ouro");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Cliente> request = new HttpEntity<>(clienteExistente, headers);

        ResponseEntity<Cliente> response = restTemplate.exchange(
            "http://localhost:" + port + "/cliente", // Correção do endpoint
            HttpMethod.PUT, 
            request, 
            Cliente.class
        );

        // Print the status code and response body for debugging
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());

        assertTrue(response.getStatusCode().is2xxSuccessful());
        Cliente clienteAlterado = response.getBody();
        assertTrue(clienteAlterado != null);
        assertTrue(clienteAlterado.getCpf().equals("11111111111"));
        assertTrue(clienteAlterado.getNome().equals("Joana Alterada"));
        assertTrue(clienteAlterado.getClasseBonus().equals("Ouro"));
    }

    @Test
    public void test06() {
        Cliente novoCliente = new Cliente();
        novoCliente.setCpf("55555555555");
        novoCliente.setNome("Maria");
        novoCliente.setClasseBonus("Prata");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Cliente> request = new HttpEntity<>(novoCliente, headers);

        // Adiciona um novo cliente para garantir que ele está presente antes de deletar
        ResponseEntity<Cliente> responsePost = restTemplate.exchange(
            "http://localhost:" + port + "/cliente", 
            HttpMethod.POST, 
            request, 
            Cliente.class
        );

        assertTrue(responsePost.getStatusCode().is2xxSuccessful());
        Cliente clienteCriado = responsePost.getBody();
        assertTrue(clienteCriado != null);
        assertTrue(clienteCriado.getCpf().equals("55555555555"));
        assertTrue(clienteCriado.getNome().equals("Maria"));
        assertTrue(clienteCriado.getClasseBonus().equals("Prata"));

        // Deleta o cliente criado
        ResponseEntity<Void> responseDelete = restTemplate.exchange(
            "http://localhost:" + port + "/cliente?id=" + clienteCriado.getId(),
            HttpMethod.DELETE,
            null,
            Void.class
        );

        assertTrue(responseDelete.getStatusCode().is2xxSuccessful());

    }

}

