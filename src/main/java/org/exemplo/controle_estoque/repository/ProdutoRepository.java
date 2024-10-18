package org.exemplo.controle_estoque.repository;

import org.exemplo.controle_estoque.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

