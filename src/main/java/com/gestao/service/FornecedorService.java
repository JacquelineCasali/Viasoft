package com.gestao.service;

import com.gestao.domain.Empresa;
import com.gestao.domain.Fornecedor;
import com.gestao.dto.FornecedorDTO;
import com.gestao.infra.exceptions.RecursoNaoEncontradoException;
import com.gestao.infra.exceptions.RegraNegocioException;
import com.gestao.repository.EmpresaRepository;
import com.gestao.repository.FornecedorRepository;
import com.gestao.utils.CepUtils;
import com.gestao.utils.FornecedorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private EmpresaRepository empresaRepository;
    @Transactional
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
        String cpfCnpj = dto.getCpfCnpj().replaceAll("[^0-9]", "");
        if (cpfCnpj.length() != 11 && cpfCnpj.length() != 14) {
            throw new RegraNegocioException("CPF ou CNPJ inválido.");
        }

        // Buscar estado com base no CEP
        String estado = CepUtils.buscarUfPorCep(cep);
        if (estado == null || estado.isEmpty()) {
            throw new RegraNegocioException("Não foi possível determinar o estado a partir do CEP informado.");
        }


        List<Empresa> empresas = new ArrayList<>();
        // Agora vincula com empresas (se houver)
        if (dto.getEmpresaIds() != null && !dto.getEmpresaIds().isEmpty()) {
            empresas = empresaRepository.findAllById(dto.getEmpresaIds());
            // 🔒 Validar se todas as empresas existem
            if (empresas.size() != dto.getEmpresaIds().size()) {
                throw new RegraNegocioException("Uma ou mais empresas informadas não existem.");
            }
        }
            // Regra para empresas do Paraná com fornecedor menor de idade
            Fornecedor fornecedorTemp = new Fornecedor();
            fornecedorTemp.setCpfCnpj(dto.getCpfCnpj());
            fornecedorTemp.setDataNascimento(dto.getDataNascimento());

            FornecedorValidator.validarFornecedorMenorDeIdadeComEmpresaPR(empresas, fornecedorTemp);



            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setNome(dto.getNome());
            fornecedor.setCpfCnpj(dto.getCpfCnpj());
            fornecedor.setEmail(dto.getEmail());
            fornecedor.setRg(dto.getRg());
            fornecedor.setDataNascimento(dto.getDataNascimento());
            fornecedor.setCep(cep);
            fornecedor.setEstado(estado);


            // Salva o fornecedor primeiro para gerar ID
            fornecedor = fornecedorRepository.save(fornecedor);
            // Vínculo com empresas (bidirecional)
            if (!empresas.isEmpty()) {
                fornecedor.setEmpresas(empresas);
                for (Empresa empresa : empresas) {
                    empresa.getFornecedores().add(fornecedor);
                    System.out.println("Empresa: " + empresa.getNomeFantasia() + " - Estado: " + empresa.getEstado());
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

        // Verifica se está tentando alterar o CPF/CNPJ para um que já existe
        if (!fornecedor.getCpfCnpj().equals(dto.getCpfCnpj()) &&
                fornecedorRepository.existsByCpfCnpj(dto.getCpfCnpj())) {
            throw new RegraNegocioException("CPF/CNPJ já cadastrado!");
        }
        if (!fornecedor.getEmail().equals(dto.getEmail()) &&
                fornecedorRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado!");
        }

        String cep = dto.getCep().replaceAll("[^0-9]", "");
        if (cep.length() < 8) {
            throw new RegraNegocioException("CEP deve ter 8 dígitos!");
        }

        String cpfCnpj = dto.getCpfCnpj().replaceAll("[^0-9]", "");
        if (cpfCnpj.length() != 11 && cpfCnpj.length() != 14) {
            throw new RegraNegocioException("CPF ou CNPJ inválido.");
        }


        // Buscar e validar empresas
        List<Empresa> novasEmpresas = new ArrayList<>();
        if (dto.getEmpresaIds() != null && !dto.getEmpresaIds().isEmpty()) {
            novasEmpresas = empresaRepository.findAllById(dto.getEmpresaIds());

            if (novasEmpresas.size() != dto.getEmpresaIds().size()) {
                throw new RegraNegocioException("Uma ou mais empresas informadas não existem.");
            }
        }
// Cria um fornecedor temporário para validar a regra de menor de idade
        Fornecedor fornecedorTemp = new Fornecedor();
        fornecedorTemp.setCpfCnpj(dto.getCpfCnpj());
        fornecedorTemp.setDataNascimento(dto.getDataNascimento());

        FornecedorValidator.validarFornecedorMenorDeIdadeComEmpresaPR(novasEmpresas, fornecedorTemp);

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
        fornecedor.setRg(dto.getRg());
        fornecedor.setDataNascimento(dto.getDataNascimento());
        fornecedor.setCep(cep);
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