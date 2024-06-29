package br.unibh.gqs.market_solution;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.unibh.gqs.market_solution.model.Cliente;
import br.unibh.gqs.market_solution.model.CarrinhoCompra;
import br.unibh.gqs.market_solution.model.ItemCompra;
import br.unibh.gqs.market_solution.model.Produto;
import br.unibh.gqs.market_solution.rest.ClienteController;
import br.unibh.gqs.market_solution.rest.CarrinhoCompraController;
import br.unibh.gqs.market_solution.service.ClienteService;
import br.unibh.gqs.market_solution.service.CarrinhoCompraService;
import br.unibh.gqs.market_solution.service.ProdutoService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(controllers = {ClienteController.class, CarrinhoCompraController.class})
public class TesteController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClienteService clienteService;

    @MockBean
    CarrinhoCompraService carrinhoCompraService;

    @MockBean
    ProdutoService produtoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void teste01() throws Exception {
        Cliente cliente = new Cliente("777777777777", "Telma", "Ouro");
        given(clienteService.getById(1L)).willReturn(cliente);

        this.mockMvc.perform(get("/cliente/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Telma"));
    }

    @Test
    public void teste02() throws Exception {
        Cliente c1 = new Cliente("11111111111", "Adriano", "Prata");
        Cliente c2 = new Cliente("777777777777", "Telma", "Ouro");
        List<Cliente> clienteList = new ArrayList<>();
        clienteList.add(c1);
        clienteList.add(c2);
        given(clienteService.getAll()).willReturn(clienteList);

        this.mockMvc.perform(get("/clientes")).andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[0].cpf").value("11111111111"))
                .andExpect(jsonPath("$[1].cpf").value("777777777777"));
    }

    @Test
    public void teste03() throws Exception {
        Cliente cliente = new Cliente("11111111111", "Joana", "Bronze");
        Set<ItemCompra> itens = new HashSet<>();
        CarrinhoCompra carrinhoCompra = new CarrinhoCompra(cliente, itens, BigDecimal.ZERO, BigDecimal.ZERO);
        given(carrinhoCompraService.getById(1L)).willReturn(carrinhoCompra);

        this.mockMvc.perform(get("/carrinhocompra/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cliente.nome").value("Joana"));
    }

    @Test
    public void teste04() throws Exception {
        Cliente cliente = new Cliente("22222222222", "Carlos", "Prata");
        Set<ItemCompra> itens = new HashSet<>();
        CarrinhoCompra carrinhoCompra = new CarrinhoCompra(cliente, itens, BigDecimal.ZERO, BigDecimal.ZERO);
        given(carrinhoCompraService.save(carrinhoCompra)).willReturn(carrinhoCompra);

        this.mockMvc.perform(post("/carrinhocompra")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(carrinhoCompra)))
            .andExpect(status().isOk());
    }

    @Test
    public void teste05() throws Exception {
        Cliente cliente = new Cliente("33333333333", "Marcos", "Ouro");
        Set<ItemCompra> itens = new HashSet<>();
        CarrinhoCompra carrinhoCompra = new CarrinhoCompra(cliente, itens, BigDecimal.ZERO, BigDecimal.ZERO);
        given(carrinhoCompraService.save(carrinhoCompra)).willReturn(carrinhoCompra);

        this.mockMvc.perform(put("/carrinhocompra")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(carrinhoCompra)))
            .andExpect(status().isOk());
    }

    @Test
    public void teste06() throws Exception {
        given(carrinhoCompraService.exists(1L)).willReturn(true);

        this.mockMvc.perform(delete("/carrinhocompra")
            .param("id", "1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.Status").value("CarrinhoCompra deleted successfully"));
    }

    @Test
    public void teste07() throws Exception {
        Cliente cliente1 = new Cliente("11111111111", "Joana", "Bronze");
        Cliente cliente2 = new Cliente("22222222222", "Carlos", "Prata");
        Set<ItemCompra> itens1 = new HashSet<>();
        Set<ItemCompra> itens2 = new HashSet<>();
        CarrinhoCompra carrinhoCompra1 = new CarrinhoCompra(cliente1, itens1, BigDecimal.ZERO, BigDecimal.ZERO);
        CarrinhoCompra carrinhoCompra2 = new CarrinhoCompra(cliente2, itens2, BigDecimal.ZERO, BigDecimal.ZERO);
        List<CarrinhoCompra> carrinhoList = new ArrayList<>();
        carrinhoList.add(carrinhoCompra1);
        carrinhoList.add(carrinhoCompra2);
        given(carrinhoCompraService.getAll()).willReturn(carrinhoList);

        this.mockMvc.perform(get("/carrinhocompras")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].cliente.nome").value("Joana"))
            .andExpect(jsonPath("$[1].cliente.nome").value("Carlos"));
    }

    @Test
    public void teste08() throws Exception {
        
        Cliente cliente = new Cliente("99999999999", "João", "Bronze");
        given(clienteService.getById(1L)).willReturn(cliente);

        
        Produto produto = new Produto("Enxaguante Bucal Sem Álcool Listerine Cool Mint 1L", BigDecimal.valueOf(29.99), new Date());
        produto.setId(1L);
        given(produtoService.getById(1L)).willReturn(produto);

        
        ItemCompra itemCompra = new ItemCompra(produto, 2);

        
        Set<ItemCompra> itens = new HashSet<>();
        CarrinhoCompra carrinhoCompra = new CarrinhoCompra(cliente, itens, BigDecimal.ZERO, BigDecimal.ZERO);
        given(carrinhoCompraService.getById(1L)).willReturn(carrinhoCompra);

        
        this.mockMvc.perform(put("/carrinho/1/item")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(itemCompra)))
            .andExpect(status().isOk());
    }
}
