package br.com.avaliacao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avaliacao.entity.CampoAvaliacao;
import br.com.avaliacao.repository.CampoAvaliacaoRepository;

@Service
public class CampoAvaliacaoService {

	@Autowired
	private CampoAvaliacaoRepository repository;

	public List<CampoAvaliacao> findAll() {
		return repository.findAll();
	}

	public CampoAvaliacao save(CampoAvaliacao campoAvaliacao) {
		return repository.save(campoAvaliacao);
	}
	
	public Iterable<CampoAvaliacao> saveAll(Iterable<CampoAvaliacao> campoAvaliacao) {
		return repository.saveAll(campoAvaliacao);
	}

	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	public CampoAvaliacao findById(Integer id) {
		Optional<CampoAvaliacao> optional = repository.findById(id);
		if(!optional.isPresent()) return null;

		return optional.get();
	}

}
