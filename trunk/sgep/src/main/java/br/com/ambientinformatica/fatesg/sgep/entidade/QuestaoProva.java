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
public class QuestaoProva implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "questaoProva_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "questaoProva_seq", sequenceName = "questaoProva_seq", allocationSize = 1, initialValue = 1)
	private Integer id_questaoProva;

	@OneToOne(cascade = CascadeType.ALL)
	private Questao questao;

	public QuestaoProva() {
		questao = new Questao();
	}

	public Integer getId() {
		return id_questaoProva;
	}

	public void setId(Integer id) {
		this.id_questaoProva = id;
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
		result = prime * result
				+ ((id_questaoProva == null) ? 0 : id_questaoProva.hashCode());
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
		QuestaoProva other = (QuestaoProva) obj;
		if (id_questaoProva == null) {
			if (other.id_questaoProva != null)
				return false;
		} else if (!id_questaoProva.equals(other.id_questaoProva))
			return false;
		if (questao == null) {
			if (other.questao != null)
				return false;
		} else if (!questao.equals(other.questao))
			return false;
		return true;
	}

}
