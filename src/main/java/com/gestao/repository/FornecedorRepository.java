package com.gestao.repository;

import com.gestao.domain.empresa.Empresa;
import com.gestao.domain.fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor,Long> {
    Optional<Fornecedor> findFornecdorById (Long id);
}
