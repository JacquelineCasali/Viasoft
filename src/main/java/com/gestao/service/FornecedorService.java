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
import java.util.*;



@Service
@RequiredArgsConstructor
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public Fornecedor salvar(FornecedorDTO dto) {

        if (fornecedorRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado!");
        }
       if (fornecedorRepository.existsByCpfCnpj(dto.getCpfCnpj())) {
            throw new RegraNegocioException("CPF/CNPJ já cadastrado!");
        }
        String cep = dto.getCep().replaceAll("[^0-9]", ""); // Remove caracteres não numéricos
        if (cep.length() < 8) {
            throw new RegraNegocioException("Cep deve ter 8 dígitos!");
        }

        boolean isPessoaFisica = dto.getCpfCnpj().length() == 11;
        // 🔒 Pessoa física deve ter RG e data de nascimento
        if (isPessoaFisica) {
            if (dto.getRg() == null || dto.getDataNascimento() == null) {
                throw new RegraNegocioException("RG e data de nascimento são obrigatórios para pessoa física.");
            }
        }

        List<Empresa> empresas = new ArrayList<>();
        // Agora vincula com empresas (se houver)
        if (dto.getEmpresaIds() != null && !dto.getEmpresaIds().isEmpty()) {
            empresas = empresaRepository.findAllById(dto.getEmpresaIds());
            // 🔒 Validar se todas as empresas existem
            if (empresas.size() != dto.getEmpresaIds().size()) {
                throw new RegraNegocioException("Uma ou mais empresas informadas não existem.");
            }

            // 🔒 Regra para empresas do Paraná com fornecedor menor de idade
            if (isPessoaFisica && dto.getDataNascimento() != null) {
                boolean empresaDoParana = empresas.stream()
                        .anyMatch(e -> "PR".equalsIgnoreCase(e.getEstado())); // campo 'estado' na Empresa

                if (empresaDoParana) {
                    int idade = Period.between(dto.getDataNascimento(), LocalDate.now()).getYears();
                    if (idade < 18) {
                        throw new RegraNegocioException("Fornecedor menor de idade não pode ser vinculado a empresa do Paraná.");
                    }
                }
            }
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.getNome());
        fornecedor.setCpfCnpj(dto.getCpfCnpj());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setRg(dto.getRg());
        fornecedor.setDataNascimento(dto.getDataNascimento());
        fornecedor.setCep(dto.getCep());
        // Salva o fornecedor primeiro para gerar ID
        fornecedor = fornecedorRepository.save(fornecedor);
        // Vínculo com empresas (bidirecional)
        if (!empresas.isEmpty()) {
            fornecedor.setEmpresas(empresas);
            for (Empresa empresa : empresas) {
                empresa.getFornecedores().add(fornecedor);
            }
            empresaRepository.saveAll(empresas);
        }

        return fornecedor;

    }

    // Listar todos e filtrar pelo nome /cnpj
    public List<Fornecedor> filtrar(String nome, String cpfCnpj) {
        return fornecedorRepository.filtrar(nome, cpfCnpj);
    }
    public Fornecedor buscarPorId(Long id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado!"));
    }

    public Fornecedor atualizarFornecedor(Long id, FornecedorDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado com ID: " + id));

        boolean isPessoaFisica = dto.getCpfCnpj().length() == 11;

        if (isPessoaFisica) {
            if (dto.getRg() == null || dto.getDataNascimento() == null) {
                throw new RegraNegocioException("RG e data de nascimento são obrigatórios para pessoa física.");
            }
        }

        // Buscar e validar empresas
        List<Empresa> novasEmpresas = new ArrayList<>();
        if (dto.getEmpresaIds() != null && !dto.getEmpresaIds().isEmpty()) {
            novasEmpresas = empresaRepository.findAllById(dto.getEmpresaIds());

            if (novasEmpresas.size() != dto.getEmpresaIds().size()) {
                throw new RegraNegocioException("Uma ou mais empresas informadas não existem.");
            }

            if (isPessoaFisica && dto.getDataNascimento() != null) {
                boolean empresaDoParana = novasEmpresas.stream()
                        .anyMatch(e -> "PR".equalsIgnoreCase(e.getEstado()));

                if (empresaDoParana) {
                    int idade = Period.between(dto.getDataNascimento(), LocalDate.now()).getYears();
                    if (idade < 18) {
                        throw new RegraNegocioException("Fornecedor menor de idade não pode ser vinculado a empresa do Paraná.");
                    }
                }
            }
        }

       // Remove o fornecedor das empresas antigas
        List<Empresa> empresasAntigas = new ArrayList<>(fornecedor.getEmpresas());
        for (Empresa antiga : empresasAntigas) {
            antiga.getFornecedores().remove(fornecedor);
        }

        // Adiciona o fornecedor às novas empresas
        for (Empresa nova : novasEmpresas) {
            if (!nova.getFornecedores().contains(fornecedor)) {
                nova.getFornecedores().add(fornecedor);
            }
        }

        // Atualiza a lista do próprio fornecedor
        fornecedor.setEmpresas(novasEmpresas);

        // Salva empresas para persistir a relação
        empresaRepository.saveAll(empresasAntigas);
        empresaRepository.saveAll(novasEmpresas);


        fornecedor.setCpfCnpj(dto.getCpfCnpj());
        fornecedor.setNome(dto.getNome());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setCep(dto.getCep());
        fornecedor.setRg(dto.getRg());
        fornecedor.setDataNascimento(dto.getDataNascimento());

        return fornecedorRepository.save(fornecedor);
    }

    // Deletar Fornecedor
    public void deletarFornecedor(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado com ID: " + id));

        // Desvincula o fornecedor das empresas antes de deletar
        for (Empresa empresa : fornecedor.getEmpresas()) {
            empresa.getFornecedores().remove(fornecedor);
        }
        empresaRepository.saveAll(fornecedor.getEmpresas());

        fornecedorRepository.delete(fornecedor);
    }

}