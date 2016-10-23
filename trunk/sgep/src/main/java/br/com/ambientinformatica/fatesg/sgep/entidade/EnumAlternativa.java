package br.com.ambientinformatica.fatesg.sgep.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumAlternativa implements IEnum{
	A("A"), B("B"), C("C"), D("D"), E("E");
	
	private final String descricao;
	
	private EnumAlternativa(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
