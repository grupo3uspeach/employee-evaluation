package br.com.avaliacao.service;

import br.com.avaliacao.entity.Colaborador;
import br.com.avaliacao.entity.Hierarquia;
import br.com.avaliacao.repository.ColaboradorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColaboradorService {

	@Autowired
	private ColaboradorRepository repository;

	public List<Colaborador> findAll() {
		return repository.findAll();
	}

	public Colaborador save(Colaborador colaborador) {
		return repository.save(colaborador);
	}

	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	public Colaborador findById(Integer id) {
		Optional<Colaborador> optional = repository.findById(id);
		if(!optional.isPresent()) return null;

		return optional.get();
	}

	public List<Colaborador> findAllByHierarquiaOrderByNome(Hierarquia hierarquia){
		return repository.findAllByHierarquiaOrderByNome(hierarquia);
	}
	
	public List<Colaborador> findAllByHierarquiaNotOrderByNome(Hierarquia hierarquia){
		return repository.findAllByHierarquiaNotOrderByNome(hierarquia);
	}

	public List<Colaborador> findAllBySuperiorOrderByNome(Colaborador superior){
		return repository.findAllBySuperiorOrderByNome(superior);
	}

}
