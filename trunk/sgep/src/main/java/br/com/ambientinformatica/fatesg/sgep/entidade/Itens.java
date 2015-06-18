package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class Itens implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "itens_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "itens_seq" , sequenceName = "itens_seq", allocationSize = 1, initialValue = 1)
	private Long id;
	
	@NotNull
	private String primeiraAlternativa;
	
	@NotNull
	private String segundaAlternativa;
	
	@NotNull
	private String terceiraAlternativa;
	
	@NotNull
	private String quartaAlternativa;
	
	private String quintaAlternativa;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrimeiraAlternativa() {
		return primeiraAlternativa;
	}

	public void setPrimeiraAlternativa(String primeiraAlternativa) {
		this.primeiraAlternativa = primeiraAlternativa;
	}

	public String getSegundaAlternativa() {
		return segundaAlternativa;
	}

	public void setSegundaAlternativa(String segundaAlternativa) {
		this.segundaAlternativa = segundaAlternativa;
	}

	public String getTerceiraAlternativa() {
		return terceiraAlternativa;
	}

	public void setTerceiraAlternativa(String terceiraAlternativa) {
		this.terceiraAlternativa = terceiraAlternativa;
	}

	public String getQuartaAlternativa() {
		return quartaAlternativa;
	}

	public void setQuartaAlternativa(String quartaAlternativa) {
		this.quartaAlternativa = quartaAlternativa;
	}

	public String getQuintaAlternativa() {
		return quintaAlternativa;
	}

	public void setQuintaAlternativa(String quintaAlternativa) {
		this.quintaAlternativa = quintaAlternativa;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((primeiraAlternativa == null) ? 0 : primeiraAlternativa
						.hashCode());
		result = prime * result
				+ ((quartaAlternativa == null) ? 0 : quartaAlternativa.hashCode());
		result = prime * result
				+ ((quintaAlternativa == null) ? 0 : quintaAlternativa.hashCode());
		result = prime
				* result
				+ ((segundaAlternativa == null) ? 0 : segundaAlternativa.hashCode());
		result = prime
				* result
				+ ((terceiraAlternativa == null) ? 0 : terceiraAlternativa
						.hashCode());
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
		Itens other = (Itens) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (primeiraAlternativa == null) {
			if (other.primeiraAlternativa != null)
				return false;
		} else if (!primeiraAlternativa.equals(other.primeiraAlternativa))
			return false;
		if (quartaAlternativa == null) {
			if (other.quartaAlternativa != null)
				return false;
		} else if (!quartaAlternativa.equals(other.quartaAlternativa))
			return false;
		if (quintaAlternativa == null) {
			if (other.quintaAlternativa != null)
				return false;
		} else if (!quintaAlternativa.equals(other.quintaAlternativa))
			return false;
		if (segundaAlternativa == null) {
			if (other.segundaAlternativa != null)
				return false;
		} else if (!segundaAlternativa.equals(other.segundaAlternativa))
			return false;
		if (terceiraAlternativa == null) {
			if (other.terceiraAlternativa != null)
				return false;
		} else if (!terceiraAlternativa.equals(other.terceiraAlternativa))
			return false;
		return true;
	}
	
}
