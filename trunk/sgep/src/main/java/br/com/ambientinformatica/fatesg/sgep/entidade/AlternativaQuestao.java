package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class AlternativaQuestao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "alternativa_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "alternativa_seq", sequenceName = "alternativa_seq", allocationSize = 1, initialValue = 1)
	private Integer idAlternativa;

	private String descricao;

	private Character ordem;

	private Boolean resposta;

	public Integer getIdAlternativa() {
		return idAlternativa;
	}

	public void setIdAlternativa(Integer idAlternativa) {
		this.idAlternativa = idAlternativa;
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
		result = prime * result
				+ ((idAlternativa == null) ? 0 : idAlternativa.hashCode());
		result = prime * result
				+ ((resposta == null) ? 0 : resposta.hashCode());
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
		AlternativaQuestao other = (AlternativaQuestao) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idAlternativa == null) {
			if (other.idAlternativa != null)
				return false;
		} else if (!idAlternativa.equals(other.idAlternativa))
			return false;
		if (resposta == null) {
			if (other.resposta != null)
				return false;
		} else if (!resposta.equals(other.resposta))
			return false;
		return true;
	}

}
