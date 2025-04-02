package com.gestao.repository;

import com.gestao.domain.empresa.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa,Long>{
    //    Optional para validação
//    Optional<User> findByCpfCnpjOrEmail (String cpfCnpj,String email);
    Optional<Empresa> findUserById (Long id);
}
