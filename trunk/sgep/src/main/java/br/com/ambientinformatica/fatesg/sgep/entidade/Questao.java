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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.IndexColumn;

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;
import br.com.ambientinformatica.util.Entidade;

@Entity
public class Questao extends Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "questao_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "questao_seq", sequenceName = "questao_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(length = 2000)
	private String enunciado;

	@Column(length = 500)
	private String assunto;

	@ManyToOne(cascade = CascadeType.ALL)
	private Colaborador professor;

	@ManyToOne(optional=false)
	private Disciplina disciplina;

	@Enumerated(EnumType.STRING)
	private EnumEstado estado;

	@Enumerated(EnumType.STRING)
	private EnumDificuldade dificuldade;

	@Enumerated(EnumType.STRING)
	private EnumAlternativa resposta;

	private Boolean objetiva = true;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "questao_id")
	@IndexColumn(name = "index_alternativa")
	// definido como eager pelo fato de a quantidade de alternativas
	// relacionadas à questão ser pequena
	private List<AlternativaQuestao> alternativas = new ArrayList<AlternativaQuestao>();

	// Metodos
	public void addItem(AlternativaQuestao alternativa, boolean isAlternativaEdicao) throws Exception {
		if (!isAlternativaJaCadastrada(alternativa)) {
			this.alternativas.add(alternativa);
		} else if (!isAlternativaEdicao) {
			throw new Exception("Questão já contem este item!");
		}
	}

	private boolean isAlternativaJaCadastrada(AlternativaQuestao alternativa) {
		for (AlternativaQuestao alternativaQuestao : alternativas) {
			if (alternativaQuestao.getDescricao().equalsIgnoreCase(alternativa.getDescricao())) {
				return true;
			}
		}
		return false;
	}

	public void removerItem(AlternativaQuestao alternativa) {
		if (alternativas.contains(alternativa)) {
			this.alternativas.remove(alternativa);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setIdQuestao(Integer id) {
		this.id = id;
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
	public String toString() {
		return "Questao [alternativas=" + alternativas + "]";
	}

}
