package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Template {

	@Id
	@GeneratedValue(generator = "template_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "template_seq", sequenceName = "template_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	private String descricao;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Sessao> sessoes = new ArrayList<Sessao>();

	//Metodos
	public void addSessao(Sessao sessao) throws Exception {
		if(!sessoes.contains(sessao)){
			this.sessoes.add(sessao);
		} else {

			throw new Exception("Sessão já adicionada.");
		}
	}
	
	public void removerSessao(Sessao sessao) {
		if(sessoes.contains(sessao)){
			this.sessoes.remove(sessao);
		}
	}
	
	//Gets e Sets
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Sessao> getSessoes() {
		return sessoes;
	}

	public void setSessoes(List<Sessao> sessoes) {
		this.sessoes = sessoes;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
