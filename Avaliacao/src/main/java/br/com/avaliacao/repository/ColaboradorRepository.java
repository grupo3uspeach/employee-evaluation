package br.com.avaliacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.avaliacao.entity.Colaborador;
import br.com.avaliacao.entity.Hierarquia;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {
	List<Colaborador> findAllByHierarquiaOrderByNome(Hierarquia hierarquia);
	
	List<Colaborador> findAllByHierarquiaNotOrderByNome(Hierarquia hierarquia);

	List<Colaborador> findAllBySuperiorOrderByNome(Colaborador superior);
}
