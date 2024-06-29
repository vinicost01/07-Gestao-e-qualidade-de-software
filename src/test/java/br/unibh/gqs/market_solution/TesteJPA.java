package br.unibh.gqs.market_solution;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.unibh.gqs.market_solution.model.CarrinhoCompra;
import br.unibh.gqs.market_solution.model.Cliente;
import br.unibh.gqs.market_solution.model.ItemCompra;
import br.unibh.gqs.market_solution.model.Produto;
import br.unibh.gqs.market_solution.persistence.CarrinhoCompraRepository;
import br.unibh.gqs.market_solution.persistence.ClienteRepository;
import br.unibh.gqs.market_solution.persistence.ItemCompraRepository;
import br.unibh.gqs.market_solution.persistence.ProdutoRepository;

@DataJpaTest(showSql = true)
public class TesteJPA {

    private static Logger LOGGER = LoggerFactory.getLogger(TesteJPA.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemCompraRepository itemCompraRepository;

    @Autowired
    private CarrinhoCompraRepository carrinhoCompraRepository;

  @Test
  public void test01() {

    Cliente cliente = new Cliente("44444444444", "Josué da Silva", "Prata"); 
    Cliente clienteSalvo = clienteRepository.save(cliente);
    assertNotNull(clienteSalvo.getId());

    List<Cliente> list= this.clienteRepository.findAll();
    for (Cliente c : list) {
      LOGGER.info(c.toString());
    }

    Optional<Cliente> ret = clienteRepository.findById(clienteSalvo.getId());
    assertTrue(ret.isPresent());
    if (ret.isPresent()){
        assertEquals(ret.get().getId(), clienteSalvo.getId());
    }
  }

  @Test
  public void test02() {

    Produto produto = new Produto("Shampoo XPTO", 
        BigDecimal.valueOf(12.99), 
        new GregorianCalendar(2024, 7, 30).getTime());
    Produto produtoSalvo = produtoRepository.save(produto);

    List<Produto> list = this.produtoRepository.findAll();
    for (Produto p : list) {
      LOGGER.info(p.toString());
    }

    Produto prod = produtoRepository.findById(produtoSalvo.getId()).orElseThrow();
    assertEquals("Shampoo XPTO", prod.getDescricao());
    assertEquals(BigDecimal.valueOf(12.99), prod.getPreco());
    assertEquals(new GregorianCalendar(2024, 7, 30).getTime(), 
        prod.getDtValidade());

  }

  @Test
  public void test03() {

    List<Produto> list = produtoRepository.findByDescricaoStartsWith("Enxaguante");
    for (Produto p : list) {
      LOGGER.info(p.toString());
    }
    assertEquals(1, list.size());

  }

  @Test
  public void test04() {

    Produto p = produtoRepository.findByDescricaoStartsWith("Enxaguante").get(0);
    LOGGER.info(p.toString());
    ItemCompra i = new ItemCompra(p, 2);
    CarrinhoCompra c = new CarrinhoCompra();
    c.addItemCarrinho(i);
    assertEquals(c.getTotalComDesconto().doubleValue(),59.98);

  }

  @Test
  public void test05() {

      Produto p = produtoRepository.findByDescricaoStartsWith("Enxaguante").get(0);
      LOGGER.info(p.toString());
      ItemCompra i = new ItemCompra(p, 2);
      ItemCompra iSalvo = this.itemCompraRepository.save(i);
      assertNotNull(iSalvo.getId());

      Cliente cliente = clienteRepository.findById(1L).orElseThrow(); 

      CarrinhoCompra c = new CarrinhoCompra();
      c.setCliente(cliente);
      c.addItemCarrinho(iSalvo);

      CarrinhoCompra cSalvo = this.carrinhoCompraRepository.save(c);
      assertNotNull(cSalvo.getId());
      LOGGER.info(c.toString());
  }


  @Test
public void test06() {
    Produto p = produtoRepository.findByDescricaoStartsWith("Enxaguante").get(0);
    LOGGER.info(p.toString());
    ItemCompra i = new ItemCompra(p, 2);
    Produto p2 = produtoRepository.findByDescricaoStartsWith("Creme").get(0);
    LOGGER.info(p2.toString());
    ItemCompra i2 = new ItemCompra(p2, 3);

    Cliente cliente = clienteRepository.findById(1L).orElseThrow();

    CarrinhoCompra c = new CarrinhoCompra();
    c.setCliente(cliente);
    c.addItemCarrinho(i);
    c.addItemCarrinho(i2);
    c.calcula(); // Adiciona esta linha para recalcular total e descontos

    CarrinhoCompra cSalvo = this.carrinhoCompraRepository.save(c);
    assertNotNull(cSalvo.getId());
    LOGGER.info(c.toString());
    assertEquals(2, cSalvo.getItens().size());

    BigDecimal totalComDescontoEsperado = new BigDecimal("260.4256"); // Atualizado para o valor correto após revisão
    assertEquals(totalComDescontoEsperado, cSalvo.getTotalComDesconto());
}

    @Test
  public void test07() {

    Cliente cliente = clienteRepository.findById(1L).orElseThrow(); 
    Produto produto1 = produtoRepository.findById(1L).orElseThrow(); 
    Produto produto2 = produtoRepository.findByDescricaoStartsWith("Enxaguante").get(0); 
    LOGGER.info(cliente.toString());
    LOGGER.info(produto1.toString());
    LOGGER.info(produto2.toString());
    ItemCompra item1 = new ItemCompra(produto1, 1);
    ItemCompra item2 = new ItemCompra(produto2, 2);
    CarrinhoCompra carrinho = new CarrinhoCompra();
    carrinho.setCliente(cliente);
    carrinho.addItemCarrinho(item1);
    carrinho.addItemCarrinho(item2);
    CarrinhoCompra carrinhoSalvo = carrinhoCompraRepository.save(carrinho);
    assertNotNull(carrinhoSalvo.getId());
    assertNotNull(carrinhoSalvo.getCliente());
    assertEquals(cliente.getId(), carrinhoSalvo.getCliente().getId());
    assertEquals("Joana", carrinhoSalvo.getCliente().getNome());
  }
  
  @Test
  public void test08() {
      assertThrows(DataIntegrityViolationException.class, () -> {
          Produto produto = new Produto("Produto Sem Validade", 
              BigDecimal.valueOf(9.99), 
              null);
          produtoRepository.save(produto);
      });
  }
  
  @Test
  public void test09() {
      
      List<Cliente> clientes = clienteRepository.findByNomeContains("Jo");
      for (Cliente cliente : clientes) {
          LOGGER.info(cliente.toString());
      }

      assertEquals(1, clientes.size());
  }



}