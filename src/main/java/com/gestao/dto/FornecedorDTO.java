package com.gestao.dto;


import com.gestao.domain.Empresa;
import com.gestao.domain.Fornecedor;
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
    private String cpfCnpj;
    private String nome;
    private String email;
    private String cep;
    private String rg;
    private LocalDate dataNascimento;
private List<Long> empresaIds;

}