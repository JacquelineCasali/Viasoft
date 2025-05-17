package com.viasoft.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viasoft.dto.EmailAwsDTO;
import com.viasoft.dto.EmailDTO;
import com.viasoft.dto.EmailOciDTO;
import com.viasoft.enums.ProvedorIntegracao;
import com.viasoft.exception.EmailProcessingException;
import com.viasoft.strategy.EmailStrategy;
import com.viasoft.strategy.EmailStrategyFactory;
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

    private final EmailStrategyFactory emailStrategyFactory;


    public void processarEmail(@Valid EmailDTO email) {
        try {

ProvedorIntegracao provedor=ProvedorIntegracao.from(integracao);
            EmailStrategy emailStrategy = emailStrategyFactory.getStrategy(provedor);
            emailStrategy.enviar(email);
        } catch (Exception e) {
            throw new EmailProcessingException("Erro ao processar email: " + e.getMessage());
        }
    }
}