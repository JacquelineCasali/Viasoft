package com.viasoft.dto;

import org.hibernate.validator.constraints.Length;

public class EmailOciDTO {

    @Length(max = 40, message = "O Email destinatário deve conter no máximo 40 caracteres")
    private String recipientEmail;

    @Length(max = 50, message = "O Nome destinatário deve conter no máximo 50 caracteres")
    private String recipientName;

    @Length(max = 40, message = "O Email remetente deve conter no máximo 40 caracteres")
    private String senderEmail;
    @Length(max = 100, message = "O Assunto do e-mail deve conter no máximo 100 caracteres")
    private String subject;
    @Length(max = 250, message = "O Conteúdo do email deve conter no máximo 250 caracteres")
    private String body;

    public EmailOciDTO() {

    }

    public EmailOciDTO(String recipientEmail, String recipientName, String senderEmail, String subject, String body) {
        this.recipientEmail = recipientEmail;
        this.recipientName = recipientName;
        this.senderEmail = senderEmail;
        this.subject = subject;
        this.body = body;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
