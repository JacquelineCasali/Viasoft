package com.gestao.service;


import com.gestao.domain.empresa.Empresa;
import com.gestao.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpresaService {

  @Autowired
  private EmpresaRepository empresaRepository;

// verificando se o usuario exite

    public Empresa findUserById(Long id) throws Exception {
        return this.empresaRepository.findUserById(id).orElseThrow(()->new Exception("Empresa não encontrado"));
    }

    public void saveEmpresa(Empresa empresa){
      this.empresaRepository.save(empresa);
    }

    // listar todos
    public List<Empresa> getAllEmpresas(){
        return this.empresaRepository.findAll();
    }


}
