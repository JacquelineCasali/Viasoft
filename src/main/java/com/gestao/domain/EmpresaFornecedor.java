package com.gestao.domain;

import jakarta.persistence.Entity;


import jakarta.persistence.*;

import lombok.*;


@Entity
@Table(name = "empresa_fornecedor")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EmpresaFornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;
}
