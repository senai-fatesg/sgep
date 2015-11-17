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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;

@Entity
public class SessaoProva implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "sessaoProva_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "sessaoProva_seq", sequenceName = "sessaoProva_seq", allocationSize = 1, initialValue = 1)
	private Integer idSessaoProva;

	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @PrimaryKeyJoinColumn
	private Sessao sessao;

	@OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<ItemQuestaoProva> itemQuestao;

	public SessaoProva() {
		sessao = new Sessao();
	}

	public Integer getIdSessaoProva() {
		return idSessaoProva;
	}

	public void setIdSessaoProva(Integer idSessaoProva) {
		this.idSessaoProva = idSessaoProva;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public List<ItemQuestaoProva> getItemQuestao() {
		return itemQuestao;
	}

	public void setItemQuestao(List<ItemQuestaoProva> itemQuestao) {
		this.itemQuestao = itemQuestao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		SessaoProva other = (SessaoProva) obj;
		if (sessao == null) {
			if (other.sessao != null)
				return false;
		} else if (!sessao.equals(other.sessao))
			return false;
		return true;
	}

}
