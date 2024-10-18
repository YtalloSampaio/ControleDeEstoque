package org.exemplo.controle_estoque.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.exemplo.controle_estoque.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager AuthenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody org.exemplo.controle_estoque.model.Usuario loginUsuario) {
        Authentication authentication = AuthenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( loginUsuario.getUsername(), loginUsuario.getPassword() )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = Jwts.builder()
                .setSubject( userDetails.getUsername() )
                .setIssuedAt( new Date() )
                .setExpiration( new Date( System.currentTimeMillis() + jwtExpiration ) )
                .signWith( SignatureAlgorithm.HS512, jwtSecret )
                .compact();

        return ResponseEntity.ok( token );
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody org.exemplo.controle_estoque.model.Usuario usuario) {
        if (usuarioRepository.findByUsername( usuario.getUsername() ).isPresent()) {
            return ResponseEntity.badRequest().body( "Username já existe" );
        }

        usuario.setPassword( passwordEncoder.encode( usuario.getPassword() ) );
        usuarioRepository.save( usuario );
        return ResponseEntity.ok( "Usuário registrado com sucesso" );
    }
}