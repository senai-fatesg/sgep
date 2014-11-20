package br.com.ambientinformatica.fatesg.sgep.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumDificuldade implements IEnum{
	F("Fácil"),
	M("Médio"),
	D("Difícil");
	
private final String descricao;
	
	EnumDificuldade(String descricao) {
		this.descricao = descricao;			
	}
	
	@Override
	public String getDescricao() {
		return descricao;
	}
	
}
