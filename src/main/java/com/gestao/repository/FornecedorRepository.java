package com.gestao.repository;

import com.gestao.domain.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor,Long> {
    Optional<Fornecedor> findFornecdorById (Long id);

    boolean existsByCpfCnpj(String cpfCnpj);
}
