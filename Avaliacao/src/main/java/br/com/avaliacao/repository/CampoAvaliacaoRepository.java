package br.com.avaliacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.avaliacao.entity.CampoAvaliacao;

public interface CampoAvaliacaoRepository extends JpaRepository<CampoAvaliacao, Integer> {

}
