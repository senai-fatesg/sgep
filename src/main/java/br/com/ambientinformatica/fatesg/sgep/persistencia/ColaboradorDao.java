package br.com.ambientinformatica.fatesg.sgep.persistencia;

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;

public interface ColaboradorDao {
	
	Colaborador consultarPorCpf(String cpf);

}