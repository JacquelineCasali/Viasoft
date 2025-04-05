package com.gestao.repository;

import com.gestao.domain.Fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FornecedorRepository extends JpaRepository<Fornecedor,Long> {


    boolean existsByEmail(String email);

    boolean existsByCpfCnpj(String cpfCnpj);
}
