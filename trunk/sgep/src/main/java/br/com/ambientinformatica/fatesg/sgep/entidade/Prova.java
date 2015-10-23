package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Prova {

	@Id
	@GeneratedValue(generator = "prova_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "prova_seq", sequenceName = "prova_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data = new Date();

	@OneToOne(cascade = CascadeType.ALL)
	private Template template;

	@ManyToOne(cascade = CascadeType.ALL)
	private Cabecalho cabecalho;

	public List<Questao> getQuestoes() {
		//TODO verificar
		return new ArrayList<Questao>();
	}
	
	public Integer getId() {
		return id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cabecalho getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(Cabecalho cabecalho) {
		this.cabecalho = cabecalho;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cabecalho == null) ? 0 : cabecalho.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((template == null) ? 0 : template.hashCode());
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
		if (cabecalho == null) {
			if (other.cabecalho != null)
				return false;
		} else if (!cabecalho.equals(other.cabecalho))
			return false;
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
		if (template == null) {
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template))
			return false;
		return true;
	}


}
