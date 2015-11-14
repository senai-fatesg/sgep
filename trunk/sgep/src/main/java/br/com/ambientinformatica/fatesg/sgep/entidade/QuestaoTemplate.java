package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class QuestaoTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "questaoTemplate_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "questaoTemplate_seq", sequenceName = "questaoTemplate_seq", allocationSize = 1, initialValue = 1)
	private Integer id_questaoTemplate;

	@OneToOne(cascade = CascadeType.ALL)
	private Questao questao;

	public QuestaoTemplate() {
		questao = new Questao();
	}

	public Integer getId() {
		return id_questaoTemplate;
	}

	public void setId(Integer id) {
		this.id_questaoTemplate = id;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((id_questaoTemplate == null) ? 0 : id_questaoTemplate
						.hashCode());
		result = prime * result + ((questao == null) ? 0 : questao.hashCode());
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
		QuestaoTemplate other = (QuestaoTemplate) obj;
		if (id_questaoTemplate == null) {
			if (other.id_questaoTemplate != null)
				return false;
		} else if (!id_questaoTemplate.equals(other.id_questaoTemplate))
			return false;
		if (questao == null) {
			if (other.questao != null)
				return false;
		} else if (!questao.equals(other.questao))
			return false;
		return true;
	}

}