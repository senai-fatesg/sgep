package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.ambientinformatica.fatesg.api.entidade.Aluno;
import br.com.ambientinformatica.fatesg.api.entidade.Curso;
import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;
import br.com.ambientinformatica.fatesg.api.entidade.Instituicao;
import br.com.ambientinformatica.util.AmbientValidator;
import br.com.ambientinformatica.util.Entidade;

@Entity
public class Prova extends Entidade implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "prova_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "prova_seq", sequenceName = "prova_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message="Informe a data da prova.", groups=AmbientValidator.class)
	private Date data;

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@NotNull(message="Informe curso.", groups=AmbientValidator.class)
	private Curso curso;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message="Informe o período.", groups=AmbientValidator.class)
	private EnumPeriodo periodo;

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@NotNull(message="Informe a disciplina.", groups=AmbientValidator.class)
	private Disciplina disciplina;

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Aluno aluno;

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@NotNull(message="Informe a instituição.", groups=AmbientValidator.class)
	private Instituicao instituicao;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SessaoProva> sessoes = new ArrayList<SessaoProva>();
	
	public void addSessao(SessaoProva sessao) throws Exception {
		if (!sessoes.contains(sessao)) {
			this.sessoes.add(sessao);
		} else {
			throw new Exception("Prova já contém esta Sessao!");
		}
	}

	public void removerSessao(SessaoProva sessao) {
		if (sessoes.contains(sessao)) {
			this.sessoes.remove(sessao);
		}
	}

	public Integer getId() {
		return id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public void setPeriodo(EnumPeriodo periodo) {
		this.periodo = periodo;
	}

	public EnumPeriodo getPeriodo() {
		return periodo;
	}

	public List<SessaoProva> getSessoes() {
		return sessoes;
	}

	public void setSessoes(List<SessaoProva> sessoes) {
		this.sessoes = sessoes;
	}

}
