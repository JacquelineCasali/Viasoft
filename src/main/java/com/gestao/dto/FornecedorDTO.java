package com.gestao.dto;



import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class FornecedorDTO {
    @NotBlank(message = "CPF/CNPJ é obrigatório")
    private String cpfCnpj;
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    @NotBlank(message = "CEP é obrigatório")
    private String cep;
    private String estado;
    private String rg;
    private LocalDate dataNascimento;
private List<Long> empresaIds;

    public boolean isPessoaFisica() {
        if (cpfCnpj == null) return false;
        String onlyDigits = cpfCnpj.replaceAll("[^0-9]", "");
        return onlyDigits.length() == 11;
    }

    @AssertTrue(message = "RG e data de nascimento são obrigatórios para pessoa física (CPF)")
    public boolean isDadosPessoaFisicaValidos() {
        if (!isPessoaFisica()) return true;
        return rg != null && !rg.trim().isEmpty() && dataNascimento != null;
    }

}