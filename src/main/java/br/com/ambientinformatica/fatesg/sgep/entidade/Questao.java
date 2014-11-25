package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;


@Entity
public class Questao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "questao_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "questao_seq" , sequenceName = "questao_seq", allocationSize = 1, initialValue = 1)
	private Long id;

	private String primeiraAlternativa;
	
	private String segundaAlternativa;
	
	private String terceiraAlternativa;
	
	private String quartaAlternativa;
	
	private String quintaAlternativa;
	
	private String enunciado;
	
	private String assunto;
	
	private char resposta;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="questao")
	private List<ItensProva > itens = new ArrayList<ItensProva>() ;
		
	@ManyToOne
	private Colaborador professor;
	
	@ManyToOne
	private Disciplina disciplina;
	
	@Enumerated(EnumType.STRING)
	private EnumEstado estado;
	
	@Enumerated(EnumType.STRING)
	private EnumDificuldade dificuldade;

	public Long getId() {
		return id;
	}

	public String getPrimeiraAlternativa() {
		return primeiraAlternativa;
	}

	public String getSegundaAlternativa() {
		return segundaAlternativa;
	}

	public String getTerceiraAlternativa() {
		return terceiraAlternativa;
	}

	public String getQuartaAlternativa() {
		return quartaAlternativa;
	}

	public String getQuintaAlternativa() {
		return quintaAlternativa;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public String getAssunto() {
		return assunto;
	}

	public char getResposta() {
		return resposta;
	}

	public List<ItensProva> getItens() {
		return itens;
	}

	public Colaborador getProfessor() {
		return professor;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}

	public void setDificuldade(EnumDificuldade dificuldade) {
		this.dificuldade = dificuldade;
	}

	public EnumDificuldade getDificuldade() {
		return dificuldade;
	}

	public void setPrimeiraAlternativa(String primeiraAlternativa) {
		this.primeiraAlternativa = primeiraAlternativa;
	}

	public void setSegundaAlternativa(String segundaAlternativa) {
		this.segundaAlternativa = segundaAlternativa;
	}

	public void setTerceiraAlternativa(String terceiraAlternativa) {
		this.terceiraAlternativa = terceiraAlternativa;
	}

	public void setQuartaAlternativa(String quartaAlternativa) {
		this.quartaAlternativa = quartaAlternativa;
	}

	public void setQuintaAlternativa(String quintaAlternativa) {
		this.quintaAlternativa = quintaAlternativa;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public void setResposta(char resposta) {
		this.resposta = resposta;
	}

	public void setItens(List<ItensProva> itens) {
		this.itens = itens;
	}

	public void setProfessor(Colaborador professor) {
		this.professor = professor;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
		result = prime * result
				+ ((dificuldade == null) ? 0 : dificuldade.hashCode());
		result = prime * result
				+ ((disciplina == null) ? 0 : disciplina.hashCode());
		result = prime * result
				+ ((enunciado == null) ? 0 : enunciado.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itens == null) ? 0 : itens.hashCode());
		result = prime
				* result
				+ ((primeiraAlternativa == null) ? 0 : primeiraAlternativa
						.hashCode());
		result = prime * result
				+ ((professor == null) ? 0 : professor.hashCode());
		result = prime
				* result
				+ ((quartaAlternativa == null) ? 0 : quartaAlternativa
						.hashCode());
		result = prime
				* result
				+ ((quintaAlternativa == null) ? 0 : quintaAlternativa
						.hashCode());
		result = prime * result + resposta;
		result = prime
				* result
				+ ((segundaAlternativa == null) ? 0 : segundaAlternativa
						.hashCode());
		result = prime
				* result
				+ ((terceiraAlternativa == null) ? 0 : terceiraAlternativa
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Questao other = (Questao) obj;
		if (assunto == null) {
			if (other.assunto != null)
				return false;
		} else if (!assunto.equals(other.assunto))
			return false;
		if (dificuldade != other.dificuldade)
			return false;
		if (disciplina == null) {
			if (other.disciplina != null)
				return false;
		} else if (!disciplina.equals(other.disciplina))
			return false;
		if (enunciado == null) {
			if (other.enunciado != null)
				return false;
		} else if (!enunciado.equals(other.enunciado))
			return false;
		if (estado != other.estado)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itens == null) {
			if (other.itens != null)
				return false;
		} else if (!itens.equals(other.itens))
			return false;
		if (primeiraAlternativa == null) {
			if (other.primeiraAlternativa != null)
				return false;
		} else if (!primeiraAlternativa.equals(other.primeiraAlternativa))
			return false;
		if (professor == null) {
			if (other.professor != null)
				return false;
		} else if (!professor.equals(other.professor))
			return false;
		if (quartaAlternativa == null) {
			if (other.quartaAlternativa != null)
				return false;
		} else if (!quartaAlternativa.equals(other.quartaAlternativa))
			return false;
		if (quintaAlternativa == null) {
			if (other.quintaAlternativa != null)
				return false;
		} else if (!quintaAlternativa.equals(other.quintaAlternativa))
			return false;
		if (resposta != other.resposta)
			return false;
		if (segundaAlternativa == null) {
			if (other.segundaAlternativa != null)
				return false;
		} else if (!segundaAlternativa.equals(other.segundaAlternativa))
			return false;
		if (terceiraAlternativa == null) {
			if (other.terceiraAlternativa != null)
				return false;
		} else if (!terceiraAlternativa.equals(other.terceiraAlternativa))
			return false;
		return true;
	}	
}