package com.viasoft.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viasoft.dto.EmailAwsDTO;
import com.viasoft.dto.EmailDTO;
import com.viasoft.dto.EmailOciDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
@Service
public class EmailService {

    @Value("${mail.integracao}")
    private String integracao;

    // converter dados entre sistemas exemplo de json para xml
    private final ObjectMapper objectMapper;

    public EmailService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public void processarEmail(@Valid EmailDTO emailRequest) throws Exception {
        String json;

        switch (integracao.toUpperCase()) {
            case "AWS":
                EmailAwsDTO aws = new EmailAwsDTO();
                aws.setRecipient(emailRequest.getEmailDestinatario());
                aws.setRecipientName(emailRequest.getNomeDestinatario());
                aws.setSender(emailRequest.getEmailRemetente());
                aws.setSubject(emailRequest.getAssunto());
                aws.setContent(emailRequest.getConteudo());
                json = objectMapper.writeValueAsString(aws);
                break;

            case "OCI":
                EmailOciDTO oci = new EmailOciDTO();
                oci.setRecipientEmail(emailRequest.getEmailDestinatario());
                oci.setRecipientName(emailRequest.getNomeDestinatario());
                oci.setSenderEmail(emailRequest.getEmailRemetente());
                oci.setSubject(emailRequest.getAssunto());
                oci.setBody(emailRequest.getConteudo());
                json = objectMapper.writeValueAsString(oci);
                break;

            default:
                throw new IllegalArgumentException("Integração inválida: " + integracao);
        }

        System.out.println("Payload serializado:");
        System.out.println(json);
    }
}
