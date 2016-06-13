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

import org.hibernate.annotations.IndexColumn;

import br.com.ambientinformatica.fatesg.api.entidade.Aluno;
import br.com.ambientinformatica.fatesg.api.entidade.Curso;
import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;
import br.com.ambientinformatica.fatesg.api.entidade.Instituicao;

@Entity
public class Prova {

	@Id
	@GeneratedValue(generator = "prova_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "prova_seq", sequenceName = "prova_seq", allocationSize = 1, initialValue = 1)
	private Integer idProva;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data = new Date();

	@ManyToOne(fetch = FetchType.LAZY)
	private Curso curso;

	@ManyToOne(fetch = FetchType.LAZY)
	private Disciplina disciplina;

	@ManyToOne
	private Aluno aluno;

	@ManyToOne
	private Instituicao instituicao;

	private Integer periodo;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "prova_sessao", joinColumns = { @JoinColumn(name = "idProva") }, inverseJoinColumns = { @JoinColumn(name = "idSessaoProva") })
	@IndexColumn(name = "sessao")
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

	public Integer getIdProva() {
		return idProva;
	}

	public void setIdProva(Integer idProva) {
		this.idProva = idProva;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aluno == null) ? 0 : aluno.hashCode());
		result = prime * result + ((curso == null) ? 0 : curso.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((disciplina == null) ? 0 : disciplina.hashCode());
		result = prime * result + ((idProva == null) ? 0 : idProva.hashCode());
		result = prime * result
				+ ((instituicao == null) ? 0 : instituicao.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		result = prime * result + ((sessoes == null) ? 0 : sessoes.hashCode());
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
		Prova other = (Prova) obj;
		if (aluno == null) {
			if (other.aluno != null)
				return false;
		} else if (!aluno.equals(other.aluno))
			return false;
		if (curso == null) {
			if (other.curso != null)
				return false;
		} else if (!curso.equals(other.curso))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (disciplina == null) {
			if (other.disciplina != null)
				return false;
		} else if (!disciplina.equals(other.disciplina))
			return false;
		if (idProva == null) {
			if (other.idProva != null)
				return false;
		} else if (!idProva.equals(other.idProva))
			return false;
		if (instituicao == null) {
			if (other.instituicao != null)
				return false;
		} else if (!instituicao.equals(other.instituicao))
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
			return false;
		if (sessoes == null) {
			if (other.sessoes != null)
				return false;
		} else if (!sessoes.equals(other.sessoes))
			return false;
		return true;
	}

}
