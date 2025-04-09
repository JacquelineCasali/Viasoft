package com.gestao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class EmpresaDTO {
    private  Long id;
    private String cnpj;
    private String nomeFantasia;
    private String cep;
    private List<Long> fornecedorIds;
    private String estado;
    private String cidade;
}