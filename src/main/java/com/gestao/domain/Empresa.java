package com.gestao.domain;


import com.gestao.domain.EmpresaFornecedor;
import jakarta.persistence.*;

import lombok.*;


import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "empresas")
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
   private String nomefantasia;



    @Column(nullable = false)
    private String cep;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmpresaFornecedor> fornecedores = new ArrayList<>();


}
