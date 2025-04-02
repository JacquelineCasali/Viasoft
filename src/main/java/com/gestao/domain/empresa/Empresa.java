package com.gestao.domain.empresa;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity(name="empresa")
@Table(name = "empresa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Schema(example ="pedro souza" )
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nomefantasia;
       @Column(unique = true)
       @Length(min=11 , message = "O documento deve conter 11 caracteres")
       @Schema(example ="456.456.456-44", minLength = 11 )
    private String cnpj;

    @Column(nullable = false)
    private String cep;
}
