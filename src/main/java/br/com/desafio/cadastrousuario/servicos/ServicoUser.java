package br.com.desafio.cadastrousuario.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.desafio.cadastrousuario.modelos.Usuario;
import br.com.desafio.cadastrousuario.repositorios.RepositorioUsuario;

@Service
public class ServicoUser  {

	@Autowired
	private RepositorioUsuario repositorioUser;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public Usuario buscarPorID(Long id) {
		return repositorioUser.getOne(id);
	}
	
	public Usuario buscarPorEmail(String email) {
		// TODO Auto-generated method stub
		return repositorioUser.findByEmail(email);
	}
	
	public void inserir(Usuario user) {
		user.setSenha(passwordEncoder.encode(user.getSenha()));
		repositorioUser.save(user);
	}
	
	public Usuario alterar(Usuario user) {
		return repositorioUser.save(user);
	}
	
	public List<Usuario> listar(){
		return repositorioUser.findAll();
	}
	
	public void deletar(Long id) {
		repositorioUser.deleteById(id);
	}

}
