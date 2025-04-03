package com.gestao.dto;



import com.gestao.domain.Empresa;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;



@Data

public class FornecedorDTO {
    private String cpfCnpj;
    private String nome;
    private String email;
    private String cep;
    private String rg;
    private LocalDate dataNascimento;
    private List<Long> empresaId;

}