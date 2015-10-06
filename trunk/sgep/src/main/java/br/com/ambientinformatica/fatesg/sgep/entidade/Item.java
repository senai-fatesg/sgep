package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class Item implements Serializable {

	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "itens_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "itens_seq", sequenceName = "itens_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@NotNull
	private String descricao;

	private Character ordem;

	private Boolean resposta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Character getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		switch (ordem) {
		case 0:
			this.ordem = "a".charAt(0);
			break;
		case 1:
			this.ordem = "b".charAt(0);
			break;
		case 2:
			this.ordem = "c".charAt(0);
			break;
		case 3:
			this.ordem = "d".charAt(0);
			break;
		case 4:
			this.ordem = "e".charAt(0);
			break;
		}
	}

	public Boolean getResposta() {
		return resposta;
	}

	public void setResposta(Boolean resposta) {
		this.resposta = resposta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
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
		Item other = (Item) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		return true;
	}

}
