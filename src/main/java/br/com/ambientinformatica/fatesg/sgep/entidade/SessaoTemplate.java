package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class SessaoTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "sessaoTemplate_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "sessaoTemplate_seq", sequenceName = "sessaoTemplate_seq", allocationSize = 1, initialValue = 1)
	private Integer id_sessaoTemplate;

	@OneToOne(cascade = CascadeType.ALL)
	private Sessao sessao;

	@OneToMany(mappedBy = "sessao", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<ItemQuestaoTemplate> itemQuestao;

	public SessaoTemplate() {
		sessao = new Sessao();
	}

	public Integer getId() {
		return id_sessaoTemplate;
	}

	public void setId(Integer id) {
		this.id_sessaoTemplate = id;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((id_sessaoTemplate == null) ? 0 : id_sessaoTemplate
						.hashCode());
		result = prime * result + ((sessao == null) ? 0 : sessao.hashCode());
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
		SessaoTemplate other = (SessaoTemplate) obj;
		if (id_sessaoTemplate == null) {
			if (other.id_sessaoTemplate != null)
				return false;
		} else if (!id_sessaoTemplate.equals(other.id_sessaoTemplate))
			return false;
		if (sessao == null) {
			if (other.sessao != null)
				return false;
		} else if (!sessao.equals(other.sessao))
			return false;
		return true;
	}

}
