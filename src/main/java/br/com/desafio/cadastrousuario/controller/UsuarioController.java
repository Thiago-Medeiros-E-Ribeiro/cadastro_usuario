package br.com.desafio.cadastrousuario.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.desafio.cadastrousuario.modelos.Usuario;
import br.com.desafio.cadastrousuario.repositorios.RepositorioUsuario;
import br.com.desafio.cadastrousuario.servicos.ServicoUser;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private ServicoUser servicoUser;

	@GetMapping("/listar")
	public ModelAndView listar(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/usuarios/listar");
		mv.addObject("usuario", servicoUser.listar());
		return mv;
	}
	
	@GetMapping("/inserir")
	public ModelAndView inserir() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("usuarios/inserir");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@PostMapping("/inserir")
	public ModelAndView inserir(@Valid Usuario usuario, BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		if(usuario.getNome() == null || usuario.getNome().isEmpty()) {
			result.rejectValue("nome", "usuario.nomeInvalido", "Preenchimento do nome é obrigatório");
		}
		
		if(usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			result.rejectValue("email", "usuario.emailInvalido", "Preenchimento do email é obrigatório");
		}
		
		//Verifica se já existe cadastrado o e-mail informado
		Usuario usuarioCadastrado = servicoUser.buscarPorEmail(usuario.getEmail());
				
		if (usuarioCadastrado!=null) {
			result.rejectValue("email", "usuario.emailJaCadastrado", "Já existe um usuário cadastrado com o e-mail informado.");
		}
		
		if(usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
			result.rejectValue("senha", "usuario.senhaInvalida", "Preenchimento da senha é obrigatório");	
		}
		
		if(result.hasErrors()) {
			mv.setViewName("usuarios/inserir");
			mv.addObject("usuario",usuario);
		}else{
			mv.setViewName("redirect:/usuarios/listar");
			servicoUser.inserir(usuario);
		}
		
		return mv;
	}

	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("usuarios/alterar");
		Usuario usuario = servicoUser.buscarPorID(id);
		mv.addObject("usuario", usuario);
		return mv;
	}
	
	@PostMapping("/alterar")
	public ModelAndView alterar(@Valid Usuario usuario, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		
		if(usuario.getNome() == null || usuario.getNome().isEmpty()) {
			result.rejectValue("nome", "usuario.nomeInvalido", "Preenchimento do nome é obrigatório");
			
		}
		
		if(usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			result.rejectValue("email", "usuario.emailInvalido", "Preenchimento do email é obrigatório");
			
		}
		
		Usuario usuarioVerificarEmail = servicoUser.buscarPorEmail(usuario.getEmail());
		
		if(usuarioVerificarEmail!=null && !usuarioVerificarEmail.getId().equals(usuario.getId())) {
			result.rejectValue("email", "usuario.emailJaCadastrado", "Já existe um usuário cadastrado com o e-mail informado.");
		}
		
		if(result.hasErrors()) {
			mv.setViewName("usuarios/alterar");
			mv.addObject("usuario",usuario);
		}else {
			mv.setViewName("redirect:/usuarios/listar");
			servicoUser.alterar(usuario);
		}
		
		return mv;
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id) {
		servicoUser.deletar(id);
		return "redirect:/usuarios/listar";
	}
}
