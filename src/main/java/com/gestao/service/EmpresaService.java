package com.gestao.service;


import com.gestao.domain.Empresa;
import com.gestao.domain.Fornecedor;
import com.gestao.infra.exceptions.RecursoNaoEncontradoException;
import com.gestao.infra.exceptions.RegraNegocioException;
import com.gestao.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {

  @Autowired
  private EmpresaRepository empresaRepository;


    public Empresa criarEmpresa(Empresa empresa){

      if (empresaRepository.existsByCnpj(empresa.getCnpj())) {
        throw new RegraNegocioException("CNPJ já cadastrado!");
      }
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
  public Empresa atualizarEmpresa(Long id, Empresa novaEmpresa) {
    Empresa empresa = buscarPorId(id);
    empresa.setNomefantasia(novaEmpresa.getNomefantasia());
    empresa.setCep(novaEmpresa.getCep());
    return empresaRepository.save(empresa);
  }
  // Deletar Empresa
  public void deletarEmpresa(Long id) {
    Empresa empresa = buscarPorId(id);
    empresaRepository.delete(empresa);
  }
}
