package com.gestao.service;


import com.gestao.domain.Empresa;
import com.gestao.domain.Fornecedor;
import com.gestao.infra.exceptions.RecursoNaoEncontradoException;
import com.gestao.infra.exceptions.RegraNegocioException;
import com.gestao.repository.EmpresaRepository;
import com.gestao.repository.FornecedorRepository;
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

    @Autowired
    private EmpresaRepository empresaRepository;

    public Fornecedor criarFornecedor(Fornecedor fornecedor,  Long empresaId) {
       if (fornecedorRepository.existsByEmail(fornecedor.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado!");
        }
        String documento = fornecedor.getCpfCnpj().replaceAll("[^0-9]", ""); // Remove caracteres não numéricos
        if (documento.length() < 11 || documento.length() > 14) {
            throw new RegraNegocioException("O documento deve ter entre 11 (CPF) e 14 (CNPJ) dígitos!");
        }
        if (fornecedorRepository.existsByCpfCnpj(fornecedor.getCpfCnpj())) {
            throw new RegraNegocioException("CPF/CNPJ já cadastrado!");
        }
        // Se for pessoa física, verificar idade se a empresa for do Paraná
        if (fornecedor.getCpfCnpj().length() == 11 && fornecedor.getDataNascimento() != null) {
            int idade = Period.between(fornecedor.getDataNascimento(), LocalDate.now()).getYears();
            if (idade < 18) {
                throw new RuntimeException("Fornecedor menor de idade não permitido no Paraná.");
            }
        }


        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        fornecedor = fornecedorRepository.save(fornecedor);
empresa.getFornecedor().add(fornecedor);
empresaRepository.save(empresa);
return fornecedor;

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