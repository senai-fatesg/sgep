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
	@GeneratedValue(generator = "questaoprova_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "questaoprova_seq", sequenceName = "questaoprova_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	//@PrimaryKeyJoinColumn
	private Questao questao = new Questao();

	public QuestaoProva() {
		questao = new Questao();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

}
