package br.com.desafio.cadastrousuario.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafio.cadastrousuario.modelos.Usuario;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {	
	Usuario findByEmail(String email);
}
