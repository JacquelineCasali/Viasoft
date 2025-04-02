package com.gestao.service;



import com.gestao.domain.Fornecedor;
import com.gestao.infra.exceptions.RecursoNaoEncontradoException;
import com.gestao.infra.exceptions.RegraNegocioException;
import com.gestao.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorService {

  @Autowired
  private FornecedorRepository fornecedorRepository;

  public Fornecedor criarFornecedor(Fornecedor fornecedor) {
    if (fornecedorRepository.existsByCpfCnpj(fornecedor.getCpfCnpj())) {
      throw new RegraNegocioException("CPF/CNPJ já cadastrado!");
    }
    return fornecedorRepository.save(fornecedor);
  }

  // Listar todos
  public List<Fornecedor> getAllFornecedor() {
    return fornecedorRepository.findAll();
  }
  public Fornecedor buscarPorId(Long id) {
    return fornecedorRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado!"));
  }
  // Atualizar Fornecedor
  public Fornecedor atualizarFornecedor(Long id, Fornecedor novoFornecedor) {
    Fornecedor fornecedor = buscarPorId(id);
    fornecedor.setNome(novoFornecedor.getNome());
    fornecedor.setEmail(novoFornecedor.getEmail());
    fornecedor.setCep(novoFornecedor.getCep());
    return fornecedorRepository.save(fornecedor);
  }
  // Deletar Fornecedor
  public void deletarFornecedor(Long id) {
    Fornecedor fornecedor = buscarPorId(id);
    fornecedorRepository.delete(fornecedor);
  }

}
