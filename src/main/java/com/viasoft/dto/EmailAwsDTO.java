package com.viasoft.dto;

import org.hibernate.validator.constraints.Length;

public class EmailAwsDTO {

    @Length(max = 45, message = "O Email do destinatário deve conter no máximo 45 caracteres")
    private String recipient;

    @Length(max = 60, message = "O Nome do destinatário deve conter no máximo 60 caracteres")
    private String recipientName;

    @Length(max = 45, message = "O Email do remetente deve conter no máximo 45 caracteres")
    private String sender;
    @Length(max = 120, message = "O Assunto do email deve conter no máximo 120 caracteres")
    private String subject;
    @Length(max = 256, message = "O Conteúdo do email deve conter no máximo 256 caracteres")
    private String content;

    public EmailAwsDTO() {

    }

    public EmailAwsDTO(String recipient, String recipientName, String sender, String subject, String content) {
        this.recipient = recipient;
        this.recipientName = recipientName;
        this.sender = sender;
        this.subject = subject;
        this.content = content;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
