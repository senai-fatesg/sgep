package br.com.ambientinformatica.fatesg.sgep.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Sessao{

	@Id
	@GeneratedValue(generator = "sessao_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "sessao_seq", sequenceName = "sessao_seq", allocationSize = 1, initialValue = 1)
	private Long id;

	private String titulo;
	
	private String descricao;
}
