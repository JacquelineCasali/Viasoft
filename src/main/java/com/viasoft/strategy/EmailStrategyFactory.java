package com.viasoft.strategy;


import com.viasoft.enums.ProvedorIntegracao;
import com.viasoft.exception.EmailProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailStrategyFactory {
    private final EmailAwsStrategy emailAwsStrategy;
    private final EmailOciStrategy emailOciStrategy;

    public EmailStrategy getStrategy(ProvedorIntegracao integracao) {
        return switch (integracao) {
            case AWS -> emailAwsStrategy;
            case OCI -> emailOciStrategy;
            default -> throw new EmailProcessingException("Estratégia não implementada para: " + integracao);
        };
    }

}
