package br.com.ambientinformatica.fatesg.sgep.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Item{

	@Id
	@GeneratedValue(generator = "item_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "item_seq" , sequenceName = "item_seq", allocationSize = 1, initialValue = 1)
	private Long id;

	private String alternativa;
	
	private String assunto;

	public Long getId() {
		return id;
	}

	public String getAlternativa() {
		return alternativa;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAlternativa(String alternativa) {
		this.alternativa = alternativa;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
}
