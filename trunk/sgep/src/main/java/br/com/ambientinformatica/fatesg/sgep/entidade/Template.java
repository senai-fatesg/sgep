package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Template {
	
	@Id
	@GeneratedValue(generator = "template_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "template_seq" , sequenceName = "template_seq", allocationSize = 1, initialValue = 1)
	private Long id;
	
	@OneToMany
	private List<Sessao> sessao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Sessao> getSessao() {
		return sessao;
	}

	public void setSessao(List<Sessao> sessao) {
		this.sessao = sessao;
	}
}
