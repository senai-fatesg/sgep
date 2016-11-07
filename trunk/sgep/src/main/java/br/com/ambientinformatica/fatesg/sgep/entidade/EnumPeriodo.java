package br.com.ambientinformatica.fatesg.sgep.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumPeriodo implements IEnum{
	
	PRIMEIRO_PERIODO("1° Período"),
	SEGUNGO_PERIODO("2° Período"),
	TERCEIRO_PERIODO("3° Período"),
	QUARTO_PERIODO("4° Período"),
	QUINTO_PERIODO("5° Período");

	private final String descricao;
	
	EnumPeriodo(String descricao){
		this.descricao = descricao;
	}
	
	@Override
	public String getDescricao() {
		return descricao;
	}
}
