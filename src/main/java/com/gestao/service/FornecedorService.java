package com.gestao.service;


import com.gestao.domain.Fornecedor;
import com.gestao.infra.exceptions.RecursoNaoEncontradoException;
import com.gestao.infra.exceptions.RegraNegocioException;
import com.gestao.repository.FornecedorRepository;
import com.gestao.utils.DocumentoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public Fornecedor criarFornecedor(Fornecedor fornecedor) {
       if (fornecedorRepository.existsByEmail(fornecedor.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado!");
        }
        if (!DocumentoUtils.isCnpjOuCpfValido(fornecedor.getCpfCnpj())) {
            throw new RegraNegocioException("CPF/CNPJ inválido!");
        }

        if (fornecedorRepository.existsByCpfCnpj(fornecedor.getCpfCnpj())) {
            throw new RegraNegocioException("CPF/CNPJ já cadastrado!");
        }
        // Se for pessoa física, verificar idade se a empresa for do Paraná
        // Validação de menor de idade no Paraná
//        if (fornecedor.getDataNascimento() != null && isMenorDeIdade(fornecedor)) {
//            throw new RegraNegocioException("Menores de idade não podem ser fornecedores no PR!");
//        }
        return fornecedorRepository.save(fornecedor);
    }
//    private boolean isMenorDeIdade(Fornecedor fornecedor) {
//        LocalDate hoje = LocalDate.now();
//        return fornecedor.getDataNascimento().plusYears(18).isAfter(hoje);
//    }
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
        fornecedor.setCpfCnpj(novoFornecedor.getCpfCnpj());
        fornecedor.setNome(novoFornecedor.getNome());
        fornecedor.setEmail(novoFornecedor.getEmail());
        fornecedor.setCep(novoFornecedor.getCep());
        fornecedor.setRg(novoFornecedor.getRg());
        fornecedor.setDataNascimento(novoFornecedor.getDataNascimento());
        return fornecedorRepository.save(fornecedor);
    }
    // Deletar Fornecedor
    public void deletarFornecedor(Long id) {
        Fornecedor fornecedor = buscarPorId(id);
        fornecedorRepository.delete(fornecedor);
    }

}