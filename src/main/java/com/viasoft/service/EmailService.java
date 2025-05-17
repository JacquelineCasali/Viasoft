package com.viasoft.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viasoft.dto.EmailAwsDTO;
import com.viasoft.dto.EmailDTO;
import com.viasoft.dto.EmailOciDTO;
import com.viasoft.enums.ProvedorIntegracao;
import com.viasoft.exception.EmailProcessingException;
import com.viasoft.util.JsonUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${mail.integracao}")
    private String integracao;

    // converter dados entre sistemas exemplo de json para xml
    private final ObjectMapper objectMapper;


    public void processarEmail(@Valid EmailDTO email) {
        try {
            ProvedorIntegracao provedor = ProvedorIntegracao.from(integracao);

            switch (provedor) {
                case AWS -> {
                    EmailAwsDTO awsDTO = new EmailAwsDTO(
                            email.getEmailDestinatario(),
                            email.getNomeDestinatario(),
                            email.getEmailRemetente(),
                            email.getAssunto(),
                            email.getConteudo()
                    );
                    System.out.println(JsonUtil.serialize(awsDTO));
                }
                case OCI -> {
                    EmailOciDTO ociDTO = new EmailOciDTO(
                            email.getEmailDestinatario(),
                            email.getNomeDestinatario(),
                            email.getEmailRemetente(),
                            email.getAssunto(),
                            email.getConteudo()
                    );
                    System.out.println(JsonUtil.serialize(ociDTO));
                }
            }
        } catch (IllegalArgumentException e) {
            throw new EmailProcessingException("Valor inválido para mail.integracao: " + integracao);
        } catch (Exception e) {
            throw new EmailProcessingException("Erro ao processar email: " + e.getMessage());
        }
    }}