package br.com.ambientinformatica.fatesg.sgep.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumDificuldade implements IEnum {
	Fácil("Fácil"), Médio("Médio"), Difícil("Difícil");

	private final String descricao;

	EnumDificuldade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
