package com.viasoft.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
@AllArgsConstructor
public class EmailOciDTO {

    @NotBlank(message = "Preencha Email do destinatário campo obrigatório ")
    @Email(message = "Email inválido")
    @Length(max = 40, message = "O Email destinatário deve conter no máximo 40 caracteres")
    private String recipientEmail;

    @NotBlank(message = "Preencha o Nome do destinatário campo obrigatório")
    @Length(max = 50, message = "O Nome destinatário deve conter no máximo 50 caracteres")
    private String recipientName;

    @NotBlank(message = "Preencha Email do Remetente campo obrigatório")
    @Email(message = "Email inválido")
    @Length(max = 40, message = "O Email remetente deve conter no máximo 40 caracteres")
    private String senderEmail;
    @NotBlank(message = "Preencha o Assunto do Email campo obrigatório")
    @Length(max = 100, message = "O Assunto do e-mail deve conter no máximo 100 caracteres")
    private String subject;

    @NotBlank(message = "Preencha o conteudo do Email campo obrigatório")
    @Length(max = 250, message = "O Conteúdo do email deve conter no máximo 250 caracteres")
    private String body;


}
