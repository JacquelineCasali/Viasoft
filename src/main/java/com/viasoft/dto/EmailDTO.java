package com.viasoft.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailDTO {
    @NotBlank(message = "Preencha Email do destinatário campo obrigatório ")
    @Email(message = "Email inválido")
    private String emailDestinatario;
    @NotBlank(message = "Preencha o Nome do destinatário campo obrigatório")
    private String nomeDestinatario;
    @NotBlank(message = "Preencha Email do Remetente campo obrigatório")
    @Email(message = "Email inválido")
    private String emailRemetente;
    @NotBlank(message = "Preencha o Assunto do Email campo obrigatório")
    private String assunto;
    @NotBlank(message = "Preencha o conteudo do Email campo obrigatório")
    private String conteudo;


}
