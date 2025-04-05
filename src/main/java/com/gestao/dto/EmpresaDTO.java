package com.gestao.dto;

import com.gestao.domain.Empresa;
import com.gestao.domain.Fornecedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
//O Builder é um padrão de projeto criacional
// que permite a você construir objetos complexos passo a passo.
@Builder
public class EmpresaDTO {
    private String cnpj;
    private String nomeFantasia;
    private String cep;
    private List<Long> fornecedorIds;
    private String estado;

}
