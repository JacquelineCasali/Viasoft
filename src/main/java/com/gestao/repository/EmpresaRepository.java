package com.gestao.repository;

import com.gestao.domain.Empresa;
import com.gestao.domain.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;


public interface EmpresaRepository extends JpaRepository<Empresa,Long>{


  boolean existsByCnpj(String cnpj);


  @Query("SELECT e FROM Empresa e WHERE " +
          "(:nomeFantasia IS NULL OR LOWER(e.nomeFantasia) LIKE LOWER(CONCAT('%', :nomeFantasia, '%'))) AND " +
          "(:cnpj IS NULL OR e.cnpj LIKE %:cnpj%)")
  List<Empresa> filtrar(@Param("nomeFantasia") String nomeFantasia, @Param("cnpj") String cnpj);


}
