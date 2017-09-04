package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.util.Entidade;

@Entity
public class AlternativaQuestao extends Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "alternativaquestao_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "alternativaquestao_seq", sequenceName = "alternativaquestao_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	private String descricao;

	private String ordem;

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

	public String getOrdem() {
		return ordem;
	}
	//comportamento definido para edição das alternativas da questão
	public void setOrdem(List<AlternativaQuestao> alternativas, boolean isAlternativaEdicao) {
			this.verificarOrdemAlternativas(alternativas, isAlternativaEdicao);
			return;
	}
	//comportamento definido para setar alternativas na geração da prova 
	public void setOrdem(String ordem){
		this.ordem = ordem;
	}
	
	private void verificarOrdemAlternativas(List<AlternativaQuestao> alternativas, boolean isAlternativaEdicao){
		 if(isAlternativaEdicao)
			 return;
		
		 List<String> listaDescricaoAlternativas = new ArrayList<>();
		 
		 for(AlternativaQuestao descricaoAlternativa : alternativas)
			 listaDescricaoAlternativas.add(descricaoAlternativa.ordem.toUpperCase());
		 
		 for (EnumAlternativa item : EnumAlternativa.values()) {
			 if(!listaDescricaoAlternativas.contains(item.getDescricao())){
				 this.ordem = item.getDescricao();
				 break;
			 }
		 }	
	}
	
	public Boolean getResposta() {
		return resposta;
	}

	public void setResposta(Boolean resposta) {
		this.resposta = resposta;
	}

	@Override
	public String toString() {
		return this.ordem+") "+this.descricao;
	}
	
	

}
