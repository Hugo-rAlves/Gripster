package org.ufrpe.inovagovlab.decisoestce.seguranca.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class RequestLogin {
    private String username;
    private String password;
}
