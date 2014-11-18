package br.com.ambientinformatica.fatesg.sgep.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumEstado implements IEnum{
	A("Aberto"),
	F("Fechado");

	private final String descricao;
	
	EnumEstado(String descricao) {
		this.descricao = descricao;			
	}
	
	@Override
	public String getDescricao() {
		return descricao;
	}
	
}
