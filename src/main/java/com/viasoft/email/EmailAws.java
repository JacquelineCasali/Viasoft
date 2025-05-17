package com.viasoft.email;

import com.viasoft.dto.EmailAwsDTO;
import com.viasoft.dto.EmailDTO;
import com.viasoft.util.JsonUtil;
import org.springframework.stereotype.Component;

@Component
public class EmailAws implements Emails {

    @Override
    public void enviar(EmailDTO email){
        EmailAwsDTO awsDTO = new EmailAwsDTO(
                email.getEmailDestinatario(),
                email.getNomeDestinatario(),
                email.getEmailRemetente(),
                email.getAssunto(),
                email.getConteudo()
        );
        System.out.println(JsonUtil.serialize(awsDTO));

    }

}
