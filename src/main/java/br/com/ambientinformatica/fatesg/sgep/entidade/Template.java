package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.IndexColumn;

import br.com.ambientinformatica.util.Entidade;

@Entity
public class Template extends Entidade implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "template_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "template_seq", sequenceName = "template_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	private String descricao;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "template_sessao", joinColumns = { @JoinColumn(name = "idTemplate") }, inverseJoinColumns = { @JoinColumn(name = "idSessaoTemplate") })
	@IndexColumn(name = "index_sessao")
	private List<SessaoTemplate> sessoes = new ArrayList<SessaoTemplate>();

	public void addSessao(SessaoTemplate sessao) throws Exception {
		if (!sessoes.contains(sessao)) {
			this.sessoes.add(sessao);
		} else {
			throw new Exception("Sessão já adicionada.");
		}
	}

	public void removerSessao(SessaoTemplate sessao) {
		if (sessoes.contains(sessao)) {
			this.sessoes.remove(sessao);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<SessaoTemplate> getSessoes() {
		return sessoes;
	}

	public void setSessoes(List<SessaoTemplate> sessoes) {
		this.sessoes = sessoes;
	}

}
