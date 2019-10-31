package br.com.avaliacao.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity(name="colaborador")
public class Colaborador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message="Nome é obrigatório")
	@Length(message="Digite um nome com até 50 caracteres", max=50)
	private String nome;
	
	@NotNull(message="Hierarquia é obrigatória")
	@Enumerated(EnumType.STRING)
	private Hierarquia hierarquia;
	
	@ManyToOne
	private Colaborador superior;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<CampoAvaliacao> campoAvaliacoes = new ArrayList<>();
	
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

	public Hierarquia getHierarquia(){
		return hierarquia;
	}

	public void setHierarquia(Hierarquia hierarquia){
		this.hierarquia = hierarquia;
	}

	public Colaborador getSuperior(){
		return superior;
	}

	public void setSuperior(Colaborador superior){
		this.superior = superior;
	}
	
	public void setCampoAvaliacoes(List<CampoAvaliacao> campoAvaliacoes) {
		this.campoAvaliacoes = campoAvaliacoes;
	}
	
	public List<CampoAvaliacao> getCampoAvaliacoes() {
		return campoAvaliacoes;
	}
	
	public void addCampoAvaliacao(CampoAvaliacao campoAvaliacao) {
		campoAvaliacoes.add(campoAvaliacao);
	}

	public String avaliacao() {
		try {
			DecimalFormat formatador = new DecimalFormat();
			formatador.applyPattern("#,##0.00");
			
			return formatador.format(campoAvaliacoes.stream()
					.filter(CA -> CA.getValor() > 0)
					.mapToDouble(CampoAvaliacao::getValor)
					.average()
					.getAsDouble());
		} catch (NoSuchElementException e) {
			return "Não avaliado(a)";
		} catch (Exception e) {
			return "Erro";
		}
	}
	
	@Override
	public String toString() {
		return "Colaborador [id=" + id + ", nome=" + nome + ", hierarquia=" + hierarquia + ", superior=" + superior
				+ ", campoAvaliacoes=" + campoAvaliacoes + "]";
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(id).hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Colaborador)) return false;
		Colaborador oColaborador = (Colaborador) o;

		return id == oColaborador.id;
	}
	
	public static Colaborador novo() {
		Colaborador novo = new Colaborador();
		
		novo.campoAvaliacoes.add(new CampoAvaliacao("É ciente dos objetivos da Empresa, estabelecendo prioridades e planejando ações para atingi-los."));				
		novo.campoAvaliacoes.add(new CampoAvaliacao("Utiliza de forma adequada e cuidadosa os recursos disponíveis na Empresa."));					
		novo.campoAvaliacoes.add(new CampoAvaliacao("Mostra-se atento e executa seu trabalho com agilidade e precisão, contribuindo para atingir os objetivos da qualidade da sua área."));			
		novo.campoAvaliacoes.add(new CampoAvaliacao("Ao se deparar com uma dificuldade no trabalho, participa prontamente da busca de soluções."));			
		
		novo.campoAvaliacoes.add(new CampoAvaliacao("Não falta ou chega atrasado no trabalho."));						
		novo.campoAvaliacoes.add(new CampoAvaliacao("Contribui com o processo de qualidade da Empresa."));					
		novo.campoAvaliacoes.add(new CampoAvaliacao("Utiliza os materiais e equipamentos da instituição com cuidado, visando uma maior durabilidade do mesmo."));						
		novo.campoAvaliacoes.add(new CampoAvaliacao("Conhece as normas de segurança no trabalho."));						
		
		novo.campoAvaliacoes.add(new CampoAvaliacao("Estabelece bom relacionamento com chefia e colegas de trabalho."));					
		novo.campoAvaliacoes.add(new CampoAvaliacao("Estabelece bom relacionamento com os funcionários da Instituição."));					
		novo.campoAvaliacoes.add(new CampoAvaliacao("Demonstra interesse em participar das atividades de capacitação e qualificação profissional promovidas pela Instituição, através dos treinamentos e cursos oferecidos internamente."));				
		novo.campoAvaliacoes.add(new CampoAvaliacao("Aceita sugestões e críticas visando o seu desenvolvimento."));					

		novo.campoAvaliacoes.add(new CampoAvaliacao("Aplica o conhecimento na execução da prática"));						
		novo.campoAvaliacoes.add(new CampoAvaliacao("Transfere o conhecimento aos colegas"));						
		novo.campoAvaliacoes.add(new CampoAvaliacao("Comunica-se com facilidade"));						
		novo.campoAvaliacoes.add(new CampoAvaliacao("Facilidade em trabalhar em equipe"));					
		novo.campoAvaliacoes.add(new CampoAvaliacao("Conhece os documentos da Gestão de Qualidade"));						
		novo.campoAvaliacoes.add(new CampoAvaliacao("Executa suas atividades nos prazos acordados."));						
		novo.campoAvaliacoes.add(new CampoAvaliacao("Preocupa-se em obter resultados satisfatórios"));					
		novo.campoAvaliacoes.add(new CampoAvaliacao("Realiza as tarefas cumprindo todas as normas éticas"));
		
		return novo;
	}
}
