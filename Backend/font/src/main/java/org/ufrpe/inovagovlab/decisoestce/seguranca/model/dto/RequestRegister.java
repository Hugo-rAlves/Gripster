package org.ufrpe.inovagovlab.decisoestce.seguranca.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
public class RequestRegister {
    private String primeiroNome;
    private String ultimoNome;
    private String email;
    private String password;
    private LocalDate dataNascimento;


}
