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
public class ItemQuestaoTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "itemQuestaoTemplate_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "itemQuestaoTemplate_seq", sequenceName = "itemQuestaoTemplate_seq", allocationSize = 1, initialValue = 1)
	private Integer id_itemQuestaoTemplate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sessaoTemplate")
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.SAVE_UPDATE)
	private SessaoTemplate sessao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_questaoTemplate")
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.SAVE_UPDATE)
	private QuestaoTemplate questao;

	private int numero;

	public ItemQuestaoTemplate(SessaoTemplate sessao, QuestaoTemplate questao) {
		this.sessao = sessao;
		this.questao = questao;
	}

	public ItemQuestaoTemplate() {
	}

	public Integer getId() {
		return id_itemQuestaoTemplate;
	}

	public void setId(Integer id) {
		this.id_itemQuestaoTemplate = id;
	}

	public SessaoTemplate getSessao() {
		return sessao;
	}

	public void setSessao(SessaoTemplate sessao) {
		this.sessao = sessao;
	}

	public QuestaoTemplate getQuestao() {
		return questao;
	}

	public void setQuestao(QuestaoTemplate questao) {
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
		result = prime * result + ((id_itemQuestaoTemplate == null) ? 0 : id_itemQuestaoTemplate.hashCode());
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
		ItemQuestaoTemplate other = (ItemQuestaoTemplate) obj;
		if (id_itemQuestaoTemplate == null) {
			if (other.id_itemQuestaoTemplate != null)
				return false;
		} else if (!id_itemQuestaoTemplate.equals(other.id_itemQuestaoTemplate))
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
