package org.ufrpe.inovagovlab.decisoestce.seguranca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ufrpe.inovagovlab.decisoestce.seguranca.model.dto.RequestLogin;
import org.ufrpe.inovagovlab.decisoestce.seguranca.model.dto.RequestRegister;
import org.ufrpe.inovagovlab.decisoestce.seguranca.model.dto.ResponseAuth;
import org.ufrpe.inovagovlab.decisoestce.service.AuthService;

@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping(value = "/register")
    ResponseEntity<ResponseAuth> register(@RequestBody RequestRegister request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping(value = "/login")
    ResponseEntity<ResponseAuth> register(@RequestBody RequestLogin request){
        return ResponseEntity.ok(service.login(request));

    }
}
