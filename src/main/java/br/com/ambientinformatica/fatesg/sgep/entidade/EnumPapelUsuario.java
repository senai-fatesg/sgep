package br.com.ambientinformatica.fatesg.sgep.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumPapelUsuario implements IEnum{

   ADMIN("Administrador"),
   USUARIO("Usuário"),
	COLABORADOR("Colaborador");
	
	private final String descricao;
	
	private EnumPapelUsuario(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}
	
   
}