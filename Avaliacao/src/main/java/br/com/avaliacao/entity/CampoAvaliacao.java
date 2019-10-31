package br.com.avaliacao.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Entity(name="campoavaliacao")
public class CampoAvaliacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Length(message="Digite um nome com até 2000 caracteres", max=2000)
	private String nome;
	
	@ColumnDefault("0")
	@Range(min=0,max=5, message="Escolha valores entre 0 e 5")
	private int valor;

	public CampoAvaliacao() {
		
	}
	
	public CampoAvaliacao(String nome) {
		this.nome = nome;
	}

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	public String getNome(){
		return nome;
	}

	public void setNome(String nome){
		this.nome = nome;
	}

	public int getValor(){
		return valor;
	}

	public void setValor(int valor){
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return "CampoAvaliacao [id=" + id + ", nome=" + nome + ", valor=" + valor + "]";
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(id).hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof CampoAvaliacao)) return false;
		CampoAvaliacao oCampoAvaliacao = (CampoAvaliacao) o;

		return id == oCampoAvaliacao.id;
	}
}
