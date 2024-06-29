package br.unibh.gqs.market_solution;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.unibh.gqs.market_solution.model.Cliente;
import br.unibh.gqs.market_solution.model.CarrinhoCompra;
import br.unibh.gqs.market_solution.model.ItemCompra;
import br.unibh.gqs.market_solution.model.Produto;

public class TesteDesconto {

    @Test
    public void testeDescontoClienteOuroAcima50() {
        Cliente cliente = new Cliente("11111111111", "Joana", "Ouro");
        Produto produto = new Produto("Produto Teste", BigDecimal.valueOf(60.00), null);
        ItemCompra itemCompra = new ItemCompra(produto, 1);
        Set<ItemCompra> itens = new HashSet<>();
        itens.add(itemCompra);
        CarrinhoCompra carrinhoCompra = new CarrinhoCompra(cliente, itens, BigDecimal.ZERO, BigDecimal.ZERO);
        carrinhoCompra.calcula();
        assertEquals(new BigDecimal("3.000"), carrinhoCompra.getDesconto());
    }

    @Test
    public void testeDescontoClienteOuroAcima100() {
        Cliente cliente = new Cliente("11111111111", "Joana", "Ouro");
        Produto produto = new Produto("Produto Teste", BigDecimal.valueOf(110.00), null);
        ItemCompra itemCompra = new ItemCompra(produto, 1);
        Set<ItemCompra> itens = new HashSet<>();
        itens.add(itemCompra);
        CarrinhoCompra carrinhoCompra = new CarrinhoCompra(cliente, itens, BigDecimal.ZERO, BigDecimal.ZERO);
        carrinhoCompra.calcula();
        assertEquals(new BigDecimal("8.800"), carrinhoCompra.getDesconto());
    }

    @Test
    public void testeDescontoClientePrataAcima50() {
        Cliente cliente = new Cliente("22222222222", "Carlos", "Prata");
        Produto produto = new Produto("Produto Teste", BigDecimal.valueOf(60.00), null);
        ItemCompra itemCompra = new ItemCompra(produto, 1);
        Set<ItemCompra> itens = new HashSet<>();
        itens.add(itemCompra);
        CarrinhoCompra carrinhoCompra = new CarrinhoCompra(cliente, itens, BigDecimal.ZERO, BigDecimal.ZERO);
        carrinhoCompra.calcula();
        assertEquals(new BigDecimal("1.800"), carrinhoCompra.getDesconto());
    }

    @Test
    public void testeDescontoClientePrataAcima100() {
        Cliente cliente = new Cliente("22222222222", "Carlos", "Prata");
        Produto produto = new Produto("Produto Teste", BigDecimal.valueOf(110.00), null);
        ItemCompra itemCompra = new ItemCompra(produto, 1);
        Set<ItemCompra> itens = new HashSet<>();
        itens.add(itemCompra);
        CarrinhoCompra carrinhoCompra = new CarrinhoCompra(cliente, itens, BigDecimal.ZERO, BigDecimal.ZERO);
        carrinhoCompra.calcula();
        assertEquals(new BigDecimal("5.500"), carrinhoCompra.getDesconto());
    }

    @Test
    public void testeDescontoClienteBronzeAcima50() {
        Cliente cliente = new Cliente("33333333333", "Marcos", "Bronze");
        Produto produto = new Produto("Produto Teste", BigDecimal.valueOf(60.00), null);
        ItemCompra itemCompra = new ItemCompra(produto, 1);
        Set<ItemCompra> itens = new HashSet<>();
        itens.add(itemCompra);
        CarrinhoCompra carrinhoCompra = new CarrinhoCompra(cliente, itens, BigDecimal.ZERO, BigDecimal.ZERO);
        carrinhoCompra.calcula();
        assertEquals(BigDecimal.ZERO, carrinhoCompra.getDesconto());
    }

    @Test
    public void testeDescontoClienteBronzeAcima100() {
        Cliente cliente = new Cliente("33333333333", "Marcos", "Bronze");
        Produto produto = new Produto("Produto Teste", BigDecimal.valueOf(110.00), null);
        ItemCompra itemCompra = new ItemCompra(produto, 1);
        Set<ItemCompra> itens = new HashSet<>();
        itens.add(itemCompra);
        CarrinhoCompra carrinhoCompra = new CarrinhoCompra(cliente, itens, BigDecimal.ZERO, BigDecimal.ZERO);
        carrinhoCompra.calcula();
        assertEquals(new BigDecimal("3.300"), carrinhoCompra.getDesconto());
    }
}
