package br.com.ambientinformatica.fatesg.sgep.entidade;

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

@Entity
public class Template {

	@Id
	@GeneratedValue(generator = "template_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "template_seq", sequenceName = "template_seq", allocationSize = 1, initialValue = 1)
	private Integer idTemplate;

	private String descricao;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "template_sessao", joinColumns = { @JoinColumn(name = "idTemplate") }, inverseJoinColumns = { @JoinColumn(name = "idSessaoTemplate") })
	@IndexColumn(name = "index_sessao")
	private List<SessaoTemplate> sessoes = new ArrayList<SessaoTemplate>();

	// Metodos
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

	// Gets e Sets
	public Integer getIdTemplate() {
		return idTemplate;
	}

	public void setIdTemplate(Integer idTemplate) {
		this.idTemplate = idTemplate;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((idTemplate == null) ? 0 : idTemplate.hashCode());
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
		Template other = (Template) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idTemplate == null) {
			if (other.idTemplate != null)
				return false;
		} else if (!idTemplate.equals(other.idTemplate))
			return false;
		if (sessoes == null) {
			if (other.sessoes != null)
				return false;
		} else if (!sessoes.equals(other.sessoes))
			return false;
		return true;
	}

}
