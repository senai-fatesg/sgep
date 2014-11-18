package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

	@OneToMany(cascade=CascadeType.ALL, mappedBy="prova")
	private List<ItensProva> itens = new ArrayList<ItensProva>() ;

	public Long getId() {
		return id;
	}

	public int getPeriodo() {
		return periodo;
	}

	public Date getData() {
		return data;
	}

	public List<ItensProva> getItens() {
		return itens;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setItens(List<ItensProva> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itens == null) ? 0 : itens.hashCode());
		result = prime * result + periodo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prova other = (Prova) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itens == null) {
			if (other.itens != null)
				return false;
		} else if (!itens.equals(other.itens))
			return false;
		if (periodo != other.periodo)
			return false;
		return true;
	}
			
}
