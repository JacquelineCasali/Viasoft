package com.viasoft.service;



import com.viasoft.dto.EmailDTO;
import com.viasoft.email.EmailFactory;
import com.viasoft.email.Emails;
import com.viasoft.enums.ProvedorIntegracao;
import com.viasoft.exception.EmailProcessingException;
import com.viasoft.utils.TestUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private EmailFactory emailFactory;

    @Mock
    private Emails emailStrategy;


    @BeforeEach
    void setup() {
        // Para simular a injeção do @Value da property, vamos setar via reflection:
        TestUtils.setField(emailService, "integracao", "AWS");
    }

    @Test
    void enviarEmailQuandoIntegracaoForAws() {
        // Arrange
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmailDestinatario("destinatario@example.com");
        emailDTO.setNomeDestinatario("Nome Destinatario");
        emailDTO.setEmailRemetente("remetente@example.com");
        emailDTO.setAssunto("Assunto");
        emailDTO.setConteudo("Conteudo");
        when(emailFactory.getStrategy(ProvedorIntegracao.AWS)).thenReturn(emailStrategy);
        emailService.processarEmail(emailDTO);
        verify(emailFactory).getStrategy(ProvedorIntegracao.AWS);
        verify(emailStrategy).enviar(emailDTO);
    }
    @Test
    void lancarExcecaoQuandoIntegracaoForInvalida() {
        TestUtils.setField(emailService, "integracao", "INVALID");

        EmailDTO emailDTO = new EmailDTO();

        EmailProcessingException thrown = assertThrows(
                EmailProcessingException.class,
                () -> emailService.processarEmail(emailDTO)
        );

        assertTrue(thrown.getMessage().contains("Erro ao processar email"));
    }
    @Test
    void deveLancarExcecaoQuandoEmailFalha() {
        TestUtils.setField(emailService, "integracao", "AWS");
        EmailDTO emailDTO = new EmailDTO();

        when(emailFactory.getStrategy(ProvedorIntegracao.AWS)).thenReturn(emailStrategy);
        doThrow(new RuntimeException("Erro interno")).when(emailStrategy).enviar(emailDTO);

        EmailProcessingException thrown = assertThrows(
                EmailProcessingException.class,
                () -> emailService.processarEmail(emailDTO)
        );

        assertTrue(thrown.getMessage().contains("Erro ao processar email"));
        assertTrue(thrown.getMessage().contains("Erro interno"));
    }
}
