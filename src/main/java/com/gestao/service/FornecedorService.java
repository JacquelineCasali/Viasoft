package com.gestao.service;

import com.gestao.domain.Empresa;
import com.gestao.domain.Fornecedor;
import com.gestao.dto.FornecedorDTO;
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

    public Fornecedor criarFornecedor(FornecedorDTO dto) {
        // Verifica se a empresa existe
        List<Empresa> empresas = empresaRepository.findAllById(dto.getEmpresaId());

       if (fornecedorRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado!");
        }
        String documento = dto.getCpfCnpj().replaceAll("[^0-9]", ""); // Remove caracteres não numéricos
        if (documento.length() < 11 || documento.length() > 14) {
            throw new RegraNegocioException("O documento deve ter entre 11 (CPF) e 14 (CNPJ) dígitos!");
        }
        if (fornecedorRepository.existsByCpfCnpj(dto.getCpfCnpj())) {
            throw new RegraNegocioException("CPF/CNPJ já cadastrado!");
        }
        // Se for pessoa física, verificar idade se a empresa for do Paraná
        if (dto.getCpfCnpj().length() == 11 && dto.getDataNascimento() != null) {
            int idade = Period.between(dto.getDataNascimento(), LocalDate.now()).getYears();
            if (idade < 18) {
                throw new RuntimeException("Fornecedor menor de idade não permitido no Paraná.");
            }
        }


        Fornecedor fornecedor = new Fornecedor();
fornecedor.setNome(dto.getNome());
fornecedor.setCpfCnpj(dto.getCpfCnpj());
fornecedor.setEmail(dto.getEmail());
fornecedor.setRg(dto.getRg());
fornecedor.setDataNascimento(dto.getDataNascimento());
fornecedor.setCep(dto.getCep());
        fornecedor.setEmpresa(empresas);
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
    public Fornecedor atualizarFornecedor(Long fornecedorId, FornecedorDTO dto) {
        // Buscar fornecedor pelo ID
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado com ID: " + fornecedorId));

        // Buscar a nova empresa (se fornecida)
        List<Empresa> empresas = empresaRepository.findAllById(dto.getEmpresaId());

        fornecedor.setCpfCnpj(dto.getCpfCnpj());
        fornecedor.setNome(dto.getNome());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setCep(dto.getCep());
        fornecedor.setRg(dto.getRg());
        fornecedor.setDataNascimento(dto.getDataNascimento());
      fornecedor.setEmpresa(empresas);

        return fornecedorRepository.save(fornecedor);
    }




    // Deletar Fornecedor
    public void deletarFornecedor(Long id) {
        Fornecedor fornecedor = buscarPorId(id);
        fornecedorRepository.delete(fornecedor);
    }

}