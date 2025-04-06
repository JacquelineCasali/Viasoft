package com.gestao.repository;

import com.gestao.domain.Fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FornecedorRepository extends JpaRepository<Fornecedor,Long> {


    boolean existsByEmail(String email);

    boolean existsByCpfCnpj(String cpfCnpj);


//    A listagem de fornecedores deverá conter filtros por Nome e CPF/CNPJ

    @Query("SELECT f FROM Fornecedor f WHERE " +
            "(:nome IS NULL OR LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
            "(:cpfCnpj IS NULL OR f.cpfCnpj LIKE %:cpfCnpj%)")
    List<Fornecedor> filtrar(@Param("nome") String nome, @Param("cpfCnpj") String cpfCnpj);


}
