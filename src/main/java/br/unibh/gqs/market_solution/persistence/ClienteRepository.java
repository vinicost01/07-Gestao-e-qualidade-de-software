package br.unibh.gqs.market_solution.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import br.unibh.gqs.market_solution.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    List<Cliente> findByNomeContains(String nome);
}
