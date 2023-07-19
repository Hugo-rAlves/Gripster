package org.ufrpe.inovagovlab.decisoestce.seguranca.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ResponseAuth {

    private String token;
}
