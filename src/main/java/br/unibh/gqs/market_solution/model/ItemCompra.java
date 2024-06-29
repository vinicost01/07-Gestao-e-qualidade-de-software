package br.unibh.gqs.market_solution.model;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "TB_ITEM_COMPRA")
public class ItemCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Positive
    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subTotal;

    // Construtor padrão
    public ItemCompra() {
    }

    // Construtor com parâmetros
    public ItemCompra(@NotEmpty Produto produto, @Positive int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.calculaSubTotal();
    }

    public void setProdutoQualidade(@NotEmpty Produto produto, @Positive int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.calculaSubTotal();
    }

    public void calculaSubTotal() {
        this.subTotal = this.produto.getPreco().multiply(new BigDecimal(this.quantidade));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCompra that = (ItemCompra) o;
        return quantidade == that.quantidade &&
               Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produto, quantidade);
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
}