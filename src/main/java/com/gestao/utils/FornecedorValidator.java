package com.gestao.utils;

import com.gestao.infra.exceptions.RegraNegocioException;
import com.gestao.domain.Empresa;
import com.gestao.domain.Fornecedor;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class FornecedorValidator {

    public static void validarFornecedorMenorDeIdadeComEmpresaPR(List<Empresa> empresas, Fornecedor fornecedor) {
        if (fornecedor.getCpfCnpj() == null || fornecedor.getDataNascimento() == null) return;

        String cpfCnpj = fornecedor.getCpfCnpj().replaceAll("[^0-9]", "");
        boolean isPessoaFisica = cpfCnpj.length() == 11;

        if (!isPessoaFisica) return;

        boolean empresaDoParana = empresas.stream()
                .filter(e -> e.getEstado() != null)
                .anyMatch(e -> "PR".equalsIgnoreCase(e.getEstado()));

        if (empresaDoParana) {
            int idade = Period.between(fornecedor.getDataNascimento(), LocalDate.now()).getYears();
            if (idade < 18) {
                throw new RegraNegocioException("Fornecedor menor de idade não pode ser vinculado a empresa do estado do Paraná.");
            }
        }
    }

    public static void validarFornecedorMenorDeIdadeComEmpresaPR(Empresa empresa, List<Fornecedor> fornecedores) {
        if (empresa.getEstado() == null || !"PR".equalsIgnoreCase(empresa.getEstado())) return;

        for (Fornecedor fornecedor : fornecedores) {
            if (fornecedor.getCpfCnpj() == null || fornecedor.getDataNascimento() == null) continue;

            String cpfCnpj = fornecedor.getCpfCnpj().replaceAll("[^0-9]", "");
            boolean isPessoaFisica = cpfCnpj.length() == 11;

            if (isPessoaFisica) {
                int idade = Period.between(fornecedor.getDataNascimento(), LocalDate.now()).getYears();
                if (idade < 18) {
                    throw new RegraNegocioException("Fornecedor menor de idade não pode ser vinculado à empresa do Paraná.");
                }
            }
        }
    }
}