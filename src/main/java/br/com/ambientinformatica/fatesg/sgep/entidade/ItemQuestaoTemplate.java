package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.util.Entidade;

@Entity
public class ItemQuestaoTemplate extends Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "itemquestaotemplate_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "itemquestaotemplate_seq", sequenceName = "itemquestaotemplate_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@ManyToOne
	private SessaoTemplate sessao;

	@ManyToOne
	private QuestaoTemplate questao;

	private int numero;
	
	
	public ItemQuestaoTemplate(){}

	public ItemQuestaoTemplate(SessaoTemplate sessao, QuestaoTemplate questao) {
		this.sessao = sessao;
		this.questao = questao;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

}
