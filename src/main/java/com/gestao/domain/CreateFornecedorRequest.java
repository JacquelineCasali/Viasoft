package com.gestao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;



@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateFornecedorRequest {
    private String cpfCnpj;
    private String nome;
     private String email;
     private String cep;
    private String rg;
    private LocalDate dataNascimento;


}