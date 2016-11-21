package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.util.Entidade;

@Entity
public class ItemQuestaoProva extends Entidade	 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "itensquestaoprova_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "itensquestaoprova_seq", sequenceName = "itensquestaoprova_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@ManyToOne(cascade=CascadeType.ALL)
	private SessaoProva sessao;

	@ManyToOne(cascade=CascadeType.ALL)
	private QuestaoProva questao;

	private int numero;
	
	public ItemQuestaoProva() {

	}

	public ItemQuestaoProva(SessaoProva sessao, QuestaoProva questao) {
		this.sessao = sessao;
		this.questao = questao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

}
