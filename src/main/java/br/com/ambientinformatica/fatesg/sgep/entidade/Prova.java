package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ambientinformatica.fatesg.api.entidade.Aluno;
import br.com.ambientinformatica.fatesg.api.entidade.Curso;
import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;
import br.com.ambientinformatica.fatesg.api.entidade.Instituicao;

@Entity
public class Prova {

	@Id
	@GeneratedValue(generator = "prova_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "prova_seq", sequenceName = "prova_seq", allocationSize = 1, initialValue = 1)
	private Integer id_prova;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data = new Date();

	@ManyToOne
	private Curso curso;

	@ManyToOne
	private Disciplina disciplina;

	@ManyToOne
	private Aluno aluno;

	@ManyToOne
	private Instituicao instituicao;

	private Integer periodo;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "prova_sessao", joinColumns = { @JoinColumn(name = "id_prova") }, inverseJoinColumns = { @JoinColumn(name = "id_sessaoProva") })
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
		return id_prova;
	}

	public void setId(Integer id) {
		this.id_prova = id;
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

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public List<SessaoProva> getSessoes() {
		return sessoes;
	}

	public void setSessoes(List<SessaoProva> sessoes) {
		this.sessoes = sessoes;
	}

	

}
