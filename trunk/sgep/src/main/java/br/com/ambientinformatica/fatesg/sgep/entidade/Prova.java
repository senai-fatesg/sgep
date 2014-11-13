package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Prova{

	@Id
	@GeneratedValue(generator = "cabecalho_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "cabecalho_seq" , sequenceName = "cabecalho_seq", allocationSize = 1, initialValue = 1)
	private Long id;

	private int periodo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data = new Date();

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Long getId() {
		return id;
	}	
}
