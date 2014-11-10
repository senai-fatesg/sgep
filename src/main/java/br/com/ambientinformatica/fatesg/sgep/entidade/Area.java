package br.com.ambientinformatica.fatesg.sgep.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Area{

	@Id
	@GeneratedValue(generator = "area_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "area_seq" , sequenceName = "area_seq", allocationSize = 1, initialValue = 1)
	private Long id;

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}	
	
}
