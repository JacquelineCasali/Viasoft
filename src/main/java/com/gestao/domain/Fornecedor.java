package com.gestao.domain;

import com.gestao.domain.EmpresaFornecedor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fornecedores")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(unique = true, nullable = false)
    private String cpfCnpj;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    @Email
    private String email;




    @Column(nullable = false)
    private String cep;

    private String rg;
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmpresaFornecedor> empresas = new ArrayList<>();
}