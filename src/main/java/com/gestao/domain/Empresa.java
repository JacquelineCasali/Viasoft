package com.gestao.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Length;


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



//        @ManyToMany>
//    @JoinTable(
//            name = "empresa_fornecedor",
//            joinColumns = @JoinColumn(name = "empresa_id"),
//            inverseJoinColumns = @JoinColumn(name = "fornecedor_id")
//    )
//    Set<Fornecedor>  fornecedors;
//    private Set<Fornecedor> fornecedores = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "empresa_fornecedor",
            joinColumns = @JoinColumn(name = "empresa_id"),
            inverseJoinColumns = @JoinColumn(name = "fornecedor_id")
    )
    private Set<Fornecedor> fornecedores ;

}
