package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;
import java.util.ArrayList;
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

import br.com.ambientinformatica.util.Entidade;

@Entity
public class SessaoTemplate extends Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "sessaoTemplate_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "sessaoTemplate_seq", sequenceName = "sessaoTemplate_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY, orphanRemoval = true)
	@PrimaryKeyJoinColumn
	private Sessao sessao;

	@OneToMany(mappedBy = "sessao", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<ItemQuestaoTemplate> itemQuestao = new ArrayList<ItemQuestaoTemplate>();

	public SessaoTemplate() {
		sessao = new Sessao();
	}

	public void addQuestao(QuestaoTemplate questao) {
		ItemQuestaoTemplate item = new ItemQuestaoTemplate(this, questao);
		if (itemQuestao.contains(item)) {

		} else {
			itemQuestao.add(item);
		}
	}

	public void removeQuestao(ItemQuestaoTemplate item) {
		itemQuestao.remove(item);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public List<ItemQuestaoTemplate> getItemQuestao() {
		return itemQuestao;
	}

	public void setItemQuestao(List<ItemQuestaoTemplate> itemQuestao) {
		this.itemQuestao = itemQuestao;
	}

}
