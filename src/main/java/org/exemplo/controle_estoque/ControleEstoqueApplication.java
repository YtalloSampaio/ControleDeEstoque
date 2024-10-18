package org.exemplo.controle_estoque;

import org.exemplo.controle_estoque.model.Usuario;
import org.exemplo.controle_estoque.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ControleEstoqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControleEstoqueApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        return args -> {
            Usuario adminUser = new Usuario("adm", passwordEncoder.encode("adm123"));
            System.out.println("Usuário administrador criado com sucesso.");
        };
    }
}