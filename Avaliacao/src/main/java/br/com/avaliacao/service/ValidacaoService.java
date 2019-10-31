package br.com.avaliacao.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import br.com.avaliacao.entity.CampoAvaliacao;
import br.com.avaliacao.entity.Colaborador;
import br.com.avaliacao.entity.Hierarquia;
 
@Component
public class ValidacaoService {
 
    public void validar(Colaborador colaborador, BindingResult result) {
    	for (CampoAvaliacao campoAvaliacao : colaborador.getCampoAvaliacoes()){
    		if(campoAvaliacao.getValor() < 0 || campoAvaliacao.getValor() > 5) {
    			result.rejectValue("campoAvaliacoes", "Range", new Object[]{"'campoAvaliacoes"}, 
        				"Escolha valores entre 0 e 5");
    			
    			break;
    		}
    	}
    	
    	if(colaborador.getHierarquia() == null) return; // deixa o erro para o spring tratar
    		
    	if(Hierarquia.diretor.equals(colaborador.getHierarquia())) {
    		if(colaborador.getSuperior() == null) return;
    		result.rejectValue("superior", "conflito", new Object[]{"'superior'"}, 
    				"Superior não é permitido para um diretor");
    		return;
    	}
    	
    	if(colaborador.getSuperior() == null) {
    		result.rejectValue("superior", "ausente", new Object[]{"'superior'"}, 
    				"Superior é obrigatorio para um não diretor");
    		return;
    	}
    	
    	if(colaborador.getSuperior().getHierarquia().ordinal()+1 != colaborador.getHierarquia().ordinal()) {
    		result.rejectValue("superior", "conflito", new Object[]{"'superior'"}, 
    				"Um "+colaborador.getHierarquia()+" deve ter como superior um "+Hierarquia.values()[colaborador.getHierarquia().ordinal()-1]);
    	}
    }
}

