package com.gestao.service;



import com.gestao.domain.fornecedor.Fornecedor;
import com.gestao.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService {

  @Autowired
  private FornecedorRepository fornecedorRepository;

// verificando se o usuario exite

    public Fornecedor findFornecdorById(Long id) throws Exception {
        return this.fornecedorRepository.findFornecdorById(id).orElseThrow(()->new Exception("Fornecedor não encontrado"));
    }

    public void saveFornecedor(Fornecedor fornecedor){
      this.fornecedorRepository.save(fornecedor);
    }

    // listar todos
    public List<Fornecedor> getAllFornecedor(){
        return this.fornecedorRepository.findAll();
    }


}
