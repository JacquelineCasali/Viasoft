package com.viasoft.email;


import com.viasoft.enums.ProvedorIntegracao;
import com.viasoft.exception.EmailProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailFactory {
    private final EmailAws emailAwsStrategy;
    private final EmailOci emailOciStrategy;

    public Emails getStrategy(ProvedorIntegracao integracao) {
        return switch (integracao) {
            case AWS -> emailAwsStrategy;
            case OCI -> emailOciStrategy;
            default -> throw new EmailProcessingException("Estratégia não implementada para: " + integracao);
        };
    }

}
