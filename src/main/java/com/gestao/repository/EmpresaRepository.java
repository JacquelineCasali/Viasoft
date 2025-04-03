package com.gestao.repository;

import com.gestao.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;


public interface EmpresaRepository extends JpaRepository<Empresa,Long>{

  boolean existsByCnpj(String cnpj);


  List<Empresa> findAllByCep(String cep);
}
