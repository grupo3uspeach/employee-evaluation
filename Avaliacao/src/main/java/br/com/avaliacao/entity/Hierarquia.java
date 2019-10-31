package br.com.avaliacao.entity;

public enum Hierarquia {
	diretor, superintendente, coordenador, funcionario;

	public String imprime() {
		switch (this) {
		case diretor: return "diretor(a)";
		case superintendente: return "superintendente";
		case coordenador: return "coordenador(a)";
		case funcionario: return "funcionario(a)";
		}

		return null;
	}
}
