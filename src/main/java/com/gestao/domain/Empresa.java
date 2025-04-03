package com.gestao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Length;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "empresa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(unique = true, nullable = false)

    private String cnpj;

    @Column(nullable = false)
   private String nomeFantasia;

    @Column(nullable = false)
    private String cep;



@ManyToMany
      @JoinTable(
            name = "empresa_fornecedor",
            joinColumns = @JoinColumn(name = "empresa_id"),
            inverseJoinColumns = @JoinColumn(name = "fornecedor_id")
    )

//@JsonIgnoreProperties("empresa")
//    private List<Fornecedor> fornecedor;




    @JsonIgnoreProperties("empresa")
//private Set<Fornecedor> fornecedor = new HashSet<>();
   private List<Fornecedor> fornecedor;
//    public void addFornecedor(final Fornecedor fornecedores) {
//        this.fornecedor.add(fornecedores);
//    }

}
