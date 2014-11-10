package br.com.ambientinformatica.fatesg.sgep.entidade;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Questao{

	@Id
	@GeneratedValue(generator = "questao_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "questao_seq" , sequenceName = "questao_seq", allocationSize = 1, initialValue = 1)
	private Long id;

	private int numero;
	
	private String enunciado;
	
	private String assunto;
	
	private char resposta;
	
	@Enumerated(EnumType.STRING)
	private EnumDificuldade dificuldade;
}
