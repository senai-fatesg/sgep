package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.api.entidade.Instituicao;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface InstituicaoDao extends Persistencia<Instituicao> {

	public List<Instituicao> listarPorNome(String nome);

}
