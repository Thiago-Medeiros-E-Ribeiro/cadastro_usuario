package br.com.desafio.cadastrousuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.desafio.cadastrousuario.modelos.Usuario;
import br.com.desafio.cadastrousuario.repositorios.RepositorioUsuario;

@SpringBootApplication
public class CadastroUsuarioApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(CadastroUsuarioApplication.class, args);
	}
}
