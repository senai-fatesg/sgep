package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;
import br.com.ambientinformatica.util.Entidade;

@Entity
public class Questao extends Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "questao_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "questao_seq", sequenceName = "questao_seq", allocationSize = 1, initialValue = 1)
	private Integer id_questao;

	private String enunciado;

	private String assunto;

	private Character resposta;

	@ManyToOne
	private Colaborador professor;

	@ManyToOne
	private Disciplina disciplina;

	@Enumerated(EnumType.STRING)
	private EnumEstado estado;

	@Enumerated(EnumType.STRING)
	private EnumDificuldade dificuldade;

	private Boolean objetiva = true;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_alternativa")
	private List<AlternativaQuestao> alternativas = new ArrayList<AlternativaQuestao>();

	// Metodos
	public void addItem(AlternativaQuestao alternativa) throws Exception {
		if (!alternativas.contains(alternativa)) {
			this.alternativas.add(alternativa);
		} else {
			throw new Exception("Questão já contem este item!");
		}

	}

	public void removerItem(AlternativaQuestao alternativa) {
		if (alternativas.contains(alternativa)) {
			this.alternativas.remove(alternativa);
		}
	}

	public Integer getId() {
		return id_questao;
	}

	public void setId(Integer id) {
		this.id_questao = id;
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

	public Character getResposta() {
		return resposta;
	}

	public void setResposta(Character resposta) {
		this.resposta = resposta;
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
}
