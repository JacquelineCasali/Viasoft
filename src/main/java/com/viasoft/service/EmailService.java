package com.viasoft.service;

import com.viasoft.dto.EmailDTO;
import com.viasoft.enums.ProvedorIntegracao;
import com.viasoft.exception.EmailProcessingException;
import com.viasoft.email.Emails;
import com.viasoft.email.EmailFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${mail.integracao}")
    private String integracao;

    private final EmailFactory emailStrategyFactory;


    public void processarEmail(@Valid EmailDTO email) {
        try {

ProvedorIntegracao provedor=ProvedorIntegracao.from(integracao);
            Emails emailStrategy = emailStrategyFactory.getStrategy(provedor);
            emailStrategy.enviar(email);
        } catch (Exception e) {
            throw new EmailProcessingException("Erro ao processar email: " + e.getMessage());
        }
    }
}