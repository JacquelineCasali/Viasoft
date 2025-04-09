package com.gestao.service;

import com.gestao.domain.CepResponse;
import com.gestao.domain.Empresa;
import com.gestao.domain.Fornecedor;
import com.gestao.dto.FornecedorDTO;
import com.gestao.infra.exceptions.MultiplasRegrasException;
import com.gestao.infra.exceptions.RecursoNaoEncontradoException;
import com.gestao.infra.exceptions.RegraNegocioException;
import com.gestao.repository.EmpresaRepository;
import com.gestao.repository.FornecedorRepository;
import com.gestao.utils.CepUtils;
import com.gestao.utils.FornecedorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

        List<String> erros = new ArrayList<>();
        if (fornecedorRepository.existsByEmail(dto.getEmail())) {
            erros.add("Email já cadastrado!");
        }
        if (fornecedorRepository.existsByCpfCnpj(dto.getCpfCnpj())) {
            erros.add("CPF/CNPJ já cadastrado!");
        }
        String cep = dto.getCep().replaceAll("[^0-9]", ""); // Remove caracteres não numéricos
        if (cep.length() < 8) {
            erros.add("CEP deve ter 8 dígitos!");
        }
        String cpfCnpj = dto.getCpfCnpj().replaceAll("[^0-9]", "");
        if (cpfCnpj.length() != 11 && cpfCnpj.length() != 14) {
            erros.add("CPF ou CNPJ inválido.");
        }
        if (!erros.isEmpty()) {
            throw new MultiplasRegrasException(erros);
        }
        // Buscar estado com base no CEP
        CepResponse cepInfo = CepUtils.buscarUfPorCep(cep);
        String estado = cepInfo.getUf();
        String cidade = cepInfo.getLocalidade();


        List<Empresa> empresas = empresaRepository.findAllById(dto.getEmpresaIds());
        if (empresas.size() != dto.getEmpresaIds().size()) {
            throw new RegraNegocioException("Uma ou mais empresas informadas não existem.");
        }
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.getNome());
        fornecedor.setCpfCnpj(dto.getCpfCnpj());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setRg(dto.getRg());
        fornecedor.setDataNascimento(dto.getDataNascimento());
        fornecedor.setCep(cep);
        fornecedor.setEstado(estado);
        fornecedor.setCidade(cidade);
        FornecedorValidator.validarFornecedorMenorDeIdadeComEmpresaPR(empresas, fornecedor);

        // Salva o fornecedor primeiro para gerar ID
        fornecedor = fornecedorRepository.save(fornecedor);
        // Faz a associação sem duplicar
        for (Empresa empresa : empresas) {
            if (!empresa.getFornecedores().contains(fornecedor)) {
                empresa.getFornecedores().add(fornecedor);
            }
        }

        fornecedor.setEmpresas(new HashSet<>(empresas));

        empresaRepository.saveAll(empresas); // Atualiza a outra ponta do relacionamento

        return fornecedorRepository.save(fornecedor); // Salva com os vínculos atualizados

    }

    // Listar todos e filtrar pelo nome /cnpj
    public List<Fornecedor> filtrar(String nome, String cpfCnpj) {
        return fornecedorRepository.filtrar(nome, cpfCnpj);
    }
    public Fornecedor buscarPorId(Long id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado!"));
    }

    public Fornecedor atualizarFornecedor(Long fornecedorId, FornecedorDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado com ID: " + fornecedorId));
        List<String> erros = new ArrayList<>();
        // Verifica se está tentando alterar o CPF/CNPJ para um que já existe
        if (!fornecedor.getCpfCnpj().equals(dto.getCpfCnpj()) &&
                fornecedorRepository.existsByCpfCnpj(dto.getCpfCnpj())) {
           erros.add("CPF/CNPJ já cadastrado!");
        }
        if (!fornecedor.getEmail().equals(dto.getEmail()) &&
                fornecedorRepository.existsByEmail(dto.getEmail())) {
           erros.add("Email já cadastrado!");
        }

        String cep = dto.getCep().replaceAll("[^0-9]", "");
        if (cep.length() < 8) {
            erros.add("CEP deve ter 8 dígitos!");
        }

        String cpfCnpj = dto.getCpfCnpj().replaceAll("[^0-9]", "");
        if (cpfCnpj.length() != 11 && cpfCnpj.length() != 14) {
           erros.add("CPF ou CNPJ inválido.");
        }
        if (!erros.isEmpty()) {
            throw new MultiplasRegrasException(erros);
        }
// Buscar cidade e estado com base no CEP
        CepResponse cepInfo = CepUtils.buscarUfPorCep(cep);
        String estado = cepInfo.getUf();
        String cidade = cepInfo.getLocalidade();

        List<Empresa> novasEmpresas   = empresaRepository.findAllById(dto.getEmpresaIds());
        if (novasEmpresas.size() != dto.getEmpresaIds().size()) {
            throw new RegraNegocioException("Uma ou mais empresas informadas não existem.");
        }


// Cria um fornecedor temporário para validar a regra de menor de idade
        Fornecedor fornecedorTemp = new Fornecedor();
        fornecedorTemp.setCpfCnpj(dto.getCpfCnpj());
        fornecedorTemp.setDataNascimento(dto.getDataNascimento());

        FornecedorValidator.validarFornecedorMenorDeIdadeComEmpresaPR(novasEmpresas , fornecedorTemp);
        fornecedor.setCpfCnpj(dto.getCpfCnpj());
        fornecedor.setNome(dto.getNome());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setRg(dto.getRg());
        fornecedor.setDataNascimento(dto.getDataNascimento());
        fornecedor.setCep(cep);
        fornecedor.setEstado(estado);
        fornecedor.setCidade(cidade);
        // Verifica se houve alteração nos vínculos com empresas
        // Atualiza vínculos de empresas
        Set<Empresa> antigasEmpresas = new HashSet<>(fornecedor.getEmpresas());

        // Remover as que não estão mais presentes
        for (Empresa antiga : antigasEmpresas) {
            if (!novasEmpresas.contains(antiga)) {
                antiga.getFornecedores().remove(fornecedor);
            }
        }

        // Adicionar novas empresas, se ainda não estiverem vinculadas
        for (Empresa nova : novasEmpresas) {
            if (!nova.getFornecedores().contains(fornecedor)) {
                nova.getFornecedores().add(fornecedor);
            }
        }

        fornecedor.setEmpresas(new HashSet<>(novasEmpresas));

        empresaRepository.saveAll(antigasEmpresas); // salva desvinculações
        empresaRepository.saveAll(novasEmpresas);   // salva novos vínculos

        return fornecedorRepository.save(fornecedor);

    }


    private List<Empresa> buscarEmpresas(List<Long> empresaIds) {
        if (empresaIds == null || empresaIds.isEmpty()) return new ArrayList<>();
        List<Empresa> empresas = empresaRepository.findAllById(empresaIds);
        if (empresas.size() != empresaIds.size()) {
            throw new RegraNegocioException("Uma ou mais empresas informadas não existem.");
        }
        return empresas;
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