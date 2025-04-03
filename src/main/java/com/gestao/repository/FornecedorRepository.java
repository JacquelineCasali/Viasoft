package com.gestao.repository;

import com.gestao.domain.Fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface FornecedorRepository extends JpaRepository<Fornecedor,Long> {


    boolean existsByCpfCnpj(String cpfCnpj);


    boolean existsByEmail(String email);



//    @Override
//    List<Fornecedor> findAllById(Iterable<Long> longs);



//    List<Fornecedor> findAllById(List<Fornecedor> fornecedorIds);
}
