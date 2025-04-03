package com.gestao.dto;

import com.gestao.domain.Fornecedor;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Data
public class EmpresaDTO {
    private String cnpj;
    private String nomeFantasia;
    private String cep;
    // lista de fornecedor associado
    private List<Long> fornecedorIds;

}
