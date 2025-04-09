package com.gestao.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(unique = true, nullable = false,length = 14)

    private String cnpj;

    @Column(nullable = false)
   private String nomeFantasia;

    @Column(nullable = false,length = 9)
    private String cep;
    private String estado;
    private String cidade;

    @ManyToMany(fetch = FetchType.EAGER)
      @JoinTable(
            name = "empresa_fornecedor",
            joinColumns = @JoinColumn(name = "empresa_id"),
            inverseJoinColumns = @JoinColumn(name = "fornecedor_id")
    )

@JsonIgnoreProperties("empresas")
private Set<Fornecedor> fornecedores =new HashSet<>();


}
