package com.gestao.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;


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
    private String cidade;
    private String rg;

    private LocalDate dataNascimento;

    @ManyToMany(mappedBy = "fornecedores", fetch = FetchType.EAGER
           )
 @JsonIgnoreProperties("fornecedores")

    private Set<Empresa> empresas= new HashSet<>();;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fornecedor that = (Fornecedor) o;
        return id != null && id.equals(that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}