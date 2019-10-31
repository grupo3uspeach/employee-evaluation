package br.com.avaliacao.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.avaliacao.entity.CampoAvaliacao;
import br.com.avaliacao.entity.Colaborador;
import br.com.avaliacao.entity.Hierarquia;
import br.com.avaliacao.service.CampoAvaliacaoService;
import br.com.avaliacao.service.ColaboradorService;
import br.com.avaliacao.service.ValidacaoService;

@Controller
public class AplicacaoController {

	@Autowired
	private ColaboradorService colaboradorService;
	
	@Autowired
	private CampoAvaliacaoService campoAvaliacaoService;

	@Autowired
	private ValidacaoService validacaoService;
	
	@RequestMapping("/")
	public ModelAndView home() {
		return new ModelAndView("home");
	}
	
	@RequestMapping("form") // mapemento da url
	public ModelAndView form() {
		return new ModelAndView("form") // nome do arquivo html
			.addObject("colaborador", Colaborador.novo()) // objeto ${objeto} usado no html -> nome objeto
			.addObject("hierarquias", Hierarquia.values())
			.addObject("superiores", colaboradorService.findAllByHierarquiaNotOrderByNome(Hierarquia.funcionario));
	}

	@RequestMapping("salvar")
	public ModelAndView salvar(@Valid @ModelAttribute("colaborador") Colaborador colaborador, BindingResult result) {
		validacaoService.validar(colaborador, result); // minha validacao
		
		if(result.hasErrors()) { // validacao do spring
			
			// se der erro, retorna o mesmo objeto
			return  new ModelAndView("form")
					.addObject("colaborador", colaborador)
					.addObject("hierarquias", Hierarquia.values())
					.addObject("superiores", colaboradorService.findAllByHierarquiaNotOrderByNome(Hierarquia.funcionario));
		}
		
		// se nao der erro, salva tudo
		campoAvaliacaoService.saveAll(colaborador.getCampoAvaliacoes());
		colaborador = colaboradorService.save(colaborador);
		
		// redireciona para a url /form
		return new ModelAndView("redirect:/form");
	}

	@RequestMapping("lista")
	public ModelAndView lista() {
		return new ModelAndView("lista")
			.addObject("superiores", colaboradorService.findAllByHierarquiaNotOrderByNome(Hierarquia.funcionario))
			.addObject("colaboradores", new ArrayList<Colaborador>());
	}

	@RequestMapping("lista/superior")
	public ModelAndView lista(@RequestParam("superior") Colaborador superior) {
		return new ModelAndView("lista")
			.addObject("superior", superior)
			.addObject("superiores", colaboradorService.findAllByHierarquiaNotOrderByNome(Hierarquia.funcionario))
			.addObject("colaboradores", colaboradorService.findAllBySuperiorOrderByNome(superior));
	}

	@RequestMapping("alterar")
	public ModelAndView alterar(@RequestParam("colaborador") Colaborador colaborador) {
		return  new ModelAndView("form")
				.addObject("colaborador", colaborador)
				.addObject("hierarquias", Hierarquia.values())
				.addObject("superiores", colaboradorService.findAllByHierarquiaNotOrderByNome(Hierarquia.funcionario));
	}

	@RequestMapping("avaliar")
	public ModelAndView avaliar(@RequestParam("colaborador") Colaborador colaborador) {
		return new ModelAndView("avaliar")
				.addObject("colaborador", colaborador);
	}
	
	@RequestMapping("excluir")
	public ModelAndView excluir(@RequestParam("colaborador") Colaborador colaborador) {
		colaboradorService.deleteById(colaborador.getId());
		if(colaborador.getSuperior() != null) return lista(colaborador.getSuperior());
		return lista();
	}
	
	@RequestMapping("avaliar/salvar")
	public ModelAndView avaliarSalvar(@Valid @ModelAttribute("colaborador") Colaborador colaborador, BindingResult result) {
		validacaoService.validar(colaborador, result);
		
		if(result.hasErrors()) {
			return  new ModelAndView("avaliar")
					.addObject("colaborador", colaborador);
		}
		
		campoAvaliacaoService.saveAll(colaborador.getCampoAvaliacoes());
		colaborador = colaboradorService.save(colaborador);
		
		return new ModelAndView("avaliar")
				.addObject("colaborador", colaborador);
	}
	
	@RequestMapping("avaliar/novo")
	public ModelAndView avaliaNovo(@ModelAttribute("colaborador") Colaborador colaborador) {
		colaborador.addCampoAvaliacao(new CampoAvaliacao());
		return new ModelAndView("avaliar")
				.addObject("colaborador", colaborador);
	}
	
	@RequestMapping("avaliar/excluir")
	public ModelAndView avaliaExcluir(@RequestParam("colaborador") Colaborador colaborador, @RequestParam("campoavaliacao") CampoAvaliacao campoavaliacao) {
		if(campoavaliacao.getId() != 0) campoAvaliacaoService.deleteById(campoavaliacao.getId());
		colaborador.getCampoAvaliacoes().remove(campoavaliacao);
		return new ModelAndView("avaliar")
				.addObject("colaborador", colaborador);
	}
}
