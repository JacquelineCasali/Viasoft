package com.viasoft.email;

import com.viasoft.dto.EmailDTO;
import com.viasoft.dto.EmailOciDTO;
import com.viasoft.util.JsonUtil;
import org.springframework.stereotype.Component;

@Component
public class EmailOci implements Emails {

    @Override
  public void enviar(EmailDTO email){


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