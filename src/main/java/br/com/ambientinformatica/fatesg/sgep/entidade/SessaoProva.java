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
public class SessaoProva extends Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "sessaoProva_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "sessaoProva_seq", sequenceName = "sessaoProva_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY, orphanRemoval = true)
	@PrimaryKeyJoinColumn
	private Sessao sessao;

	@OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<ItemQuestaoProva> itemQuestao = new ArrayList<ItemQuestaoProva>();

	public SessaoProva() {
		sessao = new Sessao();
	}

	public void addQuestao(QuestaoProva questao) {
		try {
			ItemQuestaoProva item = new ItemQuestaoProva(this, questao);
			if (itemQuestao.contains(item)) {
				throw new Exception("Já contem questão");
			} else {
				itemQuestao.add(item);
			}
		} catch (Exception e) {

		}
	}

	public void removeQuestao(ItemQuestaoProva item) {
		itemQuestao.remove(item);
	}

	public Integer getId() {
		return id;
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

}
