package com.gestao.domain.fornecedor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Entity(name="fornecedor")
@Table(name = "fornecedor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Schema(example ="pedro souza" )
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;

    @NotEmpty(message = "Email não pode ser vazio")
    @Email(message = "O campo deve conter um email valido")
    @Column(unique = true)
    @Schema(example ="pedro@gmail.com" )
    private String email;


    @Column(unique = true)
    @Length(min=11 , message = "O documento deve conter 11 caracteres")
    @Schema(example ="456.456.456-44", minLength = 11 )
    private String cnpjcpf;


    @Column(nullable = false)
    private String cep;

    private String rg;
    private LocalDate dataNascimento;
}