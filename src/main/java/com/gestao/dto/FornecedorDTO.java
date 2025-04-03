package com.gestao.dto;



import com.gestao.domain.Empresa;

import com.gestao.domain.Fornecedor;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data

public class FornecedorDTO {
    private String cpfCnpj;
    private String nome;
    private String email;
    private String cep;
    private boolean pessoaFisica; // Define se é PF ou PJ
    private String rg;
    private LocalDate dataNascimento;

    private List<Long> empresaId;

}