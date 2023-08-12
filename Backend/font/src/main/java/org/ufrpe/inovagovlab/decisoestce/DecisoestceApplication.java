package org.ufrpe.inovagovlab.decisoestce;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Decisões API", version = "2.0", description = "Dados para construção das decisões"))
public class DecisoestceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DecisoestceApplication.class, args);
    }

}
