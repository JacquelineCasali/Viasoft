package com.gestao.repository;

import com.gestao.domain.Fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;



public interface FornecedorRepository extends JpaRepository<Fornecedor,Long> {


    boolean existsByCpfCnpj(String cpfCnpj);


    boolean existsByEmail(String email);




}
