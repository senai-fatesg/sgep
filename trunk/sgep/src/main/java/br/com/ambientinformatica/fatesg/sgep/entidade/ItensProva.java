package br.com.ambientinformatica.fatesg.sgep.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class ItensProva{//questao_prova

	@Id
	@GeneratedValue(generator = "itensProva_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "itensProva_seq" , sequenceName = "itensProva_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@ManyToOne
	private Sessao sessao;
	
	@ManyToOne
	private Questao questao;
	
	private int numero;

	public ItensProva(){
		
	}
	public ItensProva(Sessao sessao, Questao questao){
		this.sessao = sessao;
		this.questao = questao;
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
	public Questao getQuestao() {
		return questao;
	}
	public void setQuestao(Questao questao) {
		this.questao = questao;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
		
}
