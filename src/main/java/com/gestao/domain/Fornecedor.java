package com.gestao.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "fornecedor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(unique = true, nullable = false)
    @Length(min=11 , message = "O documento deve conter 11 caracteres")

    private String cpfCnpj;

    @Column(nullable = false)
    private String nome;
    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String cep;

    private String rg;
    private LocalDate dataNascimento;

@ManyToMany(mappedBy="fornecedor")
@JsonIgnoreProperties("fornecedor")
    private List<Empresa> empresa;

}