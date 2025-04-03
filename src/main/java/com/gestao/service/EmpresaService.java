package com.gestao.service;



import com.gestao.domain.Empresa;

import com.gestao.domain.Fornecedor;
import com.gestao.dto.EmpresaDTO;
import com.gestao.infra.exceptions.FornecedorNaoEncontradoException;
import com.gestao.infra.exceptions.RecursoNaoEncontradoException;
import com.gestao.infra.exceptions.RegraNegocioException;
import com.gestao.repository.EmpresaRepository;


import com.gestao.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpresaService {

  @Autowired
  private EmpresaRepository empresaRepository;
@Autowired
private FornecedorRepository fornecedorRepository;


  public Empresa salvar(EmpresaDTO dto ) {
 List<Fornecedor> fornecedores = fornecedorRepository.findAllById(dto.getFornecedorIds());
//    // Valida se todos os fornecedores foram encontrados
      if (fornecedores.size() != dto.getFornecedorIds().size()) {
          List<Long> idsNaoEncontrados = dto.getFornecedorIds().stream()
                  .filter(id -> fornecedores.stream().noneMatch(f -> f.getId().equals(id)))
                  .collect(Collectors.toList());

          throw new FornecedorNaoEncontradoException("Erro: Os seguintes fornecedores não foram encontrados: " + idsNaoEncontrados);
      }
        String cnpj = dto.getCnpj().replaceAll("[^0-9]", ""); // Remove caracteres não numéricos
    if (cnpj.length() < 14) {
      throw new RegraNegocioException("CNPJ deve ter 14 dígitos!");
    }
    if (empresaRepository.existsByCnpj(dto.getCnpj())) {
      throw new RuntimeException("CNPJ já cadastrado.");
    }
 Empresa empresa= new Empresa();
    empresa.setNomeFantasia(dto.getNomeFantasia());
    empresa.setCnpj(dto.getCnpj());
    empresa.setCep(dto.getCep());
    empresa.getFornecedor().addAll(fornecedores);
    return empresaRepository.save(empresa);
  }
  // Listar todas
public List<Empresa> getAllEmpresa() {
  return empresaRepository.findAll();
}
  // Buscar por ID
  public Empresa buscarPorId(Long id) {
    return empresaRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa não encontrada!"));
  }
  // Atualizar Empresa
  public Empresa atualizarEmpresa(Long empresaId, EmpresaDTO dto) {
      Empresa empresa = empresaRepository.findById(empresaId)
              .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa não encontrada com o ID: " + empresaId));
      // Buscar fornecedores pelo ID
      List<Fornecedor> fornecedores = fornecedorRepository.findAllById(dto.getFornecedorIds());

      // Verificar se todos os fornecedores existem
      if (fornecedores.size() != dto.getFornecedorIds().size()) {
          List<Long> idsNaoEncontrados = dto.getFornecedorIds().stream()
                  .filter(id -> fornecedores.stream().noneMatch(f -> f.getId().equals(id)))
                  .collect(Collectors.toList());

          throw new FornecedorNaoEncontradoException("Erro: Os seguintes fornecedores não foram encontrados: " + idsNaoEncontrados);
      }

      // Atualizar os dados da empresa
    empresa.setCnpj(dto.getCnpj());
    empresa.setNomeFantasia(dto.getNomeFantasia());
    empresa.setCep(dto.getCep());
      empresa.getFornecedor().addAll(fornecedores);
      empresa.setFornecedor(fornecedores);
    return empresaRepository.save(empresa);
  }
  // Deletar Empresa
  public void deletarEmpresa(Long id) {
    Empresa empresa = buscarPorId(id);
    empresaRepository.delete(empresa);
  }



}