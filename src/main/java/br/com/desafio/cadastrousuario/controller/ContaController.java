package br.com.desafio.cadastrousuario.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.desafio.cadastrousuario.modelos.Usuario;
import br.com.desafio.cadastrousuario.servicos.ServicoUser;

@Controller
public class ContaController {
	
	@Autowired
	private ServicoUser servicoUser;

	@GetMapping("/login")
	public String login() {
		return "conta/login";
	}
	
	@GetMapping("/registration")
	public ModelAndView registrar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("conta/registrar");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@PostMapping("/registration")
	public ModelAndView registrar(@Valid Usuario usuario, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		
		if(usuario.getNome() == null || usuario.getNome().isEmpty()) {
			result.rejectValue("nome", "usuario.nomeInvalido", "Preenchimento do nome é obrigatório");
		}
		
		if(usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			result.rejectValue("email", "usuario.emailInvalido", "Preenchimento do email é obrigatório");
		}
		
		if(usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
			result.rejectValue("senha", "usuario.senhaInvalida", "Preenchimento da senha é obrigatório");	
		}
		
		Usuario usuarioJaCadastrado = servicoUser.buscarPorEmail(usuario.getEmail());
		
			
		if(usuarioJaCadastrado!=null) {
			result.rejectValue("email", "", "Usuário já cadastrado");
		}
		if(result.hasErrors()) {
			mv.setViewName("conta/registrar");
			mv.addObject("usuario", usuario);
		}else {
			servicoUser.inserir(usuario);
			mv.setViewName("redirect:/login");
		}
		return mv;
	}
}
