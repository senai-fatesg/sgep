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

	public Long getId() {
		return id;
	}

	public int getNumero() {
		return numero;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public String getAssunto() {
		return assunto;
	}

	public char getResposta() {
		return resposta;
	}

	public EnumDificuldade getDificuldade() {
		return dificuldade;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public void setResposta(char resposta) {
		this.resposta = resposta;
	}

	public void setDificuldade(EnumDificuldade dificuldade) {
		this.dificuldade = dificuldade;
	}
}
