package com.viasoft.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {
    @NotBlank(message = "Preencha Email do destinatário")
    @Email(message = "Email inválido")
    private String emailDestinatario;
    @NotBlank(message = "Preencha o Nome")
    private String nomeDestinatario;
    @NotBlank(message = "Preencha Email do Remetente")
    @Email(message = "Email inválido")
    private String emailRemetente;
    @NotBlank(message = "Preencha o Assunto do Email")
    private String assunto;

    @NotBlank(message = "Preencha o conteudo do Email")
    private String conteudo;

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

    public String getEmailRemetente() {
        return emailRemetente;
    }

    public void setEmailRemetente(String emailRemetente) {
        this.emailRemetente = emailRemetente;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
