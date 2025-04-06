package com.gestao.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "CPF/CNPJ é obrigatório")
    private String cpfCnpj;

    @NotBlank(message = "Nome  é obrigatório")
    private String nome;
    @Column(unique = true)
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    @NotBlank(message = "CEP é obrigatório")
    private String cep;
    private String estado;

    private String rg;

    private LocalDate dataNascimento;



    @ManyToMany(mappedBy="fornecedores")
    @JsonIgnoreProperties("fornecedores")
    private List<Empresa> empresas=new ArrayList<>();


    @AssertTrue(message = "RG e data de nascimento são obrigatórios para pessoa física (CPF)")
    public boolean isDadosPessoaFisicaValidos() {
        if (cpfCnpj == null) return true;
        String somenteNumeros = cpfCnpj.replaceAll("[^0-9]", "");
        boolean isPessoaFisica = somenteNumeros.length() == 11;

        if (!isPessoaFisica) return true;

        boolean rgValido = rg != null && !rg.trim().isEmpty();
        boolean dataNascimentoValida = dataNascimento != null;

        return rgValido && dataNascimentoValida;
    }



}