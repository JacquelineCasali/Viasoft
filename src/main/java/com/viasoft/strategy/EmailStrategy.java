package com.viasoft.strategy;

import com.viasoft.dto.EmailDTO;

public interface EmailStrategy {
    void enviar(EmailDTO email);
}
