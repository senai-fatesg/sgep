package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class ItemQuestaoProva implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "itensQuestaoProva_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "itensQuestaoProva_seq", sequenceName = "itensQuestaoProva_seq", allocationSize = 1, initialValue = 1)
	private Integer idItemQuestaoProva;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idSessaoProva")
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private SessaoProva sessao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idQuestaoProva")
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private QuestaoProva questao;

	private int numero;

	public ItemQuestaoProva(SessaoProva sessao, QuestaoProva questao) {
		this.sessao = sessao;
		this.questao = questao;
	}

	public Integer getIdItemQuestaoProva() {
		return idItemQuestaoProva;
	}

	public void setIdItemQuestaoProva(Integer idItemQuestaoProva) {
		this.idItemQuestaoProva = idItemQuestaoProva;
	}

	public SessaoProva getSessao() {
		return sessao;
	}

	public void setSessao(SessaoProva sessao) {
		this.sessao = sessao;
	}

	public QuestaoProva getQuestao() {
		return questao;
	}

	public void setQuestao(QuestaoProva questao) {
		this.questao = questao;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idItemQuestaoProva == null) ? 0 : idItemQuestaoProva
						.hashCode());
		result = prime * result + numero;
		result = prime * result + ((questao == null) ? 0 : questao.hashCode());
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
		ItemQuestaoProva other = (ItemQuestaoProva) obj;
		if (idItemQuestaoProva == null) {
			if (other.idItemQuestaoProva != null)
				return false;
		} else if (!idItemQuestaoProva.equals(other.idItemQuestaoProva))
			return false;
		if (numero != other.numero)
			return false;
		if (questao == null) {
			if (other.questao != null)
				return false;
		} else if (!questao.equals(other.questao))
			return false;
		if (sessao == null) {
			if (other.sessao != null)
				return false;
		} else if (!sessao.equals(other.sessao))
			return false;
		return true;
	}

}
