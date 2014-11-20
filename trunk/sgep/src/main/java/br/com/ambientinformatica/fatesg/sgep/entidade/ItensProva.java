package br.com.ambientinformatica.fatesg.sgep.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class ItensProva{

	@Id
	@GeneratedValue(generator = "itensProva_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "itensProva_seq" , sequenceName = "itensProva_seq", allocationSize = 1, initialValue = 1)
	private Long id;

	@ManyToOne
	private Prova prova;
	
	@ManyToOne
	private Questao questao;
	
	private int numero;

	public ItensProva(){
		
	}
	public ItensProva(Prova prova, Questao questao){
		this.prova = prova;
		this.questao = questao;
	}
	
	public Long getId() {
		return id;
	}

	public Prova getProva() {
		return prova;
	}

	public Questao getQuestao() {
		return questao;
	}

	public int getNumero() {
		return numero;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}	
	
}
