package org.ufrpe.inovagovlab.decisoestce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ufrpe.inovagovlab.decisoestce.model.Role;
import org.ufrpe.inovagovlab.decisoestce.model.Usuario;
import org.ufrpe.inovagovlab.decisoestce.repository.UsuarioRepository;
import org.ufrpe.inovagovlab.decisoestce.seguranca.model.dto.RequestLogin;
import org.ufrpe.inovagovlab.decisoestce.seguranca.model.dto.RequestRegister;
import org.ufrpe.inovagovlab.decisoestce.seguranca.model.dto.ResponseAuth;
import org.ufrpe.inovagovlab.decisoestce.seguranca.service.JwtService;

@Service
public class AuthService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    public ResponseAuth login(RequestLogin request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        UserDetails usuario = repository.findByEmail(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        var jwtToken = jwtService.generateToken(usuario);
        ResponseAuth response = new ResponseAuth();
        response.setToken(jwtToken);
        return response;

    }

    public ResponseAuth register(RequestRegister request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.getPrimeiroNome());
        usuario.setUltimoNome(request.getUltimoNome());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setDataNascimento(request.getDataNascimento());
        usuario.setRole(Role.ADMIN);

        repository.save(usuario);

        String token = jwtService.generateToken(usuario);
        ResponseAuth response = new ResponseAuth();
        response.setToken(token);
        return response;
    }
}
