package com.gestao.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false, unique = true)

    private String cpfCnpj;

    @Column(nullable = false)
    private String nome;
    @Column(unique = true, nullable = false)
    @Email
    private String email;
    @Column(nullable = false, length = 9)
    private String cep;
    private String rg;

    private LocalDate dataNascimento;

    @ManyToMany(mappedBy="fornecedores")
    @JsonIgnoreProperties("fornecedores")
    private List<Empresa> empresas=new ArrayList<>();

}