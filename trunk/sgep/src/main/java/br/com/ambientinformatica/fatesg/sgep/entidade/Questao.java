package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.IndexColumn;

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;

@Entity
public class Questao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "questao_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "questao_seq", sequenceName = "questao_seq", allocationSize = 1, initialValue = 1)
	private Integer idQuestao;

	private String enunciado;

	private String assunto;

	@ManyToOne
	private Colaborador professor;

	@ManyToOne
	private Disciplina disciplina;

	@Enumerated(EnumType.STRING)
	private EnumEstado estado;

	@Enumerated(EnumType.STRING)
	private EnumDificuldade dificuldade;
	
	@Enumerated(EnumType.STRING)
	private EnumAlternativa resposta;

	private Boolean objetiva = true;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	@IndexColumn(name = "index_alternativa")
	private List<AlternativaQuestao> alternativas = new ArrayList<AlternativaQuestao>();

	// Metodos
	public void addItem(AlternativaQuestao alternativa, boolean isAlternativaEdicao) throws Exception {
		if (!alternativas.contains(alternativa)) {
			this.alternativas.add(alternativa);
		} else if(!isAlternativaEdicao){
			throw new Exception("Questão já contem este item!");
		}
	}

	public void removerItem(AlternativaQuestao alternativa) {
		if (alternativas.contains(alternativa)) {
			this.alternativas.remove(alternativa);
		}
	}

	public Integer getIdQuestao() {
		return idQuestao;
	}

	public void setIdQuestao(Integer idQuestao) {
		this.idQuestao = idQuestao;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public Colaborador getProfessor() {
		return professor;
	}

	public void setProfessor(Colaborador professor) {
		this.professor = professor;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}

	public EnumDificuldade getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(EnumDificuldade dificuldade) {
		this.dificuldade = dificuldade;
	}

	public Boolean getObjetiva() {
		return objetiva;
	}

	public void setObjetiva(Boolean objetiva) {
		this.objetiva = objetiva;
	}

	public List<AlternativaQuestao> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(List<AlternativaQuestao> alternativas) {
		this.alternativas = alternativas;
	}
	
	public EnumAlternativa getResposta() {
		return resposta;
	}

	public void setResposta(EnumAlternativa resposta) {
		this.resposta = resposta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((alternativas == null) ? 0 : alternativas.hashCode());
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
		result = prime * result
				+ ((dificuldade == null) ? 0 : dificuldade.hashCode());
		result = prime * result
				+ ((disciplina == null) ? 0 : disciplina.hashCode());
		result = prime * result
				+ ((enunciado == null) ? 0 : enunciado.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result
				+ ((idQuestao == null) ? 0 : idQuestao.hashCode());
		result = prime * result
				+ ((objetiva == null) ? 0 : objetiva.hashCode());
		result = prime * result
				+ ((professor == null) ? 0 : professor.hashCode());
		result = prime * result
				+ ((resposta == null) ? 0 : resposta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Questao other = (Questao) obj;
		if (alternativas == null) {
			if (other.alternativas != null)
				return false;
		} else if (!alternativas.equals(other.alternativas))
			return false;
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
		if (idQuestao == null) {
			if (other.idQuestao != null)
				return false;
		} else if (!idQuestao.equals(other.idQuestao))
			return false;
		if (objetiva == null) {
			if (other.objetiva != null)
				return false;
		} else if (!objetiva.equals(other.objetiva))
			return false;
		if (professor == null) {
			if (other.professor != null)
				return false;
		} else if (!professor.equals(other.professor))
			return false;
		if (resposta == null) {
			if (other.resposta != null)
				return false;
		} else if (!resposta.equals(other.resposta))
			return false;
		return true;
	}

}
